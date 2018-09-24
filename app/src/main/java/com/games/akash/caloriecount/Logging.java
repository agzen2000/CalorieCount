package com.games.akash.caloriecount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class Logging extends AppCompatActivity {

    private Button submitBtn;
    private EditText titleTxt, caloriesTxt, descriptionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);

        //setting title
        setTitle("Log a Meal");

        //initializing objects
        submitBtn = (Button)findViewById(R.id.submitBtn);
        titleTxt = (EditText)findViewById(R.id.title);
        caloriesTxt = (EditText)findViewById(R.id.calories);
        descriptionTxt = (EditText)findViewById(R.id.descrption);

        //if intent has extras, autoWrite fills in EditTexts
        Intent intent = getIntent();
        if(intent.hasExtra("Calories")) {
            autoWrite(intent.getIntExtra("Calories", 0), intent.getStringArrayListExtra("Foods"));
        }
    }

    public void autoWrite(int cal, ArrayList<String> foods) {

        //Fills in EditTexts

        ArrayList<Integer> num = new ArrayList<>();
        ArrayList<String> items = new ArrayList<>();

        items.add(foods.get(0));
        num.add(1);
        for(int i = 1; i < foods.size(); i++) {
            if(items.contains(foods.get(i))) {
                num.set(items.indexOf(foods.get(i)), num.get(items.indexOf(foods.get(i))) + 1);
            }
            else {
                items.add(foods.get(i));
                num.add(1);
            }
        }

        caloriesTxt.setText(cal + "");
        String description = "";

        for(int i= 0; i < items.size() ; i++) {
            description += items.get(i)+ " X " + num.get(i).toString() + "\n";
        }

        descriptionTxt.setText(description);
    }


    public void submit(View v) {

        //when submit button is pressed, meal is saved to database

        Scanner cal = new Scanner(caloriesTxt.getText().toString());

        if((!cal.hasNextInt()) && titleTxt.getText().toString().equals("")){
            Toast.makeText(this, "Please add Title and amount of Calories", Toast.LENGTH_SHORT).show();
        }
        else if (!cal.hasNextInt()) {
            Toast.makeText(this, "Please add amount of Calories", Toast.LENGTH_SHORT).show();
        }
        else if(titleTxt.getText().toString().equals("")) {
            Toast.makeText(this, "Please add Title", Toast.LENGTH_SHORT).show();
        }
        else {
            int calories = cal.nextInt();
            if(calories < 1) {
                return;
            }

            Meals m = new Meals(titleTxt.getText().toString(), calories, descriptionTxt.getText().toString());
            Main_menu.today.addMeal(m);
            HistoryDBHandler.getInstance(this).addMeal(m);
            Main_menu.today.setDayCal(Main_menu.today.getDayCal() + m.getCalories());
            Main_menu.todayCalTxt.setText("" + Main_menu.today.getDayCal());
            finish();

        }
    }

    public void todayLogs(View v) {

        //when Today's Logs button is pressed, DayMeals is called with today's date

        Intent intent = new Intent(this,DayMeals.class);
        intent.putExtra("date", HistoryDBHandler.getInstance(getApplicationContext()).getTodayDate());
        startActivity(intent);
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
                startActivity(new Intent(this,History.class));
                return true;
            default:
                return super.onOptionsItemSelected(i);
        }
    }
}
