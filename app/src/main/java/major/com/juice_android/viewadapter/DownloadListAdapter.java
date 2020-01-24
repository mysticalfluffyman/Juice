package major.com.juice_android.viewadapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import major.com.juice_android.DownloadedPlayerActivity;
import major.com.juice_android.MusicPlayerActivity;
import major.com.juice_android.R;
import major.com.juice_android.model.Downloaded;

public class DownloadListAdapter extends ArrayAdapter<Downloaded> {
    Context context;
    SharedPreferences sharedPreferences;
    String currentUsername;
    public DownloadListAdapter(@NonNull Context context,  ArrayList<Downloaded>list) {
        super(context,0,list);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_result_layout,null);
        sharedPreferences=view.getContext().getSharedPreferences("loggedininfo",0);
        currentUsername=sharedPreferences.getString("username","");
        CardView cardView =(CardView)view.findViewById(R.id.searchCardView);
        TextView searchTitle = (TextView)view.findViewById(R.id.searchTitleText);
        TextView searchArtist = (TextView)view.findViewById(R.id.searchArtistText);
        ImageView searchImage = (ImageView)view.findViewById(R.id.searchImage);
        final Downloaded dwn=getItem(position);

        String namemp3=dwn.getName();
        Log.d("namemp3", " "+namemp3);
        final String artistname=namemp3.substring(0,namemp3.indexOf("-"));
        final String songname=namemp3.substring(namemp3.indexOf("-")+1,namemp3.indexOf("-"+currentUsername+".juice"));
        searchTitle.setText(songname);
        searchArtist.setText(artistname);
        String namejpeg=namemp3.replace("-"+currentUsername+".juice",".jpeg");
        final String artpath = Environment.getExternalStorageDirectory().toString()+"/JUICE"+"/ART/"+namejpeg;
        Bitmap yourSelectedImage1 = BitmapFactory.decodeFile(artpath);
        searchImage.setImageBitmap(yourSelectedImage1);
        Log.d("arturl",""+artpath);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DownloadedPlayerActivity.class);

                Log.d("sondidd", "no"+dwn.getId());

                intent.putExtra("songid",dwn.getId());
                intent.putExtra("songname", songname);
                intent.putExtra("artistname",artistname);
                intent.putExtra("songurl", dwn.getUrl());
                intent.putExtra("arturl",artpath);
                context.startActivity(intent);
            }
        });
        return view;

    }
}
