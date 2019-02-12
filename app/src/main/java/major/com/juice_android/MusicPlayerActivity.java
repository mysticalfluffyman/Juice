package major.com.juice_android;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    ImageButton prev, play, next, shuffle, download;
    ImageView albumArt;
    TextView timeDuration, timePosition, artistName, songName;
    SeekBar seekbar;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;
    String link = "http://192.168.0.105/Juice/songs/testfile.mp3";

    String currentSongId;
    String currentSongTitle;
    String currentSongAlbum;
    String currentSongArtist;
    String currentSongAlbumUrl;
    String currentSongMusicUrl;

    private boolean initialStage = true;
    private boolean playPause;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        artistName = findViewById(R.id.artistNameNowplaying);
        songName = findViewById(R.id.songNameNowplaying);

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

        currentSongId = getIntent().getStringExtra("songid");
        currentSongTitle = getIntent().getStringExtra("titlename");
        currentSongAlbum = getIntent().getStringExtra("albumname");
        currentSongArtist = getIntent().getStringExtra("artistname");
        currentSongAlbumUrl = getIntent().getStringExtra("albumurl");
        currentSongMusicUrl = getIntent().getStringExtra("musicurl");

        artistName.setText(currentSongArtist);
        songName.setText(currentSongTitle);


        //mediaPlayer = MediaPlayer.create(this, R.raw.test);

        Picasso.with(this).load(currentSongAlbumUrl).into(albumArt);

        mediaPlayer = new MediaPlayer();
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        play.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        download.setOnClickListener(this);
        prev.setOnLongClickListener(this);
        next.setOnLongClickListener(this);

//
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!playPause) {
//                    if (initialStage) {
//                        new Player().execute(currentSongMusicUrl);
//
//
//                    } else {
//                        if (!mediaPlayer.isPlaying())
//                            mediaPlayer.start();
//                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
//
//                    }
//
//                    playPause = true;
//
//                }
//                else {
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
//                        mediaPlayer.pause();
//
//                    }
//                    playPause = false;
//                }
//            }
//        });

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

//                if (mediaPlayer.isPlaying())
//                {
//                    mediaPlayer.pause();
//                    //paused state
//
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
//                    changeSeekbar();
//                    play.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
//                    Toast.makeText(this, "Paused", Toast.LENGTH_SHORT).show();
//
//                }
//                else
//                {
//                    mediaPlayer.start();
//                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()); //play state
//                    changeSeekbar();
//                    play.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
//
//                    Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
//
//                }
                if (!playPause) {
                    if (initialStage) {
                        new Player().execute(currentSongMusicUrl);


                    } else {
                        if (!mediaPlayer.isPlaying())
                            mediaPlayer.start();
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        changeSeekbar();


                    }

                    playPause = true;

                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        changeSeekbar();
                        mediaPlayer.pause();

                    }

                    playPause = false;
                }


                break;
            case R.id.previousBtn:
                Toast.makeText(this, "Previous Button is Pressed    ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nextBtn:
                Toast.makeText(this, "Next Button is Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shuffleBtn:
                Toast.makeText(this, "Shuffle is Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.downloadBtn:
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

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        initialStage = true;
                        playPause = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                    }
                });

                mediaPlayer.prepare();
                prepared = true;

            } catch (Exception e) {
                Log.e("MyAudioStreamingApp", e.getMessage());
                prepared = false;
            }

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
