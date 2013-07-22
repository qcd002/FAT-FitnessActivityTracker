package se.shsu.fat;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Food extends Activity implements android.view.View.OnClickListener{
    private static final String NAME = "name";
    private static final String ADD_NEW_ITEM = "Add New Item";
	Button food_Home;
    private SimpleAdapter adapter;
    private Spinner spFood;
    private List<HashMap<String, String>> foodName;
    private int counter;
    
    private OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	HashMap<String, String> map = foodName.get(arg2);
        	String name = map.get(NAME);
        	Toast.makeText(getApplicationContext(),
                    "Want some " + name + " ???",
                    Toast.LENGTH_LONG).show();
        	
           /* HashMap<String, String> map = foodName.get(arg2);
            String name = map.get(NAME);
            if (name.equalsIgnoreCase(ADD_NEW_ITEM)) {
            	foodName.remove(map);
                counter++;
                addNewFood(String.valueOf(counter));
                addNewFood(ADD_NEW_ITEM);
                adapter.notifyDataSetChanged();
            }*/
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    };
    
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.food_screen);
		 populateList();
		 food_Home = (Button) this.findViewById(R.id.button1);
		 spFood = (Spinner) findViewById(R.id.spinner1);
		 
	        adapter = new SimpleAdapter(this, foodName, R.layout.spinner_item,
	                new String[] { NAME }, new int[] { R.id.tvName });
	        spFood.setAdapter(adapter);
	        spFood.setOnItemSelectedListener(itemSelectedListener);
	}
    private void populateList() {
        foodName = new ArrayList<HashMap<String, String>>();

        addNewFood("banana");
        addNewFood("apple");
        addNewFood("orange");
      //  addNewFood(ADD_NEW_ITEM);
    }
	
    private void addNewFood(String name) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(NAME, name);
        foodName.add(map);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onClick_Food_Home(View V){
		Intent myIntent = new Intent("se.shsu.fat.Home");
		startActivity(myIntent);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
