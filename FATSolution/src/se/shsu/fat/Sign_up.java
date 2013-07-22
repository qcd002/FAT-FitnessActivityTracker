package se.shsu.fat;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Sign_up extends Activity implements android.view.View.OnClickListener{
	//Button viewStats_Home;
	EditText userName, pw, confirmPw, name, email;
	Button submit, cancel;
	RadioGroup gender;
	RadioButton sexButton;
	
	
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 this.setContentView(R.layout.sign_up);
		 
		// viewStats_Home = (Button) this.findViewById(R.id.button1);
		 userName = (EditText) findViewById(R.id.userName);
		 pw = (EditText) findViewById(R.id.pw);
		 confirmPw = (EditText) findViewById(R.id.confirmPw);
		 name = (EditText) findViewById(R.id.name);
		 email = (EditText) findViewById(R.id.email);
		 
		 submit = (Button)findViewById(R.id.submit);
		 cancel = (Button)findViewById(R.id.cancel);
		 gender = (RadioGroup) findViewById(R.id.gender);
		 
		 submit.setOnClickListener(new View.OnClickListener() {
		//	 if( strPw.equals(strConfirmPw)){
				public void onClick(View v) {
					
		String strPw = pw.getText().toString();
		String strConfirmPw = confirmPw.getText().toString();
		
					
		if( strPw.equals(strConfirmPw)){
					
					int selectedId = gender.getCheckedRadioButtonId();
					sexButton = (RadioButton) findViewById(selectedId);

					Toast.makeText(getBaseContext(),
							"Please wait, connecting to server.",
							Toast.LENGTH_SHORT).show();
					Thread background = null;
					try {
						background = new Thread(new Runnable() {

							String encUserName = URLEncoder.encode(userName.getText()
									.toString(), "UTF-8");
							String encPw = URLEncoder.encode(pw.getText()
									.toString(), "UTF-8");
							String encConfirmPw = URLEncoder.encode(confirmPw.getText()
									.toString(), "UTF-8");
							String encName = URLEncoder.encode(name
									.getText().toString(), "UTF-8");
							String encEmail = URLEncoder.encode(email
									.getText().toString(), "UTF-8");
							String selectedGender = (String) sexButton.getText();
							String encGender = URLEncoder.encode(selectedGender,"UTF-8");
							
							
							private final HttpClient Client = new DefaultHttpClient();

							private String URL = "http://webeng.comuf.com/xmlphp/insertStudent.php?userName="
									+ encUserName
									+ "&pw="
									+ encPw
									+ "&confirmPw="
									+ encConfirmPw
									+ "&name=" + encName
									+ "&email=" + encEmail
									+ "&gender=" + encGender;
						
							
							// After call for background.start this run method call
							public void run() {
								try {

									String SetServerString = "";
									HttpGet httpget = new HttpGet(URL);
									ResponseHandler<String> responseHandler = new BasicResponseHandler();
									SetServerString = Client.execute(httpget,
											responseHandler);
									threadMsg(SetServerString);

								} catch (Throwable t) {
									// just end the background thread
									Log.i("Animation", "Thread  exception " + t);
								}
							}

							private void threadMsg(String msg) {

								if (!msg.equals(null) && !msg.equals("")) {
									Message msgObj = handler.obtainMessage();
									Bundle b = new Bundle();
									b.putString("message", msg);
									msgObj.setData(b);
									handler.sendMessage(msgObj);
								}
							}

							// Define the Handler that receives messages from the
							// thread
							// and update the progress
							private final Handler handler = new Handler() {

								public void handleMessage(Message msg) {

									String aResponse = msg.getData().getString(
											"message");

									if ((null != aResponse)) {

										// ALERT MESSAGE
										Toast.makeText(getBaseContext(),
												"Server Response: " + "Data Sent to Server",
												Toast.LENGTH_SHORT).show();
									} else {

										// ALERT MESSAGE
										Toast.makeText(getBaseContext(),
												"Not Got Response From Server.",
												Toast.LENGTH_SHORT).show();
									}

								}
							};

						});
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.print("Sucker");
						/*Toast.makeText(getBaseContext(),
								"Not Successful!!",
								Toast.LENGTH_SHORT).show();*/
					}
					// Start Thread
					background.start(); // After call start method thread called run
										// Method
				
		}
					else{
					Toast.makeText(getBaseContext(),
								"Confirm Password DO NOT Match!!",
								Toast.LENGTH_SHORT).show();
					}	
				}
		 
		
		 	
				
		 });
	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onClick_ViewStats_Home(View V){
	
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
