package com.games.akash.caloriecount;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Akash on 2/12/2017.
 */

public class FoodListDBHandler extends SQLiteOpenHelper {

    private static FoodListDBHandler instance;

    private static final String DATABASE_NAME = "FoodDB.db";
    private static final int DATABSE_VERSION = 1;
    private static final String TABLE_NAME = "Food";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_CALORIES = "Calories";
    private String databasePath;
    private SQLiteDatabase db;
    private Context context;

    public static synchronized FoodListDBHandler getInstance(Context context) {
        //calls constructor is required and returns instance
        if(instance == null) {
            instance = new FoodListDBHandler(context);
        }
        return instance;
    }


    private FoodListDBHandler(Context context) {  //private constructor so that class is a singleton
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        this.context = context;
        this.databasePath = "/data/data/"+context.getPackageName()+"/databases/";
        System.out.println(databasePath);
    }

    public void importDB() {
        //call copyDB if database doesnt already exist
        if(!(context.getDatabasePath(DATABASE_NAME).exists())) {
            this.getReadableDatabase();
            copyDB();
        }
    }

    private void openDB() {
        //opens DB
        if(db == null || !db.isOpen()) {
            db = SQLiteDatabase.openDatabase(databasePath+DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }

    }

    private void closeDB() {
        //closes DB
        if(db != null) {
            db.close();
        }
    }

    private boolean copyDB() {

        //Reads data from asset using InputStream and writes it to accessible database using OutputStream
        try {
            InputStream in = context.getAssets().open(DATABASE_NAME);
            OutputStream out = new FileOutputStream(databasePath + DATABASE_NAME);
            byte[] data = new byte[1024];
            int length = 0;
            while((length = in.read(data)) > 0) {
                out.write(data, 0 ,length);
            }

            out.flush();
            out.close();
            in.close();
            System.out.println("Copy Success");
            return true;
        }
        catch(Exception e) {
            System.out.println("Copy Error");
            System.out.println(e);
            return false;
        }
    }

    public ArrayList<FoodItem> getAllFood() {

        //returns all food items in database

        ArrayList<FoodItem> foods = new ArrayList<FoodItem>();
        openDB();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        c.moveToFirst();

        if(c.getCount() == 0) {
            return foods;
        }

        String name;
        int calories;
        do {
            name = c.getString(c.getColumnIndex(COLUMN_NAME));
            Scanner scan = new Scanner(c.getString(c.getColumnIndex(COLUMN_CALORIES)));
            calories = scan.nextInt();

            foods.add(new FoodItem(name, calories));
        } while (c.moveToNext());

        closeDB();
        return foods;
    }

    public void resetDB() {

        //rewrites DB

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        copyDB();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //No new database will be created
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //calls resetDB
        resetDB();
    }

}
