package major.com.juice_android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import major.com.juice_android.viewadapter.ViewAdapterHome;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    private ArrayList<String> artistname = new ArrayList<>();
    private ArrayList<String> songname = new ArrayList<>();
    private ArrayList<String> coverimage = new ArrayList<>();

    RecyclerView mostplayed,trending,recentlyadded,recommendedsongs,recommendedartists,mostrated;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loadstuff();
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        mostplayed= rootview.findViewById(R.id.mostplayed);
        trending = rootview.findViewById(R.id.trending);
        recentlyadded = rootview.findViewById(R.id.recentlyadded);
        recommendedsongs = rootview.findViewById(R.id.recommendedsongs);
        recommendedartists = rootview.findViewById(R.id.recommendedartists);
        mostrated = rootview.findViewById(R.id.mostratedplaylist);

        initRecyclerView_mostplayed();
        initRecyclerView_trending();
        initRecyclerView_recentlyadded();
        initRecyclerView_recommendedsongs();
        initRecyclerView_recommendedartists();
        initRecyclerView_mostrated();


        return rootview;
    }

    private void loadstuff() {
        artistname.add("tool");
        songname.add("third eye");

        artistname.add("tool");
        songname.add("fourth eye");

        artistname.add("tool");
        songname.add("fifth eye");

        artistname.add("tool");
        songname.add("sixth eye");

        artistname.add("tool");
        songname.add("seventh eye");

        artistname.add("tool");
        songname.add("eighth eye");


    }
    private void initRecyclerView_mostplayed() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mostplayed.setLayoutManager(linearLayoutManager);
        ViewAdapterHome viewAdapterRecent = new ViewAdapterHome(this.getContext(), artistname, songname);
        mostplayed.setAdapter(viewAdapterRecent);
        Log.d(TAG,"chelsea1");
    }
    private void initRecyclerView_trending(){
        LinearLayoutManager linearLayoutManagera=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        trending.setLayoutManager(linearLayoutManagera);
        ViewAdapterHome viewAdaptermostplayeda=new ViewAdapterHome(this.getContext(),artistname,songname);
        trending.setAdapter(viewAdaptermostplayeda);
        Log.d(TAG,"chelsea2");

    }
    private void initRecyclerView_recentlyadded(){
        LinearLayoutManager linearLayoutManagera=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recentlyadded.setLayoutManager(linearLayoutManagera);
        ViewAdapterHome viewAdaptermostplayeda=new ViewAdapterHome(this.getContext(),artistname,songname);
        recentlyadded.setAdapter(viewAdaptermostplayeda);
        Log.d(TAG,"chelsea3");

    }
    private void initRecyclerView_recommendedsongs(){
        LinearLayoutManager linearLayoutManagera=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recommendedsongs.setLayoutManager(linearLayoutManagera);
        ViewAdapterHome viewAdaptermostplayeda=new ViewAdapterHome(this.getContext(),artistname,songname);
        recommendedsongs.setAdapter(viewAdaptermostplayeda);
        Log.d(TAG,"chelsea4");

    }
    private void initRecyclerView_recommendedartists(){
        LinearLayoutManager linearLayoutManagera=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recommendedartists.setLayoutManager(linearLayoutManagera);
        ViewAdapterHome viewAdaptermostplayeda=new ViewAdapterHome(this.getContext(),artistname,songname);
        recommendedartists.setAdapter(viewAdaptermostplayeda);
        Log.d(TAG,"chelsea5");

    }
    private void initRecyclerView_mostrated(){
        LinearLayoutManager linearLayoutManagera=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mostrated.setLayoutManager(linearLayoutManagera);
        ViewAdapterHome viewAdaptermostplayeda=new ViewAdapterHome(this.getContext(),artistname,songname);
        mostrated.setAdapter(viewAdaptermostplayeda);
        Log.d(TAG,"chelsea6");

    }
}
