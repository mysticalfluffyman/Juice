package major.com.juice_android;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.fragments.BrowseFragment;
import major.com.juice_android.fragments.DownloadsFragment;
import major.com.juice_android.fragments.HomeFragment;
import major.com.juice_android.fragments.PlaylistsFragment;
import major.com.juice_android.fragments.SearchFragment;
import major.com.juice_android.model.LoggidInUserResponse;
import major.com.juice_android.model.Song;
import major.com.juice_android.model.SongResponse;
import major.com.juice_android.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private ImageView floatimage,floatplay;
    private TextView floatartist,floatsong;

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private BrowseFragment browseFragment;
    private DownloadsFragment downloadsFragment;
    private PlaylistsFragment playlistsFragment;
    private MediaPlayer mediaPlayer;
    boolean playPause;
    private boolean come;

    private List<Song> song1;

    SharedPreferences sharedPreferences;
    public String currentUsernameValue;
    static String currentUserIdValue;
     String songid;
     String fromid;
     int id=1,from=1;
    User tempUser = new User();
    private static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 1;

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
        floatimage=findViewById(R.id.floatimage);
        floatartist=findViewById(R.id.floatartist);
        floatsong=findViewById(R.id.floatname);
        floatplay=findViewById(R.id.floatplay);

        mediaPlayer =new MediaPlayer();

        come =getIntent().getBooleanExtra("bol",false);
        if(come=true) {
            songid = getIntent().getStringExtra("songname");
            fromid = getIntent().getStringExtra("artistname");
            Log.d("artistname", "on"+fromid);
        }
        /*if (songid != null)
        {
            id=Integer.parseInt(songid);

        }*/
        Log.d("songname", "no"+songid);
        Log.d("come", "noo"+come);


        requestAppPermissions();


        sharedPreferences = getSharedPreferences("loggedininfo", 0);

        currentUsernameValue = sharedPreferences.getString("username", "");
        Toast.makeText(this, "The current userid is : " + currentUsernameValue, Toast.LENGTH_LONG).show();

        Call<LoggidInUserResponse> call = RetrofitClient.getInstance().getApi().getLoggedInUserInfo(currentUsernameValue);
        call.enqueue(new Callback<LoggidInUserResponse>()
        {

            @Override
            public void onResponse(Call<LoggidInUserResponse> call, Response<LoggidInUserResponse> response)
            {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                 tempUser = response.body().getUser();
                 editor.putString("userid", tempUser.getId());
                //Toast.makeText(MainActivity.this, "user id is " + tempUser.getId(), Toast.LENGTH_SHORT).show();
                 editor.apply();
                //Toast.makeText(MainActivity.this, tempUser.getId(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "Response success" + currentUserIdValue, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<LoggidInUserResponse> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "Response failure", Toast.LENGTH_SHORT).show();
            }

        });

       /* Call<SongResponse> call1 = RetrofitClient.getInstance().getApi().getSongs();

        call1.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call1, Response<SongResponse> response) {

                song1 =response.body().getSongs();

            }

            @Override
            public void onFailure(Call<SongResponse> call1, Throwable t) {
                Log.d("floatno", "onFailure: fuckinh"+call1);

            }
        });*/
        loadFragment(homeFragment);
        streamplay(songid,fromid);
//        switch(from)
//        {
//            case 1:
//                streamplay(songid,fromid);
//                break;
//
//            default :
//                streamplay("a","b");
//                break;
//        }


    }

    private void streamplay(String songname,String artistname) {

       // String  = songp.getSongurl();
       /* final Song songp = song1.get(idd);
        String SongTitle = songp.getTitle();
        String SongArtist = songp.getArtist();
        String SongAlbumurl = songp.getAlbumcoverurl();*/

        floatartist.setText(artistname);
        floatsong.setText(songname);
        //Picasso.with(this).load(SongAlbumurl).into(floatimage);

        floatplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    floatplay.setImageResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());

                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());

                        mediaPlayer.pause();
                        floatplay.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                    }

                    playPause = false;
                }


            }
        });
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

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] { android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE_REQUEST_CODE);

    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

}



