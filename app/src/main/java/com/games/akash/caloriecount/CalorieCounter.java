package com.games.akash.caloriecount;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CalorieCounter extends AppCompatActivity {

    static ListView foodList;
    static ArrayAdapter<FoodItem> adapter;
    static TextView totalCalTxt;
    static Button submitBtn;
    static int calories;
    static String food;
    static ArrayList<String> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        //Setting title of activity
        setTitle("Food Items");

        //initialize objects
        foods= new ArrayList<String>();
        calories = 0;
        food = "";
        totalCalTxt = (TextView)findViewById(R.id.totalCaloriesTxt);
        submitBtn = (Button)findViewById(R.id.submitBtn);
        foodList = (ListView)findViewById(R.id.foodList);
        adapter = new CustomCheckListAdapter(this, R.layout.checkbox_listview, FoodListDBHandler.getInstance(this).getAllFood());

        //setting adapter for list
        foodList.setAdapter(adapter);

        //When the submit button is clicked, it starts the Logging activity using an Intent that contains the calories and Foods selected
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foods.size()>0) {
                    Intent log = new Intent(v.getContext(), Logging.class);
                    log.putExtra("Calories", calories);
                    log.putExtra("Foods", foods);
                    startActivity(log);
                    finish();
                }
                else {
                    //Atleast 1 item must be selected
                    Toast.makeText(CalorieCounter.this, "Please select at least one item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //When an item is selected, or the serving size is increased, the item is added to the array of food items.
    //When an item is unselected, or the serving size is decreased, the item is removed from the array of food items.
    public static void itemChecked(int cal, String itemName, boolean increase) {
        if(increase) {
            calories += cal;
            totalCalTxt.setText(calories + "");
            foods.add(itemName);
        } else {
            calories -= cal;
            totalCalTxt.setText(calories + "");
            foods.remove(itemName);
        }
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
