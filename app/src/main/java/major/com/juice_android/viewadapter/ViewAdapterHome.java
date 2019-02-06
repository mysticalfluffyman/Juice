package major.com.juice_android.viewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import major.com.juice_android.MusicPlayerActivity;
import major.com.juice_android.R;
import major.com.juice_android.model.Song;

public class ViewAdapterHome extends RecyclerView.Adapter<ViewAdapterHome.ViewHolder>
{
    private Context context;
    private List<Song> songList;


    public ViewAdapterHome(Context context, List<Song> songList)
    {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
       final Song song = songList.get(position);

       holder.song.setText(song.getTitle());
       holder.artist.setText(song.getArtist());
       Picasso.with(this.context).load(song.getAlbumcoverurl()).into(holder.imageView);
       holder.cardViewSong.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               Intent intent = new Intent(context, MusicPlayerActivity.class);
               intent.putExtra("songid", song.getId());
               intent.putExtra("artistname", song.getArtist());
               intent.putExtra("albumname", song.getAlbum());
               intent.putExtra("titlename", song.getTitle());
               intent.putExtra("albumurl", song.getAlbumcoverurl());
               intent.putExtra("musicurl", song.getSongurl());

               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount()
    {
        return songList.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder
    {
        CircularImageView image;
        TextView song, artist;
        ImageView imageView;
        CardView cardViewSong;


        public ViewHolder(View itemView)
        {
            super(itemView);

            song=itemView.findViewById(R.id.SongName);
            artist=itemView.findViewById(R.id.ArtistName);
            imageView = itemView.findViewById(R.id.albumCover);
            cardViewSong = (CardView)itemView.findViewById(R.id.cardViewSong);
        }
    }

    }



