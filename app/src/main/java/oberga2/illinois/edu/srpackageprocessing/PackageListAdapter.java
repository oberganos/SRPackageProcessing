package oberga2.illinois.edu.srpackageprocessing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PackageListAdapter extends ArrayAdapter<Package> {

    public static final String TAG = "PackageListAdapter";

    Context currContext;
    int currResource;

    public PackageListAdapter(Context context, int resource, ArrayList<Package> objects) {
        super(context, resource, objects);
        currContext = context;
        currResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int id = getItem(position).getId();
        String recipient = getItem(position).getRecipient();
        String firm = getItem(position).getFirm();

        Package curr = new Package(id, recipient, firm);

        LayoutInflater inflater = LayoutInflater.from(currContext);
        convertView = inflater.inflate(currResource, parent, false);

        TextView idTV = (TextView) convertView.findViewById(R.id.idTV);
        TextView recipientTV = (TextView) convertView.findViewById(R.id.recipientTV);
        TextView firmTV = (TextView) convertView.findViewById(R.id.firmTV);

        idTV.setText("" + id);
        recipientTV.setText(recipient);
        firmTV.setText(firm);

        return convertView;
    }
}
