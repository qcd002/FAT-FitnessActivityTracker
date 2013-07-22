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
	 
	public class Editstats extends Activity {
	 
		EditText etName, etheight, etweight, etage, etchest, etneck, etbicep, etwaist, etgoalweight;
	    //EditText txtPrice;
	    //EditText txtDesc;
	    //EditText txtCreatedAt;
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
	    //private static final String TAG_PRODUCTS = "products";
	    private static final String TAG_PID = "pid";
	    private static final String TAG_NAME = "name";
	    private static final String TAG_HEIGHT = "height";
	    private static final String TAG_WEIGHT = "weight";
	    private static final String TAG_AGE = "age";
	    private static final String TAG_CHEST = "chest";
	    private static final String TAG_NECK = "neck";
	    private static final String TAG_BICEP = "bicep";
	    private static final String TAG_WAIST = "waist";
	    private static final String TAG_GOALWEIGHT = "goalweight";
	    private static final String TAG_MAINTCALS = "maintcals";
	    private static final String TAG_BMI = "bmi";
	    private static final String TAG_BMR = "bmr";
	    private static final String TAG_MINHR = "minhr";
	    private static final String TAG_MAXHR = "maxhr";
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.editstats);
	        
	     // product with this pid found
	        etName = (EditText) findViewById(R.id.name);
            etheight = (EditText) findViewById(R.id.height);
            etweight = (EditText) findViewById(R.id.weight);
            etage = (EditText) findViewById(R.id.age);
            etchest = (EditText) findViewById(R.id.chest);
            etneck = (EditText) findViewById(R.id.neck);
            etbicep = (EditText) findViewById(R.id.bicep);
            etwaist = (EditText) findViewById(R.id.waist);
            etgoalweight = (EditText) findViewById(R.id.goalweight);
	        
	        // save button
	        btnSave = (Button) findViewById(R.id.button2);
	       
	 
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
	 
	    	//EditText txtName, height, weight, age, chest, neck, bicep, waist, goalweight;
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Editstats.this);
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
	                                    .getJSONArray(TAG_PID); 
	                            // JSON Array
	 
	                            // get first product object from JSON Array
	                            JSONObject product = productObj.getJSONObject(0);
	 
	                            
	                    
	 
	                            // display product data in EditText
	                            etName.setText(product.getString(TAG_NAME));
	                            etheight.setText(product.getString(TAG_HEIGHT));
	                            etweight.setText(product.getString(TAG_WEIGHT));
	                            etage.setText(product.getString(TAG_AGE));
	                            etchest.setText(product.getString(TAG_CHEST));
	                            etneck.setText(product.getString(TAG_NECK));
	                            etbicep.setText(product.getString(TAG_BICEP));
	                            etwaist.setText(product.getString(TAG_WAIST));
	                            etgoalweight.setText(product.getString(TAG_GOALWEIGHT));
	                            
	                        }else{
	                            // product with pid not found
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
	    	
	    	
	 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(Editstats.this);
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
	            String height = etheight.getText().toString();
	            String weight = etweight.getText().toString();
	            String age = etage.getText().toString();
	            String chest = etchest.getText().toString();
	            String neck = etneck.getText().toString();
	            String bicep = etbicep.getText().toString();
	            String waist = etwaist.getText().toString();
	            String goalweight = etgoalweight.getText().toString();
	            
	            
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair(TAG_PID, pid));
	            params.add(new BasicNameValuePair(TAG_NAME, name));
	            params.add(new BasicNameValuePair(TAG_HEIGHT, height));
	            params.add(new BasicNameValuePair(TAG_WEIGHT, weight));
	            params.add(new BasicNameValuePair(TAG_AGE, age));
	            params.add(new BasicNameValuePair(TAG_CHEST, chest));
	            params.add(new BasicNameValuePair(TAG_NECK, neck));
	            params.add(new BasicNameValuePair(TAG_BICEP, bicep));
	            params.add(new BasicNameValuePair(TAG_WAIST, waist));
	            params.add(new BasicNameValuePair(TAG_GOALWEIGHT, goalweight));
	 
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
