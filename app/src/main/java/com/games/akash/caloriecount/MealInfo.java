package com.games.akash.caloriecount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MealInfo extends AppCompatActivity {

    private int position;
    Meals m;
    TextView titleTxt, calTxt, descriptionTxt, timeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_info);

        //initializing objects
        titleTxt = (TextView)findViewById(R.id.titleTxt);
        calTxt = (TextView)findViewById(R.id.caloriesTxt);
        descriptionTxt = (TextView)findViewById(R.id.descriptionTxt);
        timeTxt = (TextView)findViewById(R.id.timeTxt);

        //get position of meal in meal array and date
        Intent intent = getIntent();
        position = intent.getIntExtra("Position" , 0);
        String date = intent.getStringExtra("Date");

        //retrieve meal from database
        ArrayList<Meals> meals = HistoryDBHandler.getInstance(this).getMeals(date);
        m = meals.get(position);

        //setting title
        setTitle(m.getTitle() + " Information");

        //setting View objects
        titleTxt.setText(m.getTitle());
        calTxt.setText(m.getCalories() + "");
        descriptionTxt.setText(m.getDescription());
        timeTxt.setText(m.getTime());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        switch (i.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this,Settings.class));
                return true;
            case R.id.history:
                return true;
            default:
                return super.onOptionsItemSelected(i);
        }
    }
}
