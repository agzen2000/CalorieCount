package com.games.akash.caloriecount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class Main_menu extends AppCompatActivity {

    public static ImageButton logCalBtn, calCalcBtn;
    public static TextView todayCalTxt, calLimitTxt;
    public static Days today;
    private static SharedPreferences saveLimit;
    private static final String LIMIT_KEY = "limit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //initializes all variables when activity is started
        initiateObjects();

        //if calorie limit is not saved, limitDialog is called
        if(saveLimit.getInt(LIMIT_KEY, 0) == 0) {
            limitDialogue(Main_menu.this, false);
        }

    }

    private void initiateObjects() {

        //initializing objects
        logCalBtn = (ImageButton)findViewById(R.id.logCalBtn);
        calCalcBtn = (ImageButton)findViewById(R.id.calCalcBtn);
        todayCalTxt = (TextView)findViewById(R.id.todayCal);
        calLimitTxt = (TextView)findViewById(R.id.calLimit);
        today = new Days();
        saveLimit = getSharedPreferences("Limit", Context.MODE_PRIVATE);

        //if today's date is not same as last date on database, newDate is called on DB
        if(!today.getDate().equals(HistoryDBHandler.getInstance(this).getTodayDate())) {
            HistoryDBHandler.getInstance(this).newDay();
        }

        //Setting view objects
        today.setMeals(HistoryDBHandler.getInstance(this).getMeals(today.getDate()));
        todayCalTxt.setText(today.getDayCal()+"");
        calLimitTxt.setText(saveLimit.getInt(LIMIT_KEY, 0)+"");

        //importing food list DB if required from asset
        FoodListDBHandler.getInstance(this).importDB();
    }

    @Override
    protected void onResume() {
        //initializes all variables when activity is resumed since static variables are often lost when process is killed to free ram

        super.onResume();
        initiateObjects();
    }

    public static void limitDialogue(final Activity context, final boolean closeactivityAfter) {

        //shows alert dialog to ask for calorie limit and sets it

        //building alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.calorie_limit_dialogue, null);
        final EditText calorieLimit = (EditText) v.findViewById(R.id.calorieEt);
        Button submitBtn = (Button) v.findViewById(R.id.submitBtn);
        builder.setView(v);

        //creating dialog
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        //when submit button is clicked, checks for user input, and saves limit
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Scanner scan = new Scanner(calorieLimit.getText().toString());
                if(scan.hasNextInt()) {
                    int limit = scan.nextInt();
                    if(limit>0) {
                        setLimit(limit);
                        Toast.makeText(context, "Submitted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        if(closeactivityAfter) {
                            context.finish();
                        }
                    } else {
                        Toast.makeText(context, "Calorie Limit should be greater than zero", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please enter a number", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    public static void setLimit(int limit) {
        //sets Calorie Limit

        Main_menu.calLimitTxt.setText(limit+"");
        SharedPreferences.Editor editor = saveLimit.edit();
        editor.putInt(LIMIT_KEY,limit);
        editor.commit();
    }

    public void logCalories (View v) {

        //When log calories button is clicked, Logging activity is called
        startActivity(new Intent(this,Logging.class));
    }

    public void calorieCalculator(View v) {

        //When Calorie Calculator button is clicked, CalorieCount activity is called
        startActivity(new Intent(this,CalorieCounter.class));
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
