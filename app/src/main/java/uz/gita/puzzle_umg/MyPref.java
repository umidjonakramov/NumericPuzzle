package uz.gita.puzzle_umg;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPref {
    SharedPreferences sharefPreference;
    static MyPref myPref;

    private MyPref(Context context) {
        sharefPreference = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    static public void init(Context context) {
        if (myPref == null) {
            myPref = new MyPref(context);
        }
    }


    static public SharedPreferences getPref() {
        return myPref.sharefPreference;
    }
}
