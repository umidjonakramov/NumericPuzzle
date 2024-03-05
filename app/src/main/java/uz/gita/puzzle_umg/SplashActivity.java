package uz.gita.puzzle_umg;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, KirishActivity.class));
            finishAffinity();
        }, 2000);
    }
}
