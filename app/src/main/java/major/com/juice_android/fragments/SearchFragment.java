package major.com.juice_android.fragments;

import android.content.Context;
import  android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import major.com.juice_android.MainActivity;
import major.com.juice_android.R;
import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.model.SearchSongResponse;
import major.com.juice_android.model.Song;
import major.com.juice_android.viewadapter.SearchListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment
{
    ListView listView;
    private ArrayList<Song> songList;
    private SearchListAdapter searchListAdapter;
    String store;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ((MainActivity)getActivity()).setActionBarTitle("Search");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.searchResults);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_icon);

        SearchView searchView =(SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                //fetchSearchedData(s);

                Call<SearchSongResponse> call = RetrofitClient.getInstance().getApi().searchSongs(s);
                call.enqueue(new Callback<SearchSongResponse>()
                {
                    @Override
                    public void onResponse(Call<SearchSongResponse> call, Response<SearchSongResponse> response)
                    {
                        songList = response.body().getSongs();
                        for (int i = 0; i<songList.size(); i++)
                        {
                            Log.d("responsedata", songList.get(i).getTitle());
                        }

                        listView.setAdapter(new SearchListAdapter(getActivity(), songList));

                        if (getContext() == null)
                        {
                            store = "context is null";
                        }
                        if (songList == null)
                        {
                            Toast.makeText(getContext(), "Arraylist is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchSongResponse> call, Throwable t)
                    {

                    }
                });

                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void fetchSearchedData(String s)
    {
        Call<SearchSongResponse> call = RetrofitClient.getInstance().getApi().searchSongs(s);
        call.enqueue(new Callback<SearchSongResponse>()
        {
            @Override
            public void onResponse(Call<SearchSongResponse> call, Response<SearchSongResponse> response)
            {
                songList = response.body().getSongs();
                //store = response.body().getMessage();
                for (int i = 0; i<songList.size(); i++)
                {
                    Log.d("responsedata", songList.get(i).getTitle());
                }

                listView.setAdapter(new SearchListAdapter(getActivity(), songList));

                if (getContext() == null)
                {
                    store = "context is null";
                }
                if (songList == null)
                {
                    Toast.makeText(getContext(), "Arraylist is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchSongResponse> call, Throwable t)
            {

            }
        });

    }
}
