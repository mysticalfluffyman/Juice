package major.com.juice_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.model.Genre;
import major.com.juice_android.model.GenreResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreActivity extends AppCompatActivity {
    ArrayList<Genre> idlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_show);

        Call<GenreResponse> call = RetrofitClient.getInstance().getApi().getMetal();

        call.enqueue(new Callback<GenreResponse>()
        {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response)
            {
                idlist =  response.body().getGenre();

            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                Toast.makeText(GenreActivity.this,"failed",Toast.LENGTH_SHORT).show();
            }



        });
        Genre a= idlist.get(2);
        int i=a.getSongid();
        Toast.makeText(GenreActivity.this,"songid:"+i,Toast.LENGTH_SHORT).show();

    }
}
