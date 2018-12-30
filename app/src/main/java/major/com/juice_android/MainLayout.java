package major.com.juice_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.florent37.materialviewpager.MaterialViewPager;

public class MainLayout extends AppCompatActivity {
    private MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
    }
}
