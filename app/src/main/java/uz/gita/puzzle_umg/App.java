package uz.gita.puzzle_umg;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyPref.init(this);
    }
}
