package uz.gita.puzzle_umg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import com.smb.glowbutton.GlowButton;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import studio.karo.neonbutton.NeonButton;

public class KirishActivity extends AppCompatActivity {
    private MediaPlayer music;
     boolean musicOn=true;
     private SharedPreferences myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        music = MediaPlayer.create(this, R.raw.click);
        setContentView(R.layout.activity_kirish);
        NeonButton btnPlay = findViewById(R.id.btnPlay);
        ImageView volume=findViewById(R.id.volume);
        myPref= MyPref.getPref();

        volume.setOnClickListener(view -> {
            if (myPref.getBoolean("music",true)) {
                music.stop();
                music.release();
                myPref.edit().putBoolean("music" , false).apply();
            }else {
                music = MediaPlayer.create(this , R.raw.click) ;
                myPref.edit().putBoolean("music" , true).apply();
            }
            if (myPref.getBoolean("music",true)) {
                volume.setImageResource(R.drawable.volume_loud_svgrepo_com);
            } else {
                volume.setImageResource(R.drawable.volume_cross_svgrepo_com);
            }
        });


        btnPlay.setOnClickListener(view -> {
            Intent intent;
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            if (myPref.getBoolean("music",true)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
//            finish();
        });
        NeonButton btnExit = findViewById(R.id.Exit);
        btnExit.setOnClickListener(view -> {
            if (myPref.getBoolean("music",true)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
            finish();
//            new AlertDialog.Builder(this)
//                    .setTitle("Exit")
//                            .setMessage("Do you want exit?")
//                                    .setPositiveButton("yes",(dialog,which)->{
//                                        finish();
//                                            })
//                    .setNegativeButton("no",(dialog,which)-> {
//
//                    })
//                    .show();
        });

        NeonButton about=findViewById(R.id.about);
        about.setOnClickListener(view -> {
            Intent intent=new Intent(this,AboutActivity.class);
            startActivity(intent);
            if (myPref.getBoolean("music",true)){
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
        });
        NeonButton statistics=findViewById(R.id.statistic);
        statistics.setOnClickListener(view -> {
            Intent intent= new Intent(this,Statistics.class);
            startActivity(intent);
            if (myPref.getBoolean("music",true)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }

        });
    }
}