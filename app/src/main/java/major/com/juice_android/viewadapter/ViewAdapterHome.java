package major.com.juice_android.viewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import major.com.juice_android.R;

public class ViewAdapterHome extends RecyclerView.Adapter<ViewAdapterHome.ViewHolder> {
    private ArrayList<String> artistname = new ArrayList<String>();
    private ArrayList<String> songname = new ArrayList<String>();
    private Context context;

    public ViewAdapterHome(Context context, ArrayList<String> artistname, ArrayList<String> songname) {
        this.artistname = artistname;
        this.songname = songname;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.song.setText(songname.get(position));
        holder.artist.setText(artistname.get(position));
    }

    @Override
    public int getItemCount() {
        return songname.size();
    }


    public class  ViewHolder extends RecyclerView.ViewHolder {
        CircularImageView image;
        TextView song,artist;


        public ViewHolder(View itemView) {
            super(itemView);
            song=itemView.findViewById(R.id.SongName);
            artist=itemView.findViewById(R.id.ArtistName);
        }
    }

    }



