package com.games.akash.caloriecount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    Button reset;
    Button changeLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        //initialize button
        changeLimit = (Button) findViewById(R.id.changeCalLimitBtn);

        //when button is clicked, Alert Dialog is shown that sets calorie limit
        changeLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main_menu.limitDialogue(Settings.this, true);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle item selection
        switch (i.getItemId()) {
            case R.id.settings:
                return true;
            case R.id.history:
                startActivity(new Intent(this,History.class));
                return true;
            default:
                return super.onOptionsItemSelected(i);
        }
    }
}
