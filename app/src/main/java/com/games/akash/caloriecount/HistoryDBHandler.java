package com.games.akash.caloriecount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Akash on 2/8/2017.
 */

public class HistoryDBHandler extends SQLiteOpenHelper{

    private static HistoryDBHandler instance;
    private static String todayDate;

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "HistoryDB.db";
    public static final String TABLE_MEALS = "meals";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CALORIES = "calories";
    public static final String TABLE_DATES = "dates";

    private HistoryDBHandler (Context context) { //private constructor to have singleton class
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public static synchronized HistoryDBHandler getInstance(Context context) {

        //calls constructor if required and returns instance

        if(instance == null) {
            instance = new HistoryDBHandler(context);
        }

        todayDate = instance.getTodayDate();
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating meals table in database
        System.out.println("OnCreate called");
        String str = "CREATE TABLE " + TABLE_MEALS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CALORIES + " TEXT " +
                ");";
        System.out.println(str);
        db.execSQL(str);

        //Creating date table in database
        str = "CREATE TABLE " + TABLE_DATES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT " +
                ");";
        System.out.println(str);
        db.execSQL(str);

    }

    public void addMeal(Meals m) {

        //adds meal to today's Date

        System.out.println("addMeal called");
        ContentValues row = new ContentValues();
        row.put(COLUMN_DATE, todayDate);
        row.put(COLUMN_TIME, m.getTime());
        row.put(COLUMN_TITLE, m.getTitle());
        row.put(COLUMN_DESCRIPTION, m.getDescription());
        row.put(COLUMN_CALORIES, m.getCalories()+"");

        SQLiteDatabase db = getWritableDatabase();
        System.out.println("writable database");

        db.insert(TABLE_MEALS, null, row);
        db.close();
    }

    public ArrayList<Meals> getMeals(String date) {

        //retrieves all dates from a day

        ArrayList<Meals> meals = new ArrayList<Meals>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_MEALS + " WHERE " + COLUMN_DATE + " = '" + date + "';", null);
        c.moveToFirst();

        if(c.getCount() == 0) {
            return meals;
        }

        String time = "";
        String title = "";
        String description = "";
        int calories = 0;

        do {
            time = c.getString(c.getColumnIndex(COLUMN_TIME));
            title = c.getString(c.getColumnIndex(COLUMN_TITLE));
            description = c.getString(c.getColumnIndex(COLUMN_DESCRIPTION));
            Scanner scan = new Scanner(c.getString(c.getColumnIndex(COLUMN_CALORIES)));
            calories = scan.nextInt();

            meals.add(new Meals(time, title, description, calories));
        } while (c.moveToNext());

        db.close();
        return meals;
    }

    public void newDay() {

        //adds a new day, and sets todayDate

        todayDate = Main_menu.today.getDate();
        System.out.println("newDay called");
        ContentValues row = new ContentValues();
        row.put(COLUMN_DATE, todayDate);

        SQLiteDatabase db = getWritableDatabase();
        System.out.println("writable database");

        db.insert(TABLE_DATES, null, row);
        db.close();

    }

    public String getTodayDate() {

        //retrieves most recent date from database

        Cursor c = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_DATES, null);
        if(c.moveToLast()) {
            return c.getString(c.getColumnIndex(COLUMN_DATE));
        }
        return null;
    }

    public ArrayList<String> getAllDates() {

        //retrieves all dates from dates table

        Cursor c = this.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_DATES, null);
        ArrayList<String> dates = new ArrayList<String>();

        c.moveToFirst();

        if(c.getCount() == 0) {
            return dates;
        }

        do {
            dates.add(c.getString(c.getColumnIndex(COLUMN_DATE)));
        } while (c.moveToNext());

        return dates;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

        //drops tables and recreates them

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATES);
        this.onCreate(db);
    }
}
