package se.shsu.fat;

	 
	import java.util.ArrayList;
	import java.util.List;
	 
	import org.apache.http.NameValuePair;
	import org.apache.http.message.BasicNameValuePair;
	import org.json.JSONArray;
	import org.json.JSONException;
	import org.json.JSONObject;
	 
	import android.app.Activity;
	import android.app.ProgressDialog;
	import android.content.Intent;
	import android.os.AsyncTask;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.View;
	import android.widget.Button;
import android.widget.EditText;
	 
	public class Update_Account extends Activity {
	 
	    EditText etName;
	    EditText etpw;
	    EditText etemail;
	    Button btnSave;
	    
	 
	    String pid;
	 
	    // Progress Dialog
	    private ProgressDialog pDialog;
	 
	    // JSON parser class
	    JSONParser jsonParser = new JSONParser();
	 
	    // single product url
	    private static final String url_product_detials = "http://api.androidhive.info/android_connect/get_product_details.php";
	 
	    // url to update product
	    private static final String url_update_product = "http://api.androidhive.info/android_connect/update_product.php";
	 
	 
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    //private static final String TAG_PRODUCT = "product";
	    private static final String TAG_PID = "pid";
	    private static final String TAG_NAME = "name";
	    private static final String TAG_PASSWORD = "password";
	    private static final String TAG_EMAIL = "email";
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.updateaccount);
	 
	        // save button
	        btnSave = (Button) findViewById(R.id.updateButton);
	        
	 
	        // getting product details from intent
	        Intent i = getIntent();
	 
	        // getting product id (pid) from intent
	        pid = i.getStringExtra(TAG_PID);
	 
	        // Getting complete product details in background thread
	        new GetProductDetails().execute();
	 
	        // save button click event
	        btnSave.setOnClickListener(new View.OnClickListener() {
	 
	            @Override
	            public void onClick(View arg0) {
	                // starting background task to update product
	                new SaveProductDetails().execute();
	            }
	        });
	 
	 
	    }
	 
	    /**
	     * Background Async Task to Get complete product details
	     * */
	    class GetProductDetails extends AsyncTask<String, String, String> {
	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Update_Account.this);
	            pDialog.setMessage("Loading product details. Please wait...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	 
	        /**
	         * Getting product details in background thread
	         * */
	        protected String doInBackground(String... params) {
	 
	            // updating UI from Background Thread
	            runOnUiThread(new Runnable() {
	                public void run() {
	                    // Check for success tag
	                    int success;
	                    try {
	                        // Building Parameters
	                        List<NameValuePair> params = new ArrayList<NameValuePair>();
	                        params.add(new BasicNameValuePair("pid", pid));
	 
	                        // getting product details by making HTTP request
	                        // Note that product details url will use GET request
	                        JSONObject json = jsonParser.makeHttpRequest(
	                                url_product_detials, "GET", params);
	 
	                        // check your log for json response
	                        Log.d("Single Product Details", json.toString());
	 
	                        // json success tag
	                        success = json.getInt(TAG_SUCCESS);
	                        if (success == 1) {
	                            // successfully received product details
	                            JSONArray productObj = json
	                                    .getJSONArray(TAG_NAME); // JSON Array
	 
	                            // get first product object from JSON Array
	                            JSONObject product = productObj.getJSONObject(0);
	 
	                            // product with this pid found
	                            // Edit Text
	                            etName = (EditText) findViewById(R.id.name);
	                            etpw = (EditText) findViewById(R.id.pw);
	                            etemail = (EditText) findViewById(R.id.email);
	 
	                            // display product data in EditText
	                            etName.setText(product.getString(TAG_NAME));
	                            etpw.setText(product.getString(TAG_PASSWORD));
	                            etemail.setText(product.getString(TAG_EMAIL));
	 
	                        }else{
	                            // user with pid not found
	                        }
	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                    }
	                }
	            });
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once got all details
	            pDialog.dismiss();
	        }
	    }
	 
	    /**
	     * Background Async Task to  Save product Details
	     * */
	    class SaveProductDetails extends AsyncTask<String, String, String> {
	 
	        public String password;
	        public String email;

			/**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Update_Account.this);
	            pDialog.setMessage("Saving product ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	        }
	 
	        /**
	         * Saving product
	         * */
	        protected String doInBackground(String... args) {
	 
	            // getting updated data from EditTexts
	            String name = etName.getText().toString();
	            String password = etpw.getText().toString();
	            String email = etemail.toString();
	 
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair(TAG_PID, pid));
	            params.add(new BasicNameValuePair(TAG_NAME, name));
	            params.add(new BasicNameValuePair(TAG_PASSWORD, password));
	            params.add(new BasicNameValuePair(TAG_EMAIL, email));
	 
	            // sending modified data through http request
	            // Notice that update product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_update_product,
	                    "POST", params);
	 
	            // check json success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // successfully updated
	                    Intent i = getIntent();
	                    // send result code 100 to notify about product update
	                    setResult(100, i);
	                    finish();
	                } else {
	                    // failed to update product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog once product uupdated
	            pDialog.dismiss();
	        }
	    }
	 
}
