package major.com.juice_android;

import android.content.Context;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.model.GenreListResponse;
import major.com.juice_android.model.Song;
import major.com.juice_android.viewadapter.GenreListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreListActivity extends AppCompatActivity
{
    private String genreid;
    ListView listView;
    private ArrayList<Song> songList;
    private Toolbar mToolbar;
    private String genreName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        listView = findViewById(R.id.genreList);
        genreName = new String();

        genreid = getIntent().getStringExtra("genreid");
        getSupportActionBar().setTitle("sdlkfjsdl");

        if(genreid.equals("1"))
        {
            genreName = "Rock";
        }

        if(genreid.equals("2"))
        {
            genreName = "Metal";
        }

        if(genreid.equals("3"))
        {
            genreName = "Hip-Hop";
        }

        if(genreid.equals("4"))
        {
            genreName = "Folk";
        }

        if(genreid.equals("5"))
        {
            genreName = "Electronic";
        }

        if(genreid.equals("6"))
        {
            genreName = "90's";
        }

        if(genreid.equals("7"))
        {
            genreName = "Classical";
        }

        Toast.makeText(this, "Genre name is " + genreName, Toast.LENGTH_SHORT).show();

        getSupportActionBar().setTitle(genreName);

        Call<GenreListResponse> call = RetrofitClient.getInstance().getApi().getGenreListSongs(Integer.parseInt(genreid));
        call.enqueue(new Callback<GenreListResponse>()
        {
            @Override
            public void onResponse(Call<GenreListResponse> call, Response<GenreListResponse> response)
            {
                songList = response.body().getSongs();
                //Toast.makeText(GenreListActivity.this, "Got response" + songList.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                listView.setAdapter(new GenreListAdapter(GenreListActivity.this, songList));
            }

            @Override
            public void onFailure(Call<GenreListResponse> call, Throwable t)
            {

            }
        });

    }

}
