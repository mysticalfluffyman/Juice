package major.com.juice_android.model;

import java.util.ArrayList;

public class GenreListResponse
{
    private boolean error;
    private String message;
    private ArrayList<Song> songs;

    public GenreListResponse(boolean error, String message, ArrayList<Song> songs)
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
