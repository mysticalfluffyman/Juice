

package major.com.juice_android;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Rating;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import major.com.juice_android.api.Api;
import major.com.juice_android.api.RetrofitClient;
import major.com.juice_android.model.DisplayRatingResponse;
import major.com.juice_android.model.Downloaded;
import major.com.juice_android.model.InsertRatingResponse;
//import major.com.juice_android.model.NormalizedRatingResponse;
import major.com.juice_android.model.Record;
import major.com.juice_android.model.Song;
import major.com.juice_android.model.SongResponse;
import major.com.juice_android.viewadapter.DownloadListAdapter;
import major.com.juice_android.viewadapter.ViewAdapterHome;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public class DownloadedPlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    ImageView albumArt,prev, play, next, download,shuffle;
    TextView timeDuration, timePosition, artistName, songName;
    SeekBar seekbar;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;
    private RatingBar ratingBar;
    String link = "http://192.168.0.105/Juice/songs/testfile.mp3";

    String currentSongId;
    String currentSongTitle;
    //String currentSongAlbum;
    String currentSongArtist;
    String currentSongAlbumUrl;
    String currentSongMusicUrl;

    int songid,count=0,id;
    private boolean initialStage = true;
    private boolean playPause;
    private ProgressDialog progressDialog;
    private ArrayList<Downloaded> songList=new ArrayList<Downloaded>();
    private ProgressDialog progressDialogForBuffering;
    private ProgressDialog mProgressDialog;
    private static final int MY_PERMISSION_REQUEST = 100;
    String name2;



    private String currentUsernameValue,currentPassword,KEY;

    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);



        artistName = findViewById(R.id.artistNameNowplaying);
        songName = findViewById(R.id.songNameNowplaying);

        sharedPreferences = getSharedPreferences("loggedininfo", 0);
        currentUsernameValue = sharedPreferences.getString("username", "");
        currentPassword=sharedPreferences.getString("password","");
        KEY=currentUsernameValue+currentPassword;
        Toast.makeText(this, "The current user is " + currentUsernameValue, Toast.LENGTH_SHORT).show();

        progressDialog = new ProgressDialog(this);
        albumArt = (ImageView) findViewById(R.id.albumArtNowplaying);
        prev = findViewById(R.id.previousBtn);
        next = findViewById(R.id.nextBtn);
        play = findViewById(R.id.playBtn);
        shuffle = findViewById(R.id.shuffleBtn);
        download = findViewById(R.id.downloadBtn);
        seekbar = findViewById(R.id.seekBar);
        timeDuration = findViewById(R.id.durationNowplaying);
        timePosition = findViewById(R.id.currentPosition);
        handler = new Handler();
        ratingBar = (RatingBar) findViewById(R.id.ratingNowplaying);

        currentSongId = getIntent().getStringExtra("songid");
        currentSongTitle = getIntent().getStringExtra("songname");
        //currentSongAlbum = getIntent().getStringExtra("albumname");
        currentSongArtist = getIntent().getStringExtra("artistname");
        currentSongAlbumUrl = getIntent().getStringExtra("arturl");
        currentSongMusicUrl = getIntent().getStringExtra("songurl");
        artistName.setText(currentSongArtist);
        songName.setText(currentSongTitle);
        songid=Integer.parseInt(currentSongId);
        id=songid;
        File song=null;
        File juice=null;

        Bitmap yourSelectedImage1 = BitmapFactory.decodeFile(currentSongAlbumUrl);
        albumArt.setImageBitmap(yourSelectedImage1);

        mediaPlayer = new MediaPlayer();
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        play.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        download.setOnClickListener(this);
        prev.setOnLongClickListener(this);
        next.setOnLongClickListener(this);

        addtolist();


        displayRating(songid);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekbar.setMax(mediaPlayer.getAudioSessionId());
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                changeTime();
                changeSeekbar();


            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            boolean userTouch;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekbar.setProgress(progress);
                    changeSeekbar();
                    changeTime();


                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                userTouch = false;
                changeSeekbar();
                changeTime();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                userTouch = true;

            }


        });


        String name=currentSongArtist+"-"+currentSongTitle+".mp3";
        Log.d("currenturl", ""+currentSongMusicUrl);
        Log.d("currentname", ""+name);

        String read=readfile(currentSongMusicUrl);
        //Log.d("readfile", "no"+read);

        String decrypted = null;

        try {
            decrypted = Decrypt(read);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Toast.makeText(this, "UNAUTHORIZED", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(DownloadedPlayerActivity.this,MainActivity.class);
            startActivity(intent);
        }


        //Log.d("decrypted", "null"+decrypted);


            String playurl = unbase(decrypted, name);
            Log.d("internalurl", ""+playurl);



        String purl=playurl+"/"+name;
        if(name2 != null) {
            delete(name2);
        }
        new Player().execute(purl);
        name2=name;

        play.setImageResource(R.drawable.ic_pause_black_24dp);
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        changeSeekbar();
    }

    private void addtolist() {
        String path = Environment.getExternalStorageDirectory().toString()+"/JUICE";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles(
                new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        return s.endsWith(currentUsernameValue+".juice");
                    }
                }
        );
        songList.clear();
        Log.d("Files", "Size: "+ files.length);
        int i=0;
        for(File file :files){
            //Downloaded downloaded;
            Log.d("filecount", " :"+i);
            String a=String.valueOf(i);
            songList.add(new Downloaded(file.getName(),file.getPath(),a));
            i++;
        }
    }

    private void delete(String name) {
        File file= new File(getFilesDir(),name );
        boolean a=deleteFile(name);
        Log.d("deleted", ""+a);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String Decrypt(String read) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Log.d("Din", ""+read);
        String decrypted = AESencryption.Decrypt(read,KEY);

        return decrypted;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String unbase(String decrypted, String name)  {


        FileOutputStream fos =null;
        try
        {
            // Converting a Base64 String into Image byte array
            fos=openFileOutput(name,MODE_PRIVATE);
            byte[] imageByteArray = Base64.getDecoder().decode(decrypted);
            fos.write(imageByteArray);
        } catch (FileNotFoundException e)
        {
            System.out.println("Image not found" + e);
        } catch (IOException ioe)
        {
            System.out.println("Exception while reading the Image " + ioe);
        }

        String dirplay= getBaseContext().getFilesDir().toString();
        return dirplay;
    }

    private String readfile(String currentSongMusicUrl) {
        StringBuilder stringBuilder =new StringBuilder();
        //File file=new File(currentSongMusicUrl);
        try {
            File file=new File(currentSongMusicUrl);
            FileInputStream fis = new FileInputStream(file);
            if (fis!=null){
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br=new BufferedReader(isr);
                String line=null;
                while ((line=br.readLine()) != null){
                    stringBuilder.append(line + "\n");

                }

                fis.close();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("readfile", "no="+stringBuilder);

        return stringBuilder.toString();
    }


    private void displayRating( int songid)
    {
        ratingBar.setNumStars(0);

    }

    private void insertRating(float v)
    {

    }

    private void changeSeekbar() {
        seekbar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    int mediaPos_new = mediaPlayer.getCurrentPosition();
                    int mediaMax_new = mediaPlayer.getDuration();
                    seekbar.setMax(mediaMax_new);
                    seekbar.setProgress(mediaPos_new);
                    changeSeekbar();
                    changeTime();
                }
            };

            handler.postDelayed(runnable, 100);

        } else {

        }
    }

    private void changeTime() {

        int duration = mediaPlayer.getDuration();
        int current = mediaPlayer.getCurrentPosition();
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
        );
        String currentTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(current),
                TimeUnit.MILLISECONDS.toSeconds(current) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(current))
        );
        timePosition.setText(currentTime);
        timeDuration.setText(time);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.playBtn:
                if (!mediaPlayer.isPlaying())
                {    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                    changeSeekbar();
                }

                else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        changeSeekbar();
                        mediaPlayer.pause();
                        play.setImageResource(R.drawable.ic_play_arrow_black_24dp);

                    }

                    playPause = false;
                }


                break;
            case R.id.previousBtn:

                if(songid==0){
                    int a=songList.size();
                    songid=a;
                }
                songid=songid-1;
                final Downloaded songp=songList.get(songid);

                String prevSongMusicUrl=songp.getUrl();
                String prevSongTitle=songp.getName();

                final String artistname=prevSongTitle.substring(0,prevSongTitle.indexOf("-"));
                final String songname=prevSongTitle.substring(prevSongTitle.indexOf("-")+1,prevSongTitle.indexOf("-"+currentUsernameValue+".juice"));

                String namejpeg=prevSongTitle.replace("-"+currentUsernameValue+".juice",".jpeg");
                final String artpath = Environment.getExternalStorageDirectory().toString()+"/JUICE"+"/ART/"+namejpeg;
                Bitmap yourSelectedImage1 = BitmapFactory.decodeFile(artpath);
                albumArt.setImageBitmap(yourSelectedImage1);

                artistName.setText(artistname);
                songName.setText(songname);

                displayRating(songid);

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                changeSeekbar();

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                new Player().execute(prevSongMusicUrl);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
                {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                    {
                        Toast.makeText(DownloadedPlayerActivity.this, v+"", Toast.LENGTH_SHORT).show();
                        insertRating(v);

                    }
                });

                break;
            case R.id.nextBtn:

                songid=songid+1;
                if(songid==songList.size()){
                    songid=0;
                }
                final Downloaded song=songList.get(songid);
                String nextSongMusicUrl=song.getUrl();
                String nextSongTitle=song.getName();
                Log.d("naextname", "name: "+nextSongTitle);
                final String nextartistname=nextSongTitle.substring(0,nextSongTitle.indexOf("-"));
                final String nextsongname=nextSongTitle.substring(nextSongTitle.indexOf("-")+1,nextSongTitle.indexOf("-"+currentUsernameValue+".juice"));

                String nextnamejpeg=nextSongTitle.replace("-"+currentUsernameValue+".juice",".jpeg");
                final String nextartpath = Environment.getExternalStorageDirectory().toString()+"/JUICE"+"/ART/"+nextnamejpeg;
                Bitmap yourSelectedImage2 = BitmapFactory.decodeFile(nextartpath);
                albumArt.setImageBitmap(yourSelectedImage2);



                artistName.setText(nextartistname);
                songName.setText(nextsongname);

                Toast.makeText(DownloadedPlayerActivity.this,""+songid,Toast.LENGTH_SHORT).show();

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                changeSeekbar();

                displayRating(songid);

                new Player().execute(nextSongMusicUrl);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
                {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                    {
                        Toast.makeText(DownloadedPlayerActivity.this, v+"", Toast.LENGTH_SHORT).show();
                        insertRating(v);

                    }
                });

                break;
            case R.id.shuffleBtn:
                Toast.makeText(this, "Shuffle is Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.downloadBtn:

                //downloadSong(currentSongMusicUrl);
                Toast.makeText(this, "Download is Pressed", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10000);
                changeSeekbar();
                Toast.makeText(this, "Seeked +10s", Toast.LENGTH_SHORT).show();
                break;
            case R.id.previousBtn:
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 10000);
                changeTime();
                Toast.makeText(this, "Seeked -105s", Toast.LENGTH_SHORT).show();
                break;


        }
        return false;
    }

    class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            mediaPlayer= (MediaPlayer) MediaPlayer.create(getApplicationContext(), Uri.parse(strings[0]));
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());

            changeSeekbar();
            prepared = true;
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (progressDialog.isShowing()) {
                progressDialog.cancel();
            }


            mediaPlayer.start();
            initialStage = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Buffering...");
            progressDialog.show();

        }
    }


}


