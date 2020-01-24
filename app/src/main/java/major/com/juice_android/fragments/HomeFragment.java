package major.com.juice_android.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import major.com.juice_android.MainActivity;
import major.com.juice_android.R;
import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.model.RecommendedSongResponse;
import major.com.juice_android.model.Song;
import major.com.juice_android.model.SongResponse;
import major.com.juice_android.viewadapter.RecommendedSongsAdapter;
import major.com.juice_android.viewadapter.ViewAdapterHome;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment
{
    RecyclerView mostplayedView, trendingView, recentlyaddedView, recommendedsongsView, recommendedartistsView, mostratedView;
    private ViewAdapterHome adapterHome;
    private RecommendedSongsAdapter adapterRecommended;
    private List<Song> songList;
    private List<Song> recommendedSongList;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ((MainActivity)getActivity()).setActionBarTitle("Home");

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mostplayedView = view.findViewById(R.id.mostplayed);
        LinearLayoutManager horizontalRecycler = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        mostplayedView.setLayoutManager(horizontalRecycler);

        sharedPreferences = this.getActivity().getSharedPreferences("loggedininfo", 0);
        String userid = sharedPreferences.getString("userid", "");
        String username = sharedPreferences.getString("username", "");
        Toast.makeText(this.getActivity(), "The current loggedin user is" + username + userid, Toast.LENGTH_SHORT).show();

        Call<SongResponse> call = RetrofitClient.getInstance().getApi().getSongs();

        call.enqueue(new Callback<SongResponse>()
        {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response)
            {
                songList = response.body().getSongs();
                adapterHome = new ViewAdapterHome(getActivity(), songList);
                mostplayedView.setAdapter(adapterHome);
            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t)
            {

            }
        });

        recommendedsongsView = view.findViewById(R.id.recommendedsongs);
        LinearLayoutManager recommendedRecycler = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendedsongsView.setLayoutManager(recommendedRecycler);

        //int testuserid = Integer.parseInt(userid);
//        Toast.makeText(getActivity(), "Test userid is : " + userid, Toast.LENGTH_SHORT).show();
//
//        Call<RecommendedSongResponse> callRecommended = RetrofitClient.getInstance().getApi().getRecommendedSongs(userid);
//        callRecommended.enqueue(new Callback<RecommendedSongResponse>()
//        {
//            @Override
//            public void onResponse(Call<RecommendedSongResponse> call, Response<RecommendedSongResponse> response)
//            {
//                recommendedSongList = response.body().getRecommendedSongs();
//                adapterRecommended = new RecommendedSongsAdapter(getActivity(), recommendedSongList);
//                recommendedsongsView.setAdapter(adapterRecommended);
//            }
//
//            @Override
//            public void onFailure(Call<RecommendedSongResponse> call, Throwable t)
//            {
//                Toast.makeText(getActivity(), "In failure", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
