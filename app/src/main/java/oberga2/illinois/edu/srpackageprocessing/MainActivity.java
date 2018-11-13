package oberga2.illinois.edu.srpackageprocessing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

/**
 * This class is the main entry point for this app. It handles navigation with a bottom navigation
 * bar and changes fragments depending on what fragment the user is currently on.
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * This is the main entry for this app. The Home fragment is displayed by default.
     * 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //display home fragment by default
        loadFragment(new HomeFragment());
    }

    /**
     * This function loads a fragment and updates the screen depending on if the fragment passed
     * through the parameter is a valid fragment.
     *
     * @param fragment the fragment to load
     * @return false if the fragment is null, true otherwise
     */
    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }

        return false;
    }

    /**
     * This function occurs once a navigation tab is selected. If the selected tab isn't the current
     * tab, the fragment will switch to the desired fragment.
     *
     * @param menuItem the navigation item that was selected
     * @return whether the fragment loads or not
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        int itemId = menuItem.getItemId();
        switch(itemId) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_process:
                fragment = new ProcessFragment();
                break;

            case R.id.navigation_checkout:
                fragment = new CheckoutFragment();
                break;

            case R.id.navigation_search:
                fragment = new SearchFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
