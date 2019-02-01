package major.com.juice_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity
{

    SharedPreferences sharedPreferences;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences =getSharedPreferences("loggedininfo", 0);
        status = sharedPreferences.getInt("status", 0);

        if (status == 0)
        {
            final Intent intent = new Intent(this, LoginActivity.class);

            Thread timer = new Thread()
            {
                public void run()
                {
                    try
                    {
                        sleep(1500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }
        else
        {
            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("sendingUserName", sharedPreferences.getString("username", ""));

            Thread timer = new Thread()
            {
                public void run()
                {
                    try
                    {
                        sleep(1500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }
}
