package com.games.akash.caloriecount;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.games.akash.caloriecount.R.drawable.ic_info_black_24dp;

/**
 * Created by Akash on 2/3/2017.
 */

public class CustomListViewAdapter extends ArrayAdapter<Meals> {

    private Context context;
    private List<Meals> meals;

    public CustomListViewAdapter(Context context, int resource, ArrayList<Meals> mealsArrayList) {  //default contructor
        super(context, resource, mealsArrayList);
        this.context = context;
        this.meals = mealsArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //getting required meal
        Meals meal = meals.get(position);

        //inflating layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_listview, null);

        //initializing View objects
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView time = (TextView) view.findViewById(R.id.time);
        ImageView info = (ImageView) view.findViewById(R.id.info);

        //Setting title and time
        title.setText(meal.toString());
        time.setText(meal.getTime());

        return view;
    }
}
