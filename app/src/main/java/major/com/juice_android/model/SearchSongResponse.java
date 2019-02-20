package major.com.juice_android.model;

import java.util.ArrayList;
import java.util.List;

public class SearchSongResponse
{
    private boolean error;
    private String message;
    private ArrayList<Song> songs;

    public SearchSongResponse(boolean error, String message, ArrayList<Song> songs)
    {
        this.error = error;
        this.message = message;
        this.songs = songs;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
