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

        dbHelper = new ProcessorHelper(currActivity);

        packagesLV = inflatedView.findViewById(R.id.packageListView);

        ArrayList<Package> packages = getAllPackages();

        PackageListAdapter adapter = new PackageListAdapter(currActivity, R.layout.adapter_home_layout, packages);
        packagesLV.setAdapter(adapter);

        return inflatedView;
    }

    /**
     * Get a list of packages that haven't been picked up yet.
     *
     * @return an ArrayList of packages that haven't been picked up yet
     */
    public ArrayList<Package> getAllPackages() {
        final ArrayList<Package> packages = new ArrayList<>();

        Cursor dbData = dbHelper.getData();
        while(dbData.moveToNext()) {
            int id = dbData.getInt(0);
            String recipient = dbData.getString(1);
            String date = dbData.getString(2);
            String firm = dbData.getString(3);
            int count = dbData.getInt(4);

            Package currPackage = new Package(id, recipient, date, firm, count);
            packages.add(currPackage);

        }

        return packages;
    }
}
