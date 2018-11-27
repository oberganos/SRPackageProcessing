package oberga2.illinois.edu.srpackageprocessing;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import oberga2.illinois.edu.srpackageprocessing.database.ProcessorHelper;

/**
 * The main purpose of this fragment is to hold all of the elements of the package processing form.
 * There are multiple text fields that require user input on the page: Recipient, Firm, Date, and
 * Count. If the user would like to enter any additional information, they can include it in the
 * Notes text field. The button will submit the form.
 *
 * If the form is submitted with incomplete fields, the user will be notified and the form submission
 * will not be accepted.
 * If the form is submitted with all required fields completed, the user will be notified, the text
 * fields will be emptied, and the package will be added to the database.
 */
public class ProcessFragment extends Fragment {
    //current activity is saved because some functions require the activity, not the fragment
    Activity currActivity;

    ProcessorHelper dbHelper;

    //the components of the form on the fragment
    Button submitButton;
    EditText recipientET;
    EditText firmET;
    EditText dateET;
    EditText countET;
    EditText notesET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_process, null);

        currActivity = getActivity();
        currActivity.setTitle(R.string.title_process);

        dbHelper = new ProcessorHelper(currActivity);

        //get Button from fragment
        submitButton = inflatedView.findViewById(R.id.process_submit);

        //get EditText from fragment
        recipientET = inflatedView.findViewById(R.id.process_recipient_name);
        firmET = inflatedView.findViewById(R.id.process_firm);
        dateET = inflatedView.findViewById(R.id.process_entry_date);
        countET = inflatedView.findViewById(R.id.process_count);
        notesET = inflatedView.findViewById(R.id.process_notes);

        //handles user attempt at form submission
        submitButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //get user input from EditText
                        String recipient = recipientET.getText().toString();
                        String firm = firmET.getText().toString();
                        String date = dateET.getText().toString();
                        String count = countET.getText().toString();
                        String notes = notesET.getText().toString();

                        //check if the required fields have user input
                        if(recipient.length() == 0 || firm.length() == 0 || date.length() == 0 || count.length() == 0) {
                            //one of the required fields is empty
                            //user is notified
                            toastMessage("Incomplete field! Please fill in all required fields.");
                        } else {
                            //all of the required fields have user input
                            //attempt to add the package to database
                            boolean add = addDataToDB(recipient, firm, date, count, notes);
                            //notify the user accordingly
                            if(add) {
                                //package was processed
                                toastMessage("Successfully processed a package!");
                            } else {
                                //an error in database storage occurred
                                toastMessage("An error occurred inserting data to database.");
                            }

                            //clear all EditText user input
                            clearFields();
                        }
                    }
                }
        );

        return inflatedView;
    }

    /**
     * Wrapper function for the database helper function to add a package to the database.
     *
     * @param recipient recipient of the package
     * @param firm where the package is from
     * @param date date the package arrived
     * @param count number of packages processed
     * @param notes any additional information
     * @return TRUE if successfully added package to the database, FALSE if there was an error
     */
    public boolean addDataToDB(String recipient, String firm, String date, String count, String notes) {
        boolean insertData = dbHelper.addData(recipient, firm, date, count, notes);

        return insertData;
    }

    /**
     * Makes a Toast to display on the screen.
     * @param message the message to display
     */
    public void toastMessage(String message) {
        Toast.makeText(currActivity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Clear all of the fields of user input.
     */
    public void clearFields() {
        recipientET.setText("");
        firmET.setText("");
        dateET.setText("");
        countET.setText("");
        notesET.setText("");
    }
}
