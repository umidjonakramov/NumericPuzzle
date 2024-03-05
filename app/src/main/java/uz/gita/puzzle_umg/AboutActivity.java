package uz.gita.puzzle_umg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    private MediaPlayer music;
    private SharedPreferences myPref;
    boolean musicOn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        music = MediaPlayer.create(this, R.raw.click);
        setContentView(R.layout.activity_about2);
        myPref= MyPref.getPref();

        View arrow=findViewById(R.id.arrow_back);
        arrow.setOnClickListener(view -> {
            if (myPref.getBoolean("music" , true)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
//            new AlertDialog.Builder(this)
//                    .setTitle("Exit?")
//                            .setMessage("Do you want exit?")
//                    .setPositiveButton("yes",(dialog,which)->{
                        finish();
//                    })
//                    .setNegativeButton("no",(dialog,which)->{
//
//                            })
//                                    .show();
        });
    }
}