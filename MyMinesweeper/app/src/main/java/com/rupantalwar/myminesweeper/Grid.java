package com.rupantalwar.myminesweeper;

/**
 * Created by rupan on 10/25/2014.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class Grid extends Button
    {

        public Grid(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }


       int rowval;
       int colval;
       boolean flag=false;
       Integer mineNumbers = new Integer(0);
       Boolean mine = new Boolean(false);
       Boolean opened = new Boolean(false);
       Boolean visited = new Boolean(false);


    }
