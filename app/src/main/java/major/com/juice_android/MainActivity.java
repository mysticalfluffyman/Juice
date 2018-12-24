package major.com.juice_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "My name is Sudin Joshi", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Wadup bois ?", Toast.LENGTH_SHORT).show();

    }
    public void what()
    {
      int a=4;
    }

    public void pushIt(){ int b=666;}
}
