package major.com.juice_android.viewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import major.com.juice_android.MusicPlayerActivity;
import major.com.juice_android.R;
import major.com.juice_android.model.Song;

public class SearchListAdapter extends ArrayAdapter<Song>
{
    Context context;

    public SearchListAdapter(@NonNull Context context, ArrayList<Song> list)
    {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_layout, null);

        CardView cardView =(CardView)view.findViewById(R.id.searchCardView);
        TextView searchTitle = (TextView)view.findViewById(R.id.searchTitleText);
        TextView searchArtist = (TextView)view.findViewById(R.id.searchArtistText);
        ImageView searchImage = (ImageView)view.findViewById(R.id.searchImage);

        final Song song =(Song)getItem(position);

        searchTitle.setText(song.getTitle());
        searchArtist.setText(song.getArtist());
        Picasso.with(this.context).load(song.getAlbumcoverurl()).into(searchImage);

        cardView.setOnClickListener(new View.OnClickListener()
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

        return view;
    }
}
