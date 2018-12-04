package oberga2.illinois.edu.srpackageprocessing;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import oberga2.illinois.edu.srpackageprocessing.database.ProcessorHelper;

/**
 * This class contains all of the functions for the Checkout fragment of this app.
 */
public class CheckoutFragment extends Fragment {

    public final static String TAG = "CheckoutFragment";

    Activity currActivity;
    ProcessorHelper dbHelper;
    ListView packagesLV;
    PackageListAdapter adapter;

    ArrayList<Package> packages;
    EditText searchET;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_checkout, null);

        currActivity = getActivity();
        currActivity.setTitle(R.string.title_checkout);

        dbHelper = new ProcessorHelper(currActivity);

        //get the elements on the page and assign them to their respective variables
        packagesLV = inflatedView.findViewById(R.id.packageListView);
        searchET = inflatedView.findViewById(R.id.checkout_search);

        //get the initial list of packages to display which haven't been checked out yet
        packages = getUnclaimedPackages();

        //populate the list view with packages
        adapter = new PackageListAdapter(currActivity, R.layout.adapter_home_layout, packages);
        packagesLV.setAdapter(adapter);

        //set the listener for the list view so that each entry is clickable
        setOnItemClickListenerWrapper();

        //set the listener for when the user attempts to search for a package based on name
        addTextChangedListenerWrapper();

        return inflatedView;
    }

    /**
     * This is a wrapper function for the setOnItemClickListener for each list view item. Once an
     * item is clicked, the package and its information will be saved to use for the checkout form.
     * The app will also switch to a new page, the checkout form.
     */
    public void setOnItemClickListenerWrapper() {
        packagesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the package clicked
                Package clickedPackage = (Package) packagesLV.getItemAtPosition(position);

                //get package information
                int currId = clickedPackage.getId();
                String currRecipient = clickedPackage.getRecipient();
                String currDate = clickedPackage.getDate();
                String currFirm = clickedPackage.getFirm();
                int currCount = clickedPackage.getCount();
                Log.v(TAG, "" + currId);

                //create a new intent, which will open up the checkout form
                Intent checkoutFormIntent = new Intent(currActivity, CheckoutFormActivity.class);

                //save package information in the intent for use within the checkout form
                checkoutFormIntent.putExtra("packageId", currId);
                checkoutFormIntent.putExtra("packageRecipient", currRecipient);
                checkoutFormIntent.putExtra("packageDate", currDate);
                checkoutFormIntent.putExtra("packageFirm", currFirm);
                checkoutFormIntent.putExtra("packageCount", currCount);

                //start activity, waiting for a result from the checkout form
                startActivityForResult(checkoutFormIntent, 1);
            }
        });
    }

    /**
     * This is a wrapper function for the addTextChangedListener on the Edit Text search bar. Once
     * the user types characters into the search bar, the items in the list view will filter accordingly.
     */
    public void addTextChangedListenerWrapper() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //when the text changes, get all packages after the filter is applied
                packages = getFilteredPackages(s.toString());

                //change the items in the list view with the updated list of packages
                adapter = new PackageListAdapter(currActivity, R.layout.adapter_home_layout, packages);
                packagesLV.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * This is called specifically when the user returns from the Checkout form page. This will
     * update the list view with packages that haven't been checked out yet.
     *
     * @param requestCode code before the checkout form was opened
     * @param resultCode code returned by the checkout form activity
     * @param data I'm not sure
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //clear the search bar
        searchET.setText("");

        //get the list of unclaimed packages
        packages = getUnclaimedPackages();

        //repopulate the list view
        adapter = new PackageListAdapter(currActivity, R.layout.adapter_home_layout, packages);
        packagesLV.setAdapter(adapter);
    }

    /**
     * Get a list of packages that haven't been picked up yet.
     *
     * @return an ArrayList of packages that haven't been picked up yet
     */
    public ArrayList<Package> getUnclaimedPackages() {
        final ArrayList<Package> packages = new ArrayList<>();

        //get all packages from the database
        Cursor dbData = dbHelper.getData();
        while(dbData.moveToNext()) {
            //get package information
            int id = dbData.getInt(0);
            String recipient = dbData.getString(1);
            String date = dbData.getString(2);
            String firm = dbData.getString(3);
            int count = dbData.getInt(4);

            //list of packages that haven't been picked up yet
            String pickup = dbData.getString(6);
            if(pickup.length() < 1) {
                Package currPackage = new Package(id, recipient, date, firm, count);
                packages.add(currPackage);
            }
        }

        return packages;
    }

    /**
     * Get a list of packages that contain the filter string in the recipient's name.
     *
     * @param filter string to use for the filter
     * @return
     */
    public ArrayList<Package> getFilteredPackages(String filter) {
        packages = new ArrayList<>();

        //get all packages in the database
        Cursor dbData = dbHelper.getData();
        while(dbData.moveToNext()) {
            //get package information
            int id = dbData.getInt(0);
            String recipient = dbData.getString(1);
            String date = dbData.getString(2);
            String firm = dbData.getString(3);
            int count = dbData.getInt(4);

            //check if the package's recipient name contains the filter string
            if(recipient.toLowerCase().contains(filter.toLowerCase())) {
                Package currPackage = new Package(id, recipient, date, firm, count);
                packages.add(currPackage);
            }
        }

        return packages;
    }
}
