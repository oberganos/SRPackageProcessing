package oberga2.illinois.edu.srpackageprocessing;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import oberga2.illinois.edu.srpackageprocessing.database.ProcessorHelper;

/**
 * This class contains all of the functions for the Home fragment of this app.
 */
public class HomeFragment extends Fragment {

    Activity currActivity;

    ProcessorHelper dbHelper;

    ListView packagesLV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_home, null);

        currActivity = getActivity();
        currActivity.setTitle(R.string.title_home);

        return inflatedView;
    }
}