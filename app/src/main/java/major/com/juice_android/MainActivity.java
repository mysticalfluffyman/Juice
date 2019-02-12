package major.com.juice_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import major.com.juice_android.fragments.BrowseFragment;
import major.com.juice_android.fragments.DownloadsFragment;
import major.com.juice_android.fragments.HomeFragment;
import major.com.juice_android.fragments.PlaylistsFragment;
import major.com.juice_android.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private BrowseFragment browseFragment;
    private DownloadsFragment downloadsFragment;
    private PlaylistsFragment playlistsFragment;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        browseFragment = new BrowseFragment();
        downloadsFragment = new DownloadsFragment();
        playlistsFragment = new PlaylistsFragment();

        frameLayout = (FrameLayout)findViewById(R.id.fragment_container);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences("loggedininfo", 0);

        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.navigation_home:
                loadFragment(homeFragment);
                return true;

            case R.id.navigation_browse:
                loadFragment(browseFragment);
                return true;

            case R.id.navigation_search:
                loadFragment(searchFragment);
                return true;
            case R.id.navigation_downloads:
                loadFragment(downloadsFragment);
                return true;
            case R.id.navigation_playlist:
               loadFragment(playlistsFragment);
               return true;
               default:
                   return false;
        }
    }
    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

}
