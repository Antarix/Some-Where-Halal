package com.SomeWhereHalal;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.maps.GeoPoint;

public class Gplaces extends Activity {


	GeoPoint geoPoint;
	double latitude,longitude;
	String latLongString;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.restaurant_list);
        LocationUtils appLocationManager = new LocationUtils(getApplicationContext());
        String latitude = appLocationManager.getLatitude();
        String longitude = appLocationManager.getLongitude();
        latLongString =latitude+","+longitude;
       
        showRestaurants();
        
    }
    
        
    private void showRestaurants()
    {
    	ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    	
    	String Url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latLongString+"&radius=500&types=restaurant&sensor=true&key=AIzaSyAide80TRHLzcSxN71InfJ0zX8XHuzY8Hc";
    	//Toast.makeText(getApplicationContext(), Url, Toast.LENGTH_LONG).show();
    	JSONObject json = Util.getJSONfromURL(Url);
    	try {
			// Get the element that holds the earthquakes ( JSONArray )
		JSONArray RestaurantList = json.getJSONArray("results");
		
			// Loop the Array
			for (int i = 0; i < RestaurantList.length(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject Restaurant = RestaurantList.getJSONObject(i);
			
				map.put("name", Restaurant.getString("name"));
				//map.put("address", "Address: " + Restaurant.getString("formatted_address"));
				map.put("lng", Restaurant.getJSONObject("geometry").getJSONObject("location").getString("lng"));
				map.put("lat", Restaurant.getJSONObject("geometry").getJSONObject("location").getString("lat"));
				mylist.add(map);
				
    
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
    
		}
    	String[] from = new String[]{"name"};
    	int[] to = new int[]{R.id.txt_name};
    	final ListView listview = (ListView)findViewById(R.id.listview_restaurant);
	ListAdapter adapter = new SimpleAdapter(this, mylist, R.layout.restaurant_list_item,from,to);
	
	listview.setAdapter(adapter);
	//setListAdapter(adapter);
	
	listview.setTextFilterEnabled(true);
	listview.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			@SuppressWarnings("unchecked")
			HashMap<String, String> o = (HashMap<String, String>) listview
					.getItemAtPosition(position);
			
			String cord[]= new String[]{o.get("lat").toString(), o.get("lng").toString()};
			
			Intent myIntent = new Intent(getApplicationContext(), Gmap.class);
			
			myIntent.putExtra("cord",cord);
			
			startActivity(myIntent);
		//	Toast.makeText(getApplicationContext(),"ID '" + o.get("name") + "' was clicked.",Toast.LENGTH_SHORT).show();
   
		}
	});
   
    }
  
}
