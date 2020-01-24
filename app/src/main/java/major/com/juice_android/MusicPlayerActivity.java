package major.com.juice_android;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
import major.com.juice_android.model.InsertRatingResponse;
//import major.com.juice_android.model.NormalizedRatingResponse;
import major.com.juice_android.model.Record;
import major.com.juice_android.model.Song;
import major.com.juice_android.model.SongResponse;
import major.com.juice_android.viewadapter.ViewAdapterHome;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

import static major.com.juice_android.R.drawable.skymove;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    ImageView albumArt, prev, play, next, download, shuffle;
    TextView timeDuration, timePosition, artistName, songName;
    SeekBar seekbar;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;
    private RatingBar ratingBar;
    String link = "http://192.168.0.105/Juice/songs/testfile.mp3";

    String currentSongId;
    String currentSongTitle;
    String currentSongAlbum;
    String currentSongArtist;
    String currentSongAlbumUrl;
    String currentSongMusicUrl;
    String  tobase="";
    String notid="0";



    int songid, count = 0, id;
    private boolean initialStage = true;
    private boolean playPause;
    private ProgressDialog progressDialog;
    private ArrayList<Song> songList;
    private ProgressDialog progressDialogForBuffering;
    private ProgressDialog mProgressDialog;
    private static final int MY_PERMISSION_REQUEST = 100;


    private String currentUsernameValue,currentPassword,KEY,currentUserid;
    private  String base64Image;

    SharedPreferences sharedPreferences;

    /*notification*/

    private NotificationManagerCompat mNotificationManagerCompat;


    //anotherway
    private final String CHANNEL_ID = "Notification";
    private final int NOTIFICATION_ID = 666;

    //attempt

    private NotificationUtils mNotificationUtils,nNotificationUtils;


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
        currentSongTitle = getIntent().getStringExtra("titlename");
        currentSongAlbum = getIntent().getStringExtra("albumname");
        currentSongArtist = getIntent().getStringExtra("artistname");
        currentSongAlbumUrl = getIntent().getStringExtra("albumurl");
        currentSongMusicUrl = getIntent().getStringExtra("musicurl");
        artistName.setText(currentSongArtist);
        songName.setText(currentSongTitle);

        songid = Integer.parseInt(currentSongId);
        id = songid;
        File song = null;
        File juice = null;

        mNotificationUtils = new NotificationUtils(this);
        nNotificationUtils = new NotificationUtils(this);

        Call<SongResponse> call = RetrofitClient.getInstance().getApi().getSongs();

        call.enqueue(new Callback<SongResponse>() {
            @Override
            public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                songList = (ArrayList<Song>) response.body().getSongs();

            }

            @Override
            public void onFailure(Call<SongResponse> call, Throwable t) {

            }
        });


        //mediaPlayer = MediaPlayer.create(this, R.raw.test);


        Picasso.with(this).load(currentSongAlbumUrl).into(albumArt);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(mService.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        play.setOnClickListener(this);
        shuffle.setOnClickListener(this);
        download.setOnClickListener(this);
        prev.setOnLongClickListener(this);
        next.setOnLongClickListener(this);


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

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(MusicPlayerActivity.this, v + "", Toast.LENGTH_SHORT).show();
                insertRating(v);

            }
        });



        new Player().execute(currentSongMusicUrl);
        Notification.Builder nb = mNotificationUtils.getAndroidChannelNotification(currentSongTitle, currentSongArtist, currentSongAlbumUrl);
        mNotificationUtils.getManager().notify(101, nb.build());
        play.setImageResource(R.drawable.ic_pause_black_24dp);
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        changeSeekbar();
    }


    private void displayRating(int songid) {
        ratingBar.setNumStars(0);
        Call<DisplayRatingResponse> call = RetrofitClient.getInstance().getApi().displayRating(songid, currentUsernameValue);
        call.enqueue(new Callback<DisplayRatingResponse>() {
            @Override
            public void onResponse(Call<DisplayRatingResponse> call, Response<DisplayRatingResponse> response) {
                DisplayRatingResponse displayRatingResponse = response.body();

                Record record = displayRatingResponse.getRecord();

                ratingBar.setRating(Float.parseFloat(record.getRating()));
                Toast.makeText(MusicPlayerActivity.this, "", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<DisplayRatingResponse> call, Throwable t) {

            }
        });
    }

    private void insertRating(float v) {
        Call<InsertRatingResponse> call = RetrofitClient.getInstance().getApi().insertRating(songid, currentUsernameValue, v);
        call.enqueue(new Callback<InsertRatingResponse>() {
            @Override
            public void onResponse(Call<InsertRatingResponse> call, Response<InsertRatingResponse> response) {
                InsertRatingResponse insertRatingResponse = response.body();

            }

            @Override
            public void onFailure(Call<InsertRatingResponse> call, Throwable throwable) {

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.playBtn:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.ic_pause_black_24dp);
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                    changeSeekbar();
                } else {
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
                if (songid == 0) {
                    int a = songList.size();
                    songid = a;
                }
                songid = songid - 1;
                final Song songp = songList.get(songid);
                String prevSongMusicUrl = songp.getSongurl();
                String prevSongTitle = songp.getTitle();
                String prevSongArtist = songp.getArtist();
                String prevSongAlbumurl = songp.getAlbumcoverurl();
                artistName.setText(prevSongArtist);
                songName.setText(prevSongTitle);
                Picasso.with(this).load(prevSongAlbumurl).into(albumArt);

                Notification.Builder nb = mNotificationUtils.getAndroidChannelNotification(prevSongTitle, prevSongArtist, prevSongAlbumurl);
                mNotificationUtils.getManager().notify(101, nb.build());

                displayRating(songid);

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                changeSeekbar();

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                new Player().execute(prevSongMusicUrl);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        Toast.makeText(MusicPlayerActivity.this, v + "", Toast.LENGTH_SHORT).show();
                        insertRating(v);

                    }
                });

                break;
            case R.id.nextBtn:

                songid = songid + 1;
                if (songid == songList.size()) {
                    songid = 0;
                }
                final Song song = songList.get(songid);
                String nextSongMusicUrl = song.getSongurl();
                String nextSongTitle = song.getTitle();
                String nextSongArtist = song.getArtist();
                String nextSongAlbumurl = song.getAlbumcoverurl();
                artistName.setText(nextSongArtist);
                songName.setText(nextSongTitle);
                Picasso.with(this).load(nextSongAlbumurl).into(albumArt);

                 Notification.Builder nb1 = nNotificationUtils.getAndroidChannelNotification(nextSongTitle,nextSongArtist,nextSongAlbumurl);

                //nb = mNotificationUtils.getAndroidChannelNotification(nextSongTitle, nextSongArtist, nextSongAlbumurl);
                nNotificationUtils.getManager().notify(101, nb1.build());

                Toast.makeText(MusicPlayerActivity.this, "" + songid, Toast.LENGTH_SHORT).show();

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                changeSeekbar();

                displayRating(songid);

                new Player().execute(nextSongMusicUrl);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        Toast.makeText(MusicPlayerActivity.this, v + "", Toast.LENGTH_SHORT).show();
                        insertRating(v);

                    }
                });

                break;
            case R.id.shuffleBtn:
                Toast.makeText(this, "Shuffle is Pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.downloadBtn:

                downloadSong(currentSongMusicUrl);
                downloadAlbumArt(currentSongAlbumUrl);


                /*
                String readBase=readbase();
                Log.d("readbase", "null"+readBase);
                */


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
            mediaPlayer = (MediaPlayer) MediaPlayer.create(getApplicationContext(), Uri.parse(strings[0]));
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


    public void downloadSong(String url) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading Music");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        //mProgressDialog.setProgress(0);
        mProgressDialog.show();
        final String writtten;

        Builder builder = new Builder().baseUrl(url + "/");
        Retrofit rf = builder.build();
        final Api fileDownloadClient = rf.create(Api.class);


        Call<ResponseBody> call = fileDownloadClient.downloadFileWithDynamicUrlAsync(url);

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("StaticFieldLeak")
            @Override

            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Log.d("TAG1", "Server has contacted the file! 1st ");

                    new AsyncTask<Void, Void, Void>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        protected Void doInBackground(Void... voids) {
                            // assert response.body() != null;
                            Boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                            Log.d("TAG11", " file download was a success? " + writtenToDisk);
                            base64Image= toBase64();


                            try {
                                encrypt(base64Image);
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
                            }


                            return null;
                        }
                    }.execute();
                } else {

                    Log.d("TAG3", "server contact failed");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                //if something failed this toast will be displayed
                Toast.makeText(MusicPlayerActivity.this, "Download Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean writeResponseBodyToDisk(ResponseBody body) {
        File song = null;
        File juice = null;
        File art=null;

        try {
            Log.d("tag5", "file download:XX ");
            juice = new File(Environment.getExternalStorageDirectory() + "/" + "JUICE");
            if (!juice.exists()) {
                //Create Folder From Path

                juice.mkdir();
            }
            art = new File(Environment.getExternalStorageDirectory() + "/" + "JUICE" + "/"+"ART");
            if (!art.exists()) {
                //Create Folder From Path
                art.mkdir();
            }
            song = new File(juice, currentSongArtist +"-"+ currentSongTitle + ".mp3");
            if (!song.exists()) {
                song.createNewFile();
                Log.e("song", "File Created");
            }

            Log.d("song", String.valueOf(song));
            InputStream inputStream = null;
            OutputStream outputStream = null;

            Log.d("tag6", "file download:XXX ");

            try {
                byte[] fileReader = new byte[4096];
                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                double per;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(song);


                Log.d("tag12", "Download commencing");

                while (true) {


                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }


                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.e("tag4", "Downloading.......: " + fileSizeDownloaded + " of " + fileSize);


                    per = (((double) fileSizeDownloaded / fileSize) * 100);
                    Log.e("tag4", "Downloaded.......: " + fileSizeDownloaded + " of " + fileSize + " PERCENT " + per);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            mProgressDialog.setMessage("Downloading...........");

                        }
                    });



                }

                outputStream.flush();
                // convrtt

                return true;


            } catch (IOException e) {
                Log.d("tag7", "file download:XXX " + e);
                return false;
            } finally {
                if (inputStream != null) {

                    Log.d("tag8", "TEST ");
                    inputStream.close();
                }
                if (outputStream != null)
                    outputStream.close();
                mProgressDialog.setProgress(100);
                mProgressDialog.setMessage("Download Completed");
                mProgressDialog.cancel();


            }




        } catch (IOException e) {
            Log.d("tag10", "TEST ");
            return false;

        }




    }






    @RequiresApi(api = Build.VERSION_CODES.O)
    public void encrypt(String base) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String encrypted=AESencryption.Encrypt(base,KEY);
        File enc=null;
        File juice= null;
        juice = new File(Environment.getExternalStorageDirectory() + "/" + "JUICE");
        enc = new File(juice, currentSongArtist +"-"+ currentSongTitle +"-"+currentUsernameValue+ ".juice");
        try {
            FileOutputStream fos = new FileOutputStream(enc);
            fos.write(encrypted.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String toBase64() {
        String base64Image = "";
        String path=Environment.getExternalStorageDirectory().toString()+"/JUICE"+"/"+currentSongArtist +"-"+ currentSongTitle + ".mp3";
        File file = new File(path);
        try (FileInputStream imageInFile = new FileInputStream(file))
        {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            Log.d("baseconvert", ""+imageData.length);
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
            Log.d("basenotfound", ""+e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
            Log.d("baseioexc",""+ioe);
        }
          boolean a=  file.delete();
        Log.d("deletemp3", ""+a);
        return base64Image;

    }


    public void downloadAlbumArt(String AlbumUrl) {
        Builder builder1 = new Builder().baseUrl(AlbumUrl + "/");
        Retrofit rf = builder1.build();
        final Api fileDownloadClient = rf.create(Api.class);


        Call<ResponseBody> call = fileDownloadClient.downloadFileWithDynamicUrlAsync(AlbumUrl);

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("StaticFieldLeak")
            @Override

            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    Log.d("TAG1", "Server has contacted the file! 1st ");

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            // assert response.body() != null;
                            boolean writtenToDisk = writeArtResponseBodyToDisk(response.body());

                            Log.d("TAG11", " file download was a success? " + writtenToDisk);


                            return null;
                        }
                    }.execute();
                } else {

                    Log.d("TAG3", "server contact failed");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                //if something failed this toast will be displayed
                Toast.makeText(MusicPlayerActivity.this, "Download Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private boolean writeArtResponseBodyToDisk(ResponseBody body) {
        File song = null;
        File art = null;
        try {
            Log.d("tag25", "file download:XX ");
            art = new File(Environment.getExternalStorageDirectory() + "/" + "JUICE" + "/"+"ART");
            if (!art.exists()) {
                //Create Folder From Path
                art.mkdir();
            }
            song = new File(art, currentSongArtist +"-"+ currentSongTitle + ".jpeg");
            if (!song.exists()) {
                song.createNewFile();
                Log.e("art", "File Created");
            }
            Log.d("art", String.valueOf(song));
            InputStream inputStream = null;
            OutputStream outputStream = null;
            Log.d("tag26", "file download:XXX ");

            try {
                byte[] fileReader = new byte[4096];
                final long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                double per;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(song);

                Log.d("tag27", "Download commencing");

                while (true) {


                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.e("tag28", "Downloading.......: " + fileSizeDownloaded + " of " + fileSize);


                    per = (((double) fileSizeDownloaded / fileSize) * 100);
                    Log.e("art29", "Downloaded.......: " + fileSizeDownloaded + " of " + fileSize + " PERCENT " + per);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            mProgressDialog.setMessage("Downloading...........");
                            //  + (int) per + " % " + " Completed ");
                            //mProgressDialog.setProgress((int) per);

                        }
                    });
                    // download progressing


                }

                outputStream.flush();
                // convrtt

                return true;


            } catch (IOException e) {
                Log.d("tag7", "file download:XXX " + e);
                return false;
            } finally {
                if (inputStream != null) {

                    Log.d("tag8", "TEST ");
                    inputStream.close();
                }
                if (outputStream != null)
                    outputStream.close();
                mProgressDialog.setProgress(100);
                mProgressDialog.setMessage("Download Completed");
                mProgressDialog.cancel();


            }



        } catch (IOException e) {
            Log.d("tag10", "TEST ");
            return false;

        }

    }

    public void sendNotification(View view) {
        Log.d("SEND1", "Send Notification Functions Start");

        createNotificationChannel();
        Bitmap largeimage = BitmapFactory.decodeResource(getResources(), skymove);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_icons8_rock_music_480);
        builder.setContentTitle(currentSongTitle);
        builder.setContentText(currentSongArtist);
        builder.setLargeIcon(largeimage);
        builder.addAction(R.drawable.ic_skip_previous_black_24dp, "previous", null);
        builder.addAction(R.drawable.ic_pause_black_24dp, "pause", null);
        builder.addAction(R.drawable.ic_skip_next_black_24dp, "Next", null);
        builder.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1, 2, 3));
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        Log.d("SEND", "Send Notification Functions end");


    }

    private void createNotificationChannel() {

        Log.d("create1", "create NotificationC Functions Start");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Now Playing";
            String description = "Displays now playing song";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Log.d("Create", "Create Notification Channel Functions end");

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(this,MainActivity.class);
       // startActivity(intent);
        intent.putExtra("songname",currentSongTitle);
        intent.putExtra("artistname",currentSongArtist);
        intent.putExtra("bol",true);
        setResult(RESULT_OK,intent);
        //startActivityForResult();
        finish();
    }
}
