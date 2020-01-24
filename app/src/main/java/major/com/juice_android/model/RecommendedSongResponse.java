package major.com.juice_android.model;

import java.util.ArrayList;
import java.util.List;

public class RecommendedSongResponse
{
    private boolean error;
    private ArrayList<Song> songs;

    public RecommendedSongResponse(boolean error, ArrayList<Song> songs)
    {
        this.error = error;
        this.songs = songs;
    }

    public boolean isError()
    {
        return error;
    }

    public List<Song> getRecommendedSongs()
    {
        return songs;
    }
}
