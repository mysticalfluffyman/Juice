package major.com.juice_android.model;

public class Song
{
    private String id, title, artist, album, songurl, albumcoverurl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getSongurl() {
        return songurl;
    }

    public void setSongurl(String songurl) {
        this.songurl = songurl;
    }

    public String getAlbumcoverurl() {
        return albumcoverurl;
    }

    public void setAlbumcoverurl(String albumcoverurl) {
        this.albumcoverurl = albumcoverurl;
    }
}
