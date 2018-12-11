package oberga2.illinois.edu.srpackageprocessing;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * This class contains all of the functions for the Search fragment of this app.
 */
public class SearchFragment extends Fragment {

    private static final String TAG = "SearchFragment";

    Activity currActivity;
    View currView;

    RadioGroup searchRG;
    RadioButton allRB;
    RadioButton pendingRB;
    RadioButton checkedOutRB;

    EditText recipientET;
    EditText firmET;
    EditText dateET;
    EditText idET;

    Button searchButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        currView = inflater.inflate(R.layout.fragment_search, null);
        currActivity = getActivity();
        currActivity.setTitle(R.string.title_search);

        //get all view elements
        getElements();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0 - all packages; 1 - pending packages; 2 - checked out packages
                int type = getSelectedRadioButtonId();

                String recipient = recipientET.getText().toString().toLowerCase();
                String firm = firmET.getText().toString().toLowerCase();
                String date = dateET.getText().toString();
                String id = idET.getText().toString();

                //Log.v(TAG, "" + type + " - " + recipient + " - " + firm + " - " + date + " - " + count);

                //create a new intent, which will open up the checkout form
                Intent searchResultIntent = new Intent(currActivity, SearchResultActivity.class);

                //save package information in the intent for use within the checkout form
                searchResultIntent.putExtra("searchType", type);
                searchResultIntent.putExtra("searchRecipient", recipient);
                searchResultIntent.putExtra("searchFirm", firm);
                searchResultIntent.putExtra("searchDate", date);
                searchResultIntent.putExtra("searchId", id);

                //start activity, waiting for a result from the checkout form
                startActivity(searchResultIntent);
            }
        });
        return currView;
    }

    /**
     * This function gets the index of the radio button selected in the radio group.
     *
     * @return the index of the selected radio button
     */
    public int getSelectedRadioButtonId() {
        int checkedId = searchRG.getCheckedRadioButtonId();
        RadioButton checkedRB = currView.findViewById(checkedId);
        int idx = searchRG.indexOfChild(checkedRB);
        return idx;
    }

    /**
     * This function finds all of the elements on the page and assigns them to their respective variables.
     */
    public void getElements() {
        searchRG = currView.findViewById(R.id.search_radio_group);
        allRB = currView.findViewById(R.id.search_all);
        pendingRB = currView.findViewById(R.id.search_pending);
        checkedOutRB = currView.findViewById(R.id.search_checked_out);

        recipientET = currView.findViewById(R.id.search_recipient);
        firmET = currView.findViewById(R.id.search_firm);
        dateET = currView.findViewById(R.id.search_date);
        idET = currView.findViewById(R.id.search_id);

        searchButton = currView.findViewById(R.id.search_submit);

    }
}
