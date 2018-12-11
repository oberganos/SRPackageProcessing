package oberga2.illinois.edu.srpackageprocessing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PackageListAdapter extends ArrayAdapter<Package> {

    public static final String TAG = "PackageListAdapter";

    Context currContext;
    int currResource;

    TextView idTV;
    TextView recipientTV;
    TextView dateTV;
    TextView firmTV;

    /**
     * Constructor for the PackageListAdapter, which populates the list view with Package objects
     *
     * @param context conext
     * @param resource resource
     * @param objects objects to fill the listview with
     */
    public PackageListAdapter(Context context, int resource, ArrayList<Package> objects) {
        super(context, resource, objects);
        currContext = context;
        currResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get package information
        int id = getItem(position).getId();
        String recipient = getItem(position).getRecipient();
        String date = getItem(position).getDate();
        String firm = getItem(position).getFirm();
        //int count = getItem(position).getCount();

        //Package curr = new Package(id, recipient, date, firm, count);

        LayoutInflater inflater = LayoutInflater.from(currContext);
        convertView = inflater.inflate(currResource, parent, false);

        //get the elements for the activity
        idTV = (TextView) convertView.findViewById(R.id.idTV);
        recipientTV = (TextView) convertView.findViewById(R.id.recipientTV);
        dateTV = (TextView) convertView.findViewById(R.id.dateTV);
        firmTV = (TextView) convertView.findViewById(R.id.firmTV);

        //set the text view text with package information
        String packageId = "" + id;
        idTV.setText(packageId);
        recipientTV.setText(recipient);
        dateTV.setText(date);
        firmTV.setText(firm);

        return convertView;
    }
}
