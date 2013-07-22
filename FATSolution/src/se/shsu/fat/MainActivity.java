package se.shsu.fat;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	Button signInButton, signUpButton, forgotPwButton;
	EditText etUsername, etPw;	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 signInButton = (Button) this.findViewById(R.id.button1);
		 signUpButton = (Button) this.findViewById(R.id.button2);
		 forgotPwButton = (Button) this.findViewById(R.id.button3);
		 etUsername = (EditText) this.findViewById(R.id.editText1);
		 etPw = (EditText) this.findViewById(R.id.editText2);
	}
	public void onClick_SignIn(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent("se.shsu.fat.Home"));
		
		
	}
	public void onClick_SignUp(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent("se.shsu.Sign_up"));
		
		
	}
	public void onClick_Forgot_Pw(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent("se.shsu.Forgot_Pw"));
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
