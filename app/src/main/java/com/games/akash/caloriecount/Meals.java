package com.games.akash.caloriecount;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Akash on 2/2/2017.
 */

public class Meals {
    private int calories;
    private String title;
    private String description;
    private String time;

    public Meals(String title, int calories, String description) {

        //sets variables
        this.calories=calories;
        this.title=title;
        this.description=description;

        //getting time
        Calendar calendar;
        SimpleDateFormat date;
        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("h:mm a");
        time = date.format(calendar.getTime());
    }

    public Meals(String time, String title, String description, int calories) {
        //sets variables
        this.time = time;
        this.title = title;
        this.description = description;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return title + " - " + calories + " Calories";
    }

    public String getTime() {
        return time;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getCalories() { return calories; }
}
