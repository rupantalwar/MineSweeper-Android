package com.rupantalwar.myminesweeper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class MyActivityMine extends Activity implements OnClickListener
{
    private static final String TAG = "MyMinesweeper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity_mine);

        View newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(this);

        View rulesButton = findViewById(R.id.rules_button);
        rulesButton.setOnClickListener(this);

        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity_mine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.new_game_button:
                Intent i = new Intent(this, Game.class);
                startActivity(i);
                this.finish();
                break;
            case R.id.rules_button:
                Intent i1 = new Intent(this, Rules.class);
                startActivity(i1);
                break;
            case R.id.exit_button:
                Log.d(TAG,"EXIT button");
                //this.finish();
                //super.finish();
                super.onStop();
                System.exit(0);//end the application
                break;
        }
    }
}
