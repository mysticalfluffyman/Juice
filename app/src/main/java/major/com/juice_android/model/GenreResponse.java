package major.com.juice_android.model;

import java.util.ArrayList;
import java.util.List;

public class GenreResponse {
    private boolean error;
    private ArrayList<Genre> songs;

    public GenreResponse(boolean error, ArrayList<Genre> songs)
    {
        this.error = error;
        this.songs = (ArrayList<Genre>) songs;
    }

    public boolean isError()
    {
        return error;
    }

    public ArrayList<Genre> getGenre()
    {
        return songs;
    }
}
