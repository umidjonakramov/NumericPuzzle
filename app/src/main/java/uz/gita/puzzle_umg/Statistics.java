package uz.gita.puzzle_umg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;



public class Statistics extends AppCompatActivity {

    TextView record2;
    SharedPreferences myPref;

    private MediaPlayer music;

    boolean musicOn=true;
    TextView rec;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        myPref = MyPref.getPref();
        music = MediaPlayer.create(this, R.raw.click);
        View arrow = findViewById(R.id.arrow_back2);
        arrow.setOnClickListener(t->{
            Log.d("TTT", "onCreate: bacck");
            if (myPref.getBoolean("music",false)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
            finish();
        });
        rec =findViewById(R.id.Record);
        rec.setText((myPref.getInt("count", 0))+" steps");
    }
}