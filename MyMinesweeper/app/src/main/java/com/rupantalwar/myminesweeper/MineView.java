package com.rupantalwar.myminesweeper;

/**
 * Created by rupan on 10/26/2014.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;

import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import android.app.AlertDialog;

import java.util.Random;


public class MineView extends View {


    private static final String TAG = "MyMinesweeper";

    private final Game game;

    private int totalScore = 0;
    private int mineCount = 0;
    private boolean gameOver;

    private float width;    // width of one tile
    private float height;   // height of one tile

    Grid gridvar[][];

    GestureDetector gestureDetector;

    public MineView(Context context) {

        super(context);
        this.game = (Game) context;
        setFocusable(true);
        setFocusableInTouchMode(true);
        gestureDetector = new GestureDetector(context,new GestureListener());

        initializeMineGrid();
        reset();
    }





    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //Log.d(TAG,"OnTouchEvent");
        return gestureDetector.onTouchEvent(event);
    }



    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();
            int row= ((int) x)/ ((int) width);
            int col= ((int) y)/ ((int) height);
            Log.d(TAG, "onDoubleTap, Tapped at: (" + x + "," + y + ")");
            Log.d(TAG, "Value on Grid: ("+row+","+col+")");

            for(int i=0 ;i< 16 ;i++){
                for( int j =0 ; j < 16 ; j++){

                    if (gridvar[i][j]==gridvar[row][col]) {

                        if(gridvar[i][j].flag){ // if flag has been set returning
                            return true;
                        }
                        if(!gridvar[i][j].visited){ // opening grid
                            gridvar[i][j].opened = true;

                            if(gridvar[i][j].mine == true){ // checking mine is clicked
                                gameOver = true;
                            }else{
                                // calling recursively to open grid if mineNumber count as zero
                                if(gridvar[i][j].mineNumbers == 0){
                                    surroundingMines(gridvar[i][j]);
                                }
                            }
                        }
                    }

                }
            }

            invalidate();
            return true;
        }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e){
            float x = e.getX();
            float y = e.getY();
            int row= ((int) x)/ ((int) width);
            int col= ((int) y)/ ((int) height);
            Log.d(TAG, "onSingleTapConfirmed, Tapped at: (" + x + "," + y + ")");
            Log.d(TAG, "Value on Grid: (" + row + "," + col + ")");


            for(int i=0;i< 16 ;i++) {
                for (int j = 0; j < 16; j++) {
                    if (gridvar[i][j] == gridvar[row][col]) {
                        if (!gridvar[i][j].opened) {
                            if (!gridvar[i][j].flag) {
                                gridvar[i][j].flag = true;
                            } else
                                gridvar[i][j].flag = false;
                        }
                    }
                }
            }




            invalidate();
            return false;
        }
    }




    public void initializeMineGrid() {

        gridvar = new Grid[16][16];

        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                gridvar[i][j] = new Grid(getContext());
                gridvar[i][j].rowval = i;
                gridvar[i][j].colval = j;
            }
        }
    }

    public void reset() {
        for(int i = 0; i < 16; i++)
        {
            for(int j = 0; j < 16; j++)
            {
                gridvar[i][j].mineNumbers = 0;
                gridvar[i][j].mine = false;
                gridvar[i][j].opened = false;
                gridvar[i][j].flag = false;
                gridvar[i][j].visited =false;
            }
        }

        totalScore = 0;
        mineCount= 0;
        gameOver = false;
        mineLocation();
        mineNumbersCount();
    }


    public void mineLocation()
    {
        int bombsCounter = 49;
        while(bombsCounter > 0){
            Random rand = new Random();
            int randomNumber = rand.nextInt(255);
            int rowCount = (randomNumber / 16);
            int columnCount = randomNumber % 16;
            int k=0;
            for(int i=0;i< 16 ;i++){
                for( int j =0 ; j < 16 ; j++){


                    if(gridvar[i][j].rowval == rowCount && gridvar[i][j].colval == columnCount){
                        gridvar[i][j].mine = true;
                        gridvar[i][j].mineNumbers = 15;
                    }
                    k++;
                }
            }
            bombsCounter--;
        }

        for(int i=0;i< 16 ;i++){
            for( int j =0 ; j < 16 ; j++){
                if(gridvar[i][j].mine){
                    mineCount++;
                }
            }
        }

        totalScore = 256-mineCount;

    }


    public void mineNumbersCount()
    {
        int mineCount;
        for(int i=0;i< 16 ;i++){
            for( int j =0 ; j < 16 ; j++){
                mineCount =0;
                if(i!=0){
                    if(j!=0){
                        if(gridvar[i-1][j-1].mine){
                            mineCount++;
                        }
                    }
                    if(j!=15){
                        if(gridvar[i-1][j+1].mine){
                            mineCount++;
                        }
                    }
                    if(gridvar[i-1][j].mine){
                        mineCount++;
                    }
                }
                if(j!=0){
                    if(gridvar[i][j-1].mine){
                        mineCount++;
                    }
                }
                if(j!=15){
                    if(gridvar[i][j+1].mine){
                        mineCount++;
                    }
                }
                if(i!=15){
                    if(j!=0){
                        if(gridvar[i+1][j-1].mine){
                            mineCount++;
                        }
                    }
                    if(gridvar[i+1][j].mine){
                        mineCount++;
                    }
                    if(j!=15){
                        if(gridvar[i+1][j+1].mine){
                            mineCount++;
                        }
                    }
                }
                gridvar[i][j].mineNumbers = mineCount;
            }
        }
    }





    public void surroundingMines(Grid gridPoint)
    {


        // base case
        if(gridPoint.mineNumbers != 0 || gridPoint.visited == true){
            gridPoint.opened = true; // opening nearby element
            return;
        }
        // marking grid as visited
        gridPoint.visited = true;
        // marking grid open as true
        gridPoint.opened = true;
        int i = gridPoint.rowval;
        int j = gridPoint.colval;
        Log.d(TAG,"i and j:"+i+","+j);

        if(i!=0){
            if(j!=0){
                surroundingMines(gridvar[i-1][j-1]);
            }
            if(j!=15){
                surroundingMines(gridvar[i-1][j+1]);
            }
            surroundingMines(gridvar[i-1][j]);
        }
        if(j!=0){
            surroundingMines(gridvar[i][j-1]);
        }
        if(j!=15){
            surroundingMines(gridvar[i][j+1]);
        }
        if(i!=15){
            if(j!=0){
                surroundingMines(gridvar[i+1][j-1]);
            }
            surroundingMines(gridvar[i+1][j]);
        if(j!=15){
            surroundingMines(gridvar[i+1][j+1]);
        }
        }
    }





    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 16f;
        height = h / 16f;
       //getRect(selX, selY, selRect);
        Log.d(TAG, "onSizeChanged: width " + width + ", height "
                + height);
        super.onSizeChanged(w, h, oldw, oldh);
    }




        @Override
        protected void onDraw(Canvas canvas) {


            Log.d(TAG,"onDraw");
            // Draw the background...
            Paint background = new Paint();
            background.setColor(getResources().getColor(
                    R.color.game_board));
            canvas.drawRect(0, 0, getWidth(), getHeight(), background);
            // Draw the board...
            // Define colors for the grid lines
            Paint dark = new Paint();
            dark.setColor(getResources().getColor(R.color.puzzle_dark));
            Paint hilite = new Paint();
            hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
            Paint light = new Paint();
            light.setColor(getResources().getColor(R.color.puzzle_light));
            // Draw the minor grid lines
            for (int i = 0; i < 16; i++) {
                canvas.drawLine(0, i * height, getWidth(), i * height,
                        dark);
                canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, dark);
                canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
                canvas.drawLine(i * width + 1, 0, i * width + 1,
                        getHeight(), dark);
            }
            // Draw the major grid lines
            for (int i = 0; i < 16; i++) {
                // if (i % 3 != 0)
                //   continue;
                canvas.drawLine(0, i * height, getWidth(), i * height,
                        dark);
                canvas.drawLine(0, i * height + 1, getWidth(), i * height
                        + 1, dark);
                canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
                canvas.drawLine(i * width + 1, 0, i * width + 1,
                        getHeight(), dark);
            }


            Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
            foreground.setColor(getResources().getColor(
                    R.color.puzzle_foreground));
            foreground.setStyle(Style.FILL);
            foreground.setTextSize(height * 0.75f);
            foreground.setTextScaleX(width / height);
            foreground.setTextAlign(Paint.Align.CENTER);
            // Draw the number in the center of the tile
            FontMetrics fm = foreground.getFontMetrics();
            // Centering in X: use alignment (and X at midpoint)
            float x = width / 2;
            // Centering in Y: measure ascent/descent first
            float y = height / 2 - (fm.ascent + fm.descent) / 2;

            Drawable mineImg = getResources().getDrawable(R.drawable.mine);
            Drawable blankImg = getResources().getDrawable(R.drawable.blank);
            Drawable flagImg = getResources().getDrawable(R.drawable.flag);


            int currentScoreCount = 0;
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    if (gameOver) // gameover condition
                    {
                        if (gridvar[i][j].mine) {

                            //canvas.drawText(this.game.getNumString(i, j, 9), i
                            //       * width + x, j * height + y, foreground);

                            mineImg.setBounds(i*(int)width, j*(int)height, (i*(int)width)+(int)width, (j*(int)height)+(int)height);
                            mineImg.draw(canvas);
                        }
                        if (gridvar[i][j].mine == false && gridvar[i][j].opened == true) {
                            if (gridvar[i][j].mineNumbers == 0) {

                                //canvas.drawText(this.game.getNumString(i, j, 0), i
                                //        * width + x, j * height + y, foreground);
                                blankImg.setBounds(i*(int)width, j*(int)height, (i*(int)width)+(int)width, (j*(int)height)+(int)height);
                                blankImg.draw(canvas);

                            } else {
                                int mineNum = gridvar[i][j].mineNumbers;
                                canvas.drawText(this.game.getMineNumber(mineNum), i
                                        * width + x, j * height + y, foreground);
                            }
                        }
                    } else if (gridvar[i][j].flag) { // flag condition

                        //canvas.drawText(this.game.getNumString(i, j,10), i
                        //        * width + x, j * height + y, foreground);
                        flagImg.setBounds(i*(int)width, j*(int)height, (i*(int)width)+(int)width, (j*(int)height)+(int)height);
                        flagImg.draw(canvas);



                    } else if (gridvar[i][j].mine == false && gridvar[i][j].opened == true) {
                        // displaying opened grid if grid has not mine in it.
                        currentScoreCount++;
                        if (gridvar[i][j].mineNumbers == 0) {
                            //canvas.drawText(this.game.getNumString(i, j, 0), i
                            //        * width + x, j * height + y, foreground);
                            blankImg.setBounds(i*(int)width, j*(int)height, (i*(int)width)+(int)width, (j*(int)height)+(int)height);
                            blankImg.draw(canvas);

                        } else {
                            int mineNum = gridvar[i][j].mineNumbers;
                            canvas.drawText(this.game.getMineNumber(mineNum), i
                                    * width + x, j * height + y, foreground);
                        }
                    }
                }
            }



       // Alerting if game is over
        if(gameOver){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getContext());
            builder1.setMessage("!!!!!!!!!!!!!!!!!!!!!! Game Over !!!!!!!!!!!!!!!!!!!!!!");
            builder1.setCancelable(true);
            /*builder1.setPositiveButton("New Game",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i=new Intent(game,MineView.class);
                            getContext().startActivity(i);
                        }
                    });*/
            builder1.setNegativeButton("Main Menu",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i=new Intent(game,MyActivityMine.class);
                            getContext().startActivity(i);
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();




        }


        // Alerting win state
        if(currentScoreCount == totalScore){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getContext());
            builder1.setMessage("!!!!!!!!!!!!!!!!!!!!!! You WIN !!!!!!!!!!!!!!!!!!!!!!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Main Menu",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i=new Intent(game,MyActivityMine.class);
                            getContext().startActivity(i);
                        }
                    });
            builder1.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i=new Intent(game,MyActivityMine.class);
                            getContext().startActivity(i);
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }






}

