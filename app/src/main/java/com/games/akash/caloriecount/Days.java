package com.games.akash.caloriecount;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Akash on 1/31/2017.
 */

public class Days {

    //This class allows me to easily retrieve information about the day from one object

    private int dayCal;
    private String date;
    private ArrayList<Meals> meals = new ArrayList<Meals>();

    public Days() {

        //getting date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = simpleDateFormat.format(calendar.getTime());

        dayCal = 0;
    }

    public void addMeal(Meals m) {
        meals.add(m);
    }

    public void setDate(String date) { this.date = date;  }

    public int getDayCal() { return dayCal; }

    public ArrayList<Meals> getMeals() { return meals; }

    public String getDate() { return date; }

    public void setMeals (ArrayList<Meals> meals) {
        this.meals = meals;
        int totalCal = 0;
        for(int i =0; i < meals.size() ; i++) {
            totalCal+=meals.get(i).getCalories();
        }
        dayCal = totalCal;
    }

    public void setDayCal(int dayCal){
        this.dayCal = dayCal;
    }

}
