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
import java.util.Collections;

public class History extends AppCompatActivity {

    ListView daysList;
    ArrayAdapter<String> daysAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //setting title
        setTitle("History");

        //initializing objects
        ArrayList<String> allDates= HistoryDBHandler.getInstance(this).getAllDates();
        daysList = (ListView)findViewById(R.id.daysList);

        //reverse order and save to final array so that the most recent date is shown first
        Collections.reverse(allDates);
        final ArrayList<String> days = allDates;

        daysAdapter = new ArrayAdapter<>(this, R.layout.days_listview, R.id.dayTxt, days);

        //setting adapter
        daysList.setAdapter(daysAdapter);

        //when date is selected, DayMeals is started
        daysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DayMeals.class);
                intent.putExtra("date",days.get(position));
                startActivity(intent);
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
