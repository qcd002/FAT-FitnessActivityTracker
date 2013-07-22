package se.shsu.fat;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends Activity implements android.view.View.OnClickListener{

	Button view_Stats_Button;
	Button exercise_Button, btUpdate,Bteditstats;
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.home);
		 
	

}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	public void onClick_Stats(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent("se.shsu.editstats");
		startActivity(myIntent);		
	}
	public void onClick_Exercises(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent("se.shsu.Exercise");
		startActivity(myIntent);
			
	}
	
	public void onClick_Food(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent("se.shsu.Food");
		startActivity(myIntent);
			
	}
	public void onClick_Update_Stats(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent("se.shsu.Stats");
		startActivity(myIntent);
	}
	
	public void onClick_Update_Account(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent("se.shsu.Update_Account");
		startActivity(myIntent);
	}
	
	public void onClick_Help(View v) {
		// TODO Auto-generated method stub
		Intent myIntent = new Intent("se.shsu.Help");
		startActivity(myIntent);
	}
	public void onClickFacebook(View v) {
		Intent i =  new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
		startActivity(i);
		
	}
	
	
}