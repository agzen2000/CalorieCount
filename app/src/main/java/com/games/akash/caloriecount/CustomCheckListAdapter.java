package com.games.akash.caloriecount;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Akash on 2/14/2017.
 */

public class CustomCheckListAdapter extends ArrayAdapter<FoodItem> {

    private Context context;
    private List<FoodItem> FoodItems;

    public CustomCheckListAdapter(Context context, int resource, ArrayList<FoodItem> FoodItems) { //default constructor
        super(context, resource, FoodItems);
        this.context = context;
        this.FoodItems = FoodItems;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the required food item
        final FoodItem item = FoodItems.get(position);

        //inflating layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.checkbox_listview, null);

        //initializing required View Objects
        final TextView titleTxt = (TextView) view.findViewById(R.id.title);
        final CheckBox check = (CheckBox)view.findViewById(R.id.checkBox);
        final Button plusBtn = (Button)view.findViewById(R.id.plusBtn);
        final Button minusBtn = (Button)view.findViewById(R.id.minusBtn);
        final TextView servingSizeTxt = (TextView)view.findViewById(R.id.servingsTxt);

        //setting View Objects as required
        titleTxt.setText(item.toString());
        plusBtn.setVisibility(View.INVISIBLE);
        minusBtn.setVisibility(View.INVISIBLE);
        servingSizeTxt.setVisibility(View.INVISIBLE);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = check.isChecked();
                //when item is checked, servings counter with plus and minus buttons come up, itemChecked is called from CalorieCounter with boolean set to true
                if(isChecked) {
                    plusBtn.setVisibility(View.VISIBLE);
                    minusBtn.setVisibility(View.VISIBLE);
                    servingSizeTxt.setVisibility(View.VISIBLE);
                    servingSizeTxt.setText("1");

                    CalorieCounter.itemChecked(item.calories, item.name, true);

                } else
                //when item is unchecked, servings counter and plus/minus buttons go away, itemChecked is called in a loop, with boolean set to false, to remove item
                {
                    String str = (String)servingSizeTxt.getText();
                    Scanner scan = new Scanner(str);
                    for(int i=scan.nextInt(); i>0; i--) {
                        CalorieCounter.itemChecked(item.calories, item.name, false);
                    }
                    plusBtn.setVisibility(View.INVISIBLE);
                    minusBtn.setVisibility(View.INVISIBLE);
                    servingSizeTxt.setVisibility(View.INVISIBLE);
                }

            }
        });

        //when plus button is clicked, itemChecked is called with boolean set to true
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String)servingSizeTxt.getText();
                Scanner scan = new Scanner(str);
                servingSizeTxt.setText((scan.nextInt() + 1) + "");
                CalorieCounter.itemChecked(item.calories, item.name, true);
            }
        });

        //when minus button is clicked, itemChecked is called with boolean set to false
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = (String)servingSizeTxt.getText();
                Scanner scan = new Scanner(str);
                int previousCal = scan.nextInt();
                if(previousCal>1) {
                    servingSizeTxt.setText((previousCal - 1) + "");
                    CalorieCounter.itemChecked(item.calories, item.name, false);
                }
            }
        });

        return view;
    }
}
