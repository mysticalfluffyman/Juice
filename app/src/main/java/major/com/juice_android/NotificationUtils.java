package major.com.juice_android;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class NotificationUtils extends ContextWrapper {

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "major.com.juice_android.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    private Context context;


    public NotificationUtils(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {

        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(androidChannel);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        }
        return mManager;
    }

    @SuppressLint("NewApi")

    public Notification.Builder getAndroidChannelNotification(String title, String body, String albumart) {

        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.status_bar);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.status_bar_expanded);

        expandedView.setTextViewText(R.id.status_bar_track_name, title);
        expandedView.setTextViewText(R.id.status_bar_album_name, body);
//        expandedView.setImageViewBitmap(R.id.status_bar_album_art, getBitmapFromURL(albumart));

        //to open activity when notification is clicked
        Intent resultIntent = new Intent(this, MusicPlayerActivity.class);
        resultIntent.putExtra("songid",2);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT);

        //for buttons
        Intent pausetIntent = new Intent(this, NotificationBroadcast.class);
        pausetIntent.putExtra("toast", title);
        pausetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startService(pausetIntent);
        PendingIntent pauseIntent = PendingIntent.getBroadcast(this, 1, pausetIntent, PendingIntent.FLAG_ONE_SHOT);


        expandedView.setOnClickPendingIntent(R.id.status_bar_play, PendingIntent.getService(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT));


        return new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                //.setLargeIcon(getBitmapFromURL(albumart))
                .setSmallIcon(R.drawable.ic_play_arrow_black_24dp)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setOnlyAlertOnce(true)

                //.setStyle(new NotificationCompat.DecoratedCustomViewStyle())

                //for buttons

                .addAction(R.drawable.ic_skip_previous_black_24dp, "previous", null)
                .addAction(R.drawable.ic_pause_black_24dp, "toast", pauseIntent)
                .addAction(R.drawable.ic_close_black_24dp, "Next", null)

                // this should open music player activity
                .setContentIntent(PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);


    }


   /* public static Bitmap getBitmapFromURL(String src) {
        try {
            *//*URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);*//*
            Bitmap myBitmap = null;
            Picasso.with(this.context).load(src).into(myBitmap);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/


}


