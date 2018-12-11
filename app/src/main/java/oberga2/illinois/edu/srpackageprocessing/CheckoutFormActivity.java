package oberga2.illinois.edu.srpackageprocessing;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import oberga2.illinois.edu.srpackageprocessing.database.ProcessorHelper;

public class CheckoutFormActivity extends AppCompatActivity {

    ProcessorHelper dbHelper;

    //id of the current package
    int currPackageId;

    //exit button on the page
    ImageButton exitIB;

    //text that displays info about the current package
    TextView currNameTV;
    TextView currDateTV;
    TextView currFirmTV;
    TextView currCountTV;

    //elements of the checkout form
    Button submitButton;
    EditText nameET;
    EditText idET;
    EditText dateET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_form);
        dbHelper = new ProcessorHelper(this);

        //get the information about the package from the previous page
        Intent receivedIntent = getIntent();
        currPackageId = receivedIntent.getIntExtra("packageId", -1);
        String currPackageRecipient = receivedIntent.getStringExtra("packageRecipient");
        String currPackageDate = receivedIntent.getStringExtra("packageDate");
        String currPackageFirm = receivedIntent.getStringExtra("packageFirm");
        int currPackageCount = receivedIntent.getIntExtra("packageCount", -1);

        //get all elements on the page
        getElements();

        //create the strings for the text view
        String currNameText = "Recipient: " + currPackageRecipient;
        String currDateText = "Date: " + currPackageDate;
        String currFirmText = "Firm: " + currPackageFirm;
        String currCountText = "Count: " + currPackageCount;

        //set the text view text to the package info
        currNameTV.setText(currNameText);
        currDateTV.setText(currDateText);
        currFirmTV.setText(currFirmText);
        currCountTV.setText(currCountText);

        //set the listener for the exit button
        exitOnClickListenerWrapper();

        //set the listener for the submit button
        submitOnClickListenerWrapper();

        //check if the package has been picked up yet
        Cursor dbData = dbHelper.getData();
        while(dbData.moveToNext()) {
            //get current package data
            int dbPackageId = dbData.getInt(0);

            String checkoutRecipient = dbData.getString(6);
            String checkoutId = dbData.getString(7);
            String checkoutDate = dbData.getString(8);

            if(dbPackageId == currPackageId) {
                //check if the packaged has been picked up
                if(checkoutRecipient.length() > 0) {
                    //display the pickup info
                    nameET.setText(checkoutRecipient);
                    idET.setText(checkoutId);
                    dateET.setText(checkoutDate);

                    //disable form elements
                    nameET.setEnabled(false);
                    idET.setEnabled(false);
                    dateET.setEnabled(false);
                    submitButton.setVisibility(View.GONE);
                }
            }
        }
    }

    /**
     * This function is a wrapper for the setOnClickListener method for the Exit button. When the user
     * exits this page, it returns results to the main activity of the app in order to trigger the
     * list view update.
     */
    public void exitOnClickListenerWrapper() {
        exitIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
    }

    /**
     * This function finds all of the elements on the page and assigns them to their respective variables.
     */
    public void getElements() {
        //get exit button from fragment
        exitIB = findViewById(R.id.checkout_exit);

        //get the text views that will hold package information
        currNameTV = findViewById(R.id.checkout_package_recipient);
        currDateTV = findViewById(R.id.checkout_package_date);
        currFirmTV = findViewById(R.id.checkout_package_firm);
        currCountTV = findViewById(R.id.checkout_package_count);

        //get Button from fragment
        submitButton = findViewById(R.id.checkout_submit);

        //get EditText from fragment
        nameET = findViewById(R.id.checkout_name);
        idET = findViewById(R.id.checkout_id);
        dateET = findViewById(R.id.checkout_date);
    }

    /**
     * This is a wrapper for the updateData function in the database helper. This helps with notifying
     * the user if any errors occurred.
     *
     * @param id id of the current package in the database
     * @param pickupName name of the recipient of the package
     * @param pickupId id of the recipient of the package
     * @param pickupDate date the package was picked up
     * @return
     */
    public boolean updateDataInDB(int id, String pickupName, String pickupId, String pickupDate) {
        boolean updateData = dbHelper.updateData(id, pickupName, pickupId, pickupDate);

        return updateData;
    }

    /**
     * Makes a Toast to display on the screen.
     * @param message the message to display
     */
    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * This function is a wrapper for the setOnClickListener for the submit button. This ensures that
     * all required fields of the form are filled out. If some aren't, the form will fail to submit
     * and the user will be notified. If all fields are filled out, the app will attempt to check out
     * the package and will notify the user of the update's success or failure.
     */
    public void submitOnClickListenerWrapper() {
        //handles user attempt at form submission
        submitButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //get user input from EditText
                        String pickupName = nameET.getText().toString();
                        String pickupId = idET.getText().toString();
                        String pickupDate = dateET.getText().toString();

                        //check if the required fields have user input
                        if(pickupName.length() == 0 || pickupDate.length() == 0 || pickupId.length() == 0) {
                            //one of the required fields is empty
                            //user is notified
                            toastMessage("Incomplete field! Please fill in all required fields.");
                        } else {
                            //all of the required fields have user input
                            //attempt to add the package to database
                            boolean update = updateDataInDB(currPackageId, pickupName, pickupId, pickupDate);
                            //notify the user accordingly
                            if(update) {
                                //package was processed
                                toastMessage("Successfully picked up a package!");

                                //disable edit text after successful pick up
                                nameET.setEnabled(false);
                                idET.setEnabled(false);
                                dateET.setEnabled(false);
                                submitButton.setEnabled(false);

                            } else {
                                //an error in database storage occurred
                                toastMessage("An error occurred updating data to database.");
                            }
                        }
                    }
                }
        );
    }
}
