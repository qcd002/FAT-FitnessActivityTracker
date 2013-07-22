package se.shsu.fat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class terms extends Activity implements android.view.View.OnClickListener{
	Button viewStats_Home;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.terms);
		 
		 viewStats_Home = (Button) this.findViewById(R.id.button1);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onClick_terms_Home(View V){
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
