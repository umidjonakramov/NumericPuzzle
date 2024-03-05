package uz.gita.puzzle_umg;

//import static kotlin.PropertyReferenceDelegatesKt.setValue;
import static uz.gita.puzzle_umg.MyPref.myPref;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.thecode.aestheticdialogs.AestheticDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import studio.karo.neonbutton.NeonButton;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer music;
    SharedPreferences pref;
    private NeonButton[][] buttons;
    List<Integer> numbers;
    private Coordinate empty;
    private SharedPreferences myPref;
    TextView rec;
//    private Chronometer chronometer;


    int counter;

    TextView movestext;
    ImageView exit;
    ImageView shuffle;
    ImageView volume;

    int timecount;
    int min;
    int sec;
    String str;

    private boolean musicOn = true;


//    AppCompatButton time;


    {
        numbers = new ArrayList<>(15);
        empty = new Coordinate(3, 3);
        for (int i = 0; i < 15; i++) {
            numbers.add(i + 1);
        }
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movestext = findViewById(R.id.move1);
        exit = findViewById(R.id.Exit);
        shuffle = findViewById(R.id.Shuffle);
        volume = findViewById(R.id.volume);
//        chronometer = findViewById(R.id.chronometer);
        myPref = MyPref.getPref();
        rec =findViewById(R.id.Record);
        rec.setText("Record: "+(myPref.getInt("count", 0))+" steps");
//        chronometer.start();


//        volume.setOnClickListener(view -> {
//            if (myPref.getBoolean("music",false)) {
//                music.stop();
//                music.release();
//                music = MediaPlayer.create(this, R.raw.click);
//                music.start();
//            }
//            musicOn = !musicOn;
//            if (myPref.getBoolean("music",false)) {
//                volume.setImageResource(R.drawable.volume_loud_svgrepo_com);
//            } else {
//                volume.setImageResource(R.drawable.volume_cross_svgrepo_com);
//            }
//        });

        pref = MyPref.getPref();
//        time = findViewById(R.id.Timer);
        music = MediaPlayer.create(this, R.raw.click);
        initViews();
        loadViews();
        Timer timer = new Timer();
//        if (!isPlay()) {
//            music.stop();
//        }
//        findViewById(R.id.microphone).setOnClickListener(view -> {
//            music.start();
//        });


        if (savedInstanceState != null) {
            timecount = savedInstanceState.getInt("TIME");
            str = savedInstanceState.getString("STR");
        }

        movestext.setText(" " + counter);
        shuffle.setOnClickListener(view -> {
            shuffle();
            loadViews();
            timecount = 0;
            counter = 0;
            movestext.setText(": " + counter);
            if (myPref.getBoolean("music", false)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
        });
        exit.setOnClickListener(view -> {
            finish();
            if (myPref.getBoolean("music", true)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }
        });

        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                if (counter >= 1) {
                    timecount++;

                }
//                timecount++;
                min = timecount / 60;
                sec = timecount % 60;

                if (sec < 10 && min < 10) {
                    str = "0" + min + " : 0" + sec;
                } else if (sec >= 10 && min < 10) {
                    str = "0" + min + " : " + sec;
                } else if (sec < 10 && min >= 10) {
                    str = min + " : 0" + sec;
                } else {
                    str = min + " : " + sec;
                }
//                runOnUiThread(() -> time.setText(str));
            }
        }, 1000, 1000);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViews() {
        LinearLayout container = findViewById(R.id.container);
        int rawCount = container.getChildCount();
        int columnCount = ((LinearLayout) (container.getChildAt(0))).getChildCount();
        buttons = new NeonButton[rawCount][columnCount];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = (NeonButton) (((LinearLayout) (container.getChildAt(i))).getChildAt(j));
                buttons[i][j].setTag(new Coordinate(i, j));
                buttons[i][j].setOnClickListener((view) -> {
                    TextView clickedBtn = (TextView) view;
                    Coordinate clickedCoodinate = (Coordinate) clickedBtn.getTag();

                    int clickX = clickedCoodinate.getX();
                    int clickY = clickedCoodinate.getY();

                    move(clickX, clickY);

                });
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void move(int clickX, int clickY) {
        int emptyX = empty.getX();
        int emptyY = empty.getY();

        if (Math.abs(emptyX - clickX) + Math.abs(emptyY - clickY) == 1) {
            buttons[emptyX][emptyY].setVisibility(View.VISIBLE);
            buttons[emptyX][emptyY].setText(buttons[clickX][clickY].getText());
            buttons[clickX][clickY].setVisibility(View.INVISIBLE);
            buttons[clickX][clickY].setText("");

            empty.setX(clickX);
            empty.setY(clickY);

            counter++;
            movestext.setText("" + counter);
            isWin();
            if (myPref.getBoolean("music", true)) {
                music.stop();
                music.release();
                music = MediaPlayer.create(this, R.raw.click);
                music.start();
            }

        }
    }

    private void loadViews() {
        shuffle();
        int count = 0;
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size() - 1; j++) {
                if (numbers.get(i) > numbers.get(j) && numbers.get(i) != 0) count++;
            }
        }


        if (!(count % 2 == 0)) loadViews();
        buttons[empty.getX()][empty.getY()].setVisibility(View.VISIBLE);
        buttons[empty.getX()][empty.getY()].setText("");
        empty.setX(3);
        empty.setY(3);
        buttons[empty.getX()][empty.getY()].setVisibility(View.INVISIBLE);
        for (int i = 0; i < 15; i++) {
            buttons[i / 4][i % 4].setText(numbers.get(i) + "");
        }
    }

    private void shuffle() {
        Collections.shuffle(numbers);
        while (!isSolvable(numbers)) {
            Collections.shuffle(numbers);

        }
    }

    private boolean isSolvable(List<Integer> list) {
        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = i + 1; j < 15; j++) {
                if (list.get(i) > list.get(j) && j > i) {
                    countInversions++;
                }
            }
        }
        return countInversions % 2 == 0;
    }

    private void isWin() {
        int count = 0;
        for (int i = 0; i < 15; i++) {
            if (buttons[i / 4][i % 4].getText().equals(String.valueOf(i + 1))) {
                count++;
            }
            System.out.println("count: " + count);
        }
        if (count == 15) {
//            View view = LayoutInflater.from(this).inflate(R.layout.dialog_win, null, false);
//            AlertDialog dialog = new AlertDialog.Builder(this)
//                    .setView(view)
//                    .create();
//            dialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
//            AppCompatButton button = view.findViewById(R.id.Again);
//            button.setOnClickListener(v -> {
//                shuffle();
//                loadViews();
//                timecount = 0;
//                counter = 0;
//            });
//            dialog.show();


//            chronometer.stop();
//            AestheticDialog.showFlashDialog(this, "Your dialog Title", "Your message", AestheticDialog.SUCCESS);
            MaterialDialog mBottomSheetDialog = new MaterialDialog.Builder(this)
                    .setAnimation(R.raw.animation_lmt24vz0)
//                    .setAnimation(R.raw.animation_lmt0tfge)
                    .setTitle("You are winner!!!")
                    .setCancelable(false)
                    .setPositiveButton("Play Again", R.drawable.baseline_refresh_24, (dialogInterface, which) -> {
                        shuffle();
                        loadViews();
                        timecount = 0;
                        counter = 0;
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton("Moves: " + counter, R.drawable.baseline_home_24, (dialogInterface, which) -> {
//                        startActivity(new Intent(MainActivity.this, KirishActivity.class));
                        finish();
                        dialogInterface.dismiss();
                    })
                    .build();
            if (pref.getInt("count", Integer.MAX_VALUE) > counter) {
                pref.edit().putInt("count", counter).apply();

            }
            // Show Dialog
            mBottomSheetDialog.show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        LinearLayout container = findViewById(R.id.container);
        outState.putInt("counter", counter);
        outState.putInt("TIME", timecount);
        outState.putString("STR", str);

        outState.putInt("getx", empty.getX());
        outState.putInt("gety", empty.getY());

        ArrayList<String> arr = new ArrayList<>();
        int columnCount = ((LinearLayout) (container.getChildAt(0))).getChildCount();
        for (int i = 0; i < container.getChildCount(); i++) {
            for (int j = 0; j < columnCount; j++) {
                TextView textView = (TextView) ((((LinearLayout) (container.getChildAt(i))).getChildAt(j)));
                arr.add(textView.getText().toString());
            }
        }
        outState.putStringArrayList("ARR", arr);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter", 0);
        movestext.setText("" + counter);


        ArrayList<String> list = savedInstanceState.getStringArrayList("ARR");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("")) {
                buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
                buttons[i / 4][i % 4].setText("");
                empty.setX(i / 4);
                empty.setY(i % 4);
            } else {
                buttons[i / 4][i % 4].setVisibility(View.VISIBLE);
                buttons[i / 4][i % 4].setText(list.get(i));
            }
        }
        timecount = savedInstanceState.getInt("TIME");
        str = savedInstanceState.getString("STR");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        chronometer.stop();
    }
    //    private boolean isPlay() {
//
//        return music.isPlaying();
//    }
}