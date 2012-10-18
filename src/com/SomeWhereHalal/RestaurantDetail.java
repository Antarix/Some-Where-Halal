package com.SomeWhereHalal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class RestaurantDetail extends Activity {

	private String Query;
	private Util util;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_details);
		Intent RestaurantIntent = getIntent();
		util = new Util();
		if (RestaurantIntent.hasExtra("Query")) {
			Query = RestaurantIntent.getStringExtra("Query");
			String Url = "http://somewherehalal.com/lib/restaurant-detail.php?id=" + util.getEncodedString(Query);
						
			//Toast.makeText(getApplicationContext(),"URL : " +  Url, Toast.LENGTH_LONG).show();
			loadRestaurant(Url);

		}
	}
	
	public void loadRestaurant(String Url) {

		TextView txtName = (TextView)findViewById(R.id.txt_name);
		TextView txtCuisine = (TextView)findViewById(R.id.txt_cuisine);
		TextView txtDescription = (TextView)findViewById(R.id.txt_description);
		TextView txtAddress = (TextView)findViewById(R.id.txt_address);
		TextView txtCountry = (TextView)findViewById(R.id.txt_country);
		TextView txtState = (TextView)findViewById(R.id.txt_state);
		TextView txtZip = (TextView)findViewById(R.id.txt_zip);
		TextView txtPhone = (TextView)findViewById(R.id.txt_phone);
		TextView txtEmail = (TextView)findViewById(R.id.txt_email);
		TextView txtWeb = (TextView)findViewById(R.id.txt_web);
		
		
		
		try {
			JSONObject object = util.getJSONfromURL(Url);
			JSONObject json = (JSONObject) new JSONTokener(object.toString()).nextValue();
		    txtName.setText("Name :  " + json.getString("name"));
			txtCuisine.setText("Cuisine :  " + json.getString("cussine"));
			txtDescription.setText("Description :  " + json.getString("description"));
			txtAddress.setText("Address :  " + json.getString("address"));
			txtCountry.setText("Country :  " + json.getString("country"));
			txtState.setText("State :  " + json.getString("state"));
			txtZip.setText("Zip :  " + json.getString("zip"));
			txtPhone.setText("Phone :  " + json.getString("phone"));
			txtEmail.setText("Email :  " + json.getString("email"));
			txtWeb.setText("Web-address :  " + json.getString("web_address"));
			
			
		} catch (JSONException e) {
			Log.e("RESTAURANT DETAIL", "Error parsing data " + e.toString());
		}
		
		
		
		
		
	}

	
}
