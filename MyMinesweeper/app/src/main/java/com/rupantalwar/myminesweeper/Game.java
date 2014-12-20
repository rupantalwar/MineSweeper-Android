package com.rupantalwar.myminesweeper;

/**
 * Created by rupan on 10/25/2014.
 */
import android.app.Activity;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Game extends Activity {
    private static final String TAG = "MyMinesweeper";
    private MineView mineView;



    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");


        mineView = new MineView(this);
        setContentView(mineView);
        mineView.requestFocus();




    }

    protected String getMineNumber(int n) {
        switch(n){

            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";

            default:
                return "9";
        }

}

}

