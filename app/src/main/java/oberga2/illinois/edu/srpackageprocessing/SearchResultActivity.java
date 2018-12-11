package oberga2.illinois.edu.srpackageprocessing;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import oberga2.illinois.edu.srpackageprocessing.database.ProcessorHelper;

/**
 * This class contains all of the functions for the Home fragment of this app.
 */
public class SearchResultActivity extends AppCompatActivity {

    public static final String TAG = "SearchResultActivity";
    ListView resultsLV;
    ProcessorHelper dbHelper;
    Activity currActivity;
    ImageButton exitIB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setTitle("Search Results");

        currActivity = this;
        dbHelper = new ProcessorHelper(this);
        resultsLV = findViewById(R.id.searchResultsListView);

        //get user input from advanced search page
        Intent receivedIntent = getIntent();
        int type = receivedIntent.getIntExtra("searchType", 0);
        String recipient = receivedIntent.getStringExtra("searchRecipient").toLowerCase();
        String firm = receivedIntent.getStringExtra("searchFirm").toLowerCase();
        String date = receivedIntent.getStringExtra("searchDate").toLowerCase();
        String id = receivedIntent.getStringExtra("searchId");

        //set the listener for the exit button
        exitIB = findViewById(R.id.search_exit);
        exitOnClickListenerWrapper();

        //get a list of package depending on whether the user searched using keywords or package ID
        ArrayList<Package> packages;
        if(id.length() == 0) {
            packages = getPackagesByKeyword(type, recipient, firm, date);
        } else {
            packages = getPackagesById(id);
        }

        //populate the list view
        PackageListAdapter adapter = new PackageListAdapter(this, R.layout.adapter_home_layout, packages);
        resultsLV.setAdapter(adapter);

        //set the listener for the list view so that each entry is clickable
        setOnItemClickListenerWrapper();

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
                finish();
            }
        });
    }

    /**
     * This is a wrapper function for the setOnItemClickListener for each list view item. Once an
     * item is clicked, the package and its information will be saved to use for the checkout form.
     * The app will also switch to a new page, the checkout form.
     */
    public void setOnItemClickListenerWrapper() {
        resultsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the package clicked
                Package clickedPackage = (Package) resultsLV.getItemAtPosition(position);

                //get package information
                int currId = clickedPackage.getId();
                String currRecipient = clickedPackage.getRecipient();
                String currDate = clickedPackage.getDate();
                String currFirm = clickedPackage.getFirm();
                int currCount = clickedPackage.getCount();
                //Log.v(TAG, "" + currId);

                //create a new intent, which will open up the checkout form
                Intent checkoutFormIntent;
                checkoutFormIntent = new Intent(currActivity, CheckoutFormActivity.class);

                //save package information in the intent for use within the checkout form
                checkoutFormIntent.putExtra("packageId", currId);
                checkoutFormIntent.putExtra("packageRecipient", currRecipient);
                checkoutFormIntent.putExtra("packageDate", currDate);
                checkoutFormIntent.putExtra("packageFirm", currFirm);
                checkoutFormIntent.putExtra("packageCount", currCount);

                //start activity, waiting for a result from the checkout form
                startActivity(checkoutFormIntent);
            }
        });
    }

    /**
     * This function gets a list of packages based on keywords the user inputs from the advanced
     * search page.
     *
     * @param pType 0 - all packages; 1 - pending packages; 2 - checked out packages
     * @param pRecipient user input in the recipient edit text
     * @param pFirm user input in the firm edit text
     * @param pDate user input in the date edit text
     * @return a list of packages that satisfy all criteria
     */
    public ArrayList<Package> getPackagesByKeyword(int pType, String pRecipient, String pFirm, String pDate) {
        ArrayList<Package> packages = new ArrayList<>();

        //get all database data
        Cursor dbData = dbHelper.getData();
        while(dbData.moveToNext()) {
            //get current package data
            int id = dbData.getInt(0);
            String recipient = dbData.getString(1);
            String date = dbData.getString(2);
            String firm = dbData.getString(3);
            int count = dbData.getInt(4);
            String checkout = dbData.getString(6);

            Package currPackage = new Package(id, recipient, date, firm, count);

            recipient = recipient.toLowerCase();
            date = date.toLowerCase();
            firm = firm.toLowerCase();

            //search type - pending packages; skip current package if checked out by somebody
            if(pType == 1 && checkout.length() > 0) {
                continue;
            }

            //search type - checked out packages; skip current package if it hasn't been picked up
            if(pType == 2 && checkout.length() == 0) {
                continue;
            }

            //skip if current package doesn't satisfy search criteria
            if(!recipient.contains(pRecipient)) {
                continue;
            }

            if(!firm.contains(pFirm)) {
                continue;
            }

            if(!date.contains(pDate)) {
                continue;
            }

            //the package satisfies all criteria
            packages.add(currPackage);
        }

        return packages;
    }

    /**
     * This function searches for a package using its package ID. Since the package ID is unique for
     * each package, the most packages this function will return is 1.
     *
     * @param pId the user input for package ID
     * @return the package with the requested ID in a list or an empty list
     */
    public ArrayList<Package> getPackagesById(String pId) {
        ArrayList<Package> packages = new ArrayList<>();

        Cursor dbData = dbHelper.getData();
        while(dbData.moveToNext()) {
            //get package info
            int id = dbData.getInt(0);
            String recipient = dbData.getString(1);
            String date = dbData.getString(2);
            String firm = dbData.getString(3);
            int count = dbData.getInt(4);

            Package currPackage = new Package(id, recipient, date, firm, count);

            //check if the user input for package ID is the same as the current package ID
            if(pId.length() != 0) {
                int packageId = Integer.parseInt(pId);
                if(id == packageId) {
                    //the package exists
                    packages.add(currPackage);
                    break;
                } else {
                    continue;
                }
            }
        }

        return packages;
    }
}
