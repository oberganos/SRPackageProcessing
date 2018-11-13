package oberga2.illinois.edu.srpackageprocessing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class contains all of the functions for the Checkout fragment of this app.
 */
public class CheckoutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_checkout);

        return inflater.inflate(R.layout.fragment_checkout, null);
    }
}