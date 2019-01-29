package major.com.juice_android.model;

import java.util.List;

public class SongResponse
{
    private boolean error;
    private List<Song> songs;

    public SongResponse(boolean error, List<Song> songs)
    {
        this.error = error;
        this.songs = songs;
    }

    public boolean isError()
    {
        return error;
    }

    public List<Song> getSongs()
    {
        return songs;
    }
}
