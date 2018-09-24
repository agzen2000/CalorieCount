package com.games.akash.caloriecount;

/**
 * Created by Akash on 2/12/2017.
 */

public class FoodItem {

    //This class allows me to easily access each item in the FoodListDB as an object

    public String name;
    public int calories;

    public FoodItem(String name, int calories) {
        this.calories = calories;
        this.name = name;
    }

    public String toString() { return name + " - " + calories + " cal"; }
}
