package com.games.akash.caloriecount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DayMeals extends AppCompatActivity {

    ListView mealsList;
    ArrayAdapter<Meals> mealsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_meals);

        //getting the date
        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");

        //setting title
        setTitle("Meals Logged on " + date);

        //initializing objects
        mealsList = (ListView)findViewById(R.id.mealsList);
        mealsAdapter = new CustomListViewAdapter(getApplicationContext(), 0, HistoryDBHandler.getInstance(getApplicationContext()).getMeals(date));

        //setting adapter
        mealsList.setAdapter(mealsAdapter);

        //when meal is selected, MealInfo class is called
        mealsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent info = new Intent(view.getContext(), MealInfo.class);
                info.putExtra("Position", position);
                info.putExtra("Date", date);
                startActivity(info);
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
