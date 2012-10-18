package com.SomeWhereHalal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.spec.EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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


public class RestaurantList extends Activity {

	private Util util;
	private String[] SearchQuery;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_list);
		Intent RestaurantIntent = getIntent();
		util = new Util();	
		if (RestaurantIntent.hasExtra("SearchQuery")) {
			SearchQuery = RestaurantIntent.getStringArrayExtra("SearchQuery");
			
			String Url = "http://somewherehalal.com/lib/search.php?country="
					+ util.getEncodedString(SearchQuery[0]) + "&state=" + util.getEncodedString(SearchQuery[1]);
			loadRestaurantList(Url);

		}
	}
	
	

	public void loadRestaurantList(String Url) {

		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();

		JSONObject json = util.getJSONfromURL(Url);
		try {
			// Get the element that holds the earthquakes ( JSONArray )
			JSONArray RestaurantList = json.getJSONArray("Restaurants");
			// Loop the Array
			for (int i = 0; i < RestaurantList.length(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				JSONObject Restaurant = RestaurantList.getJSONObject(i);
				map.put("id", Restaurant.getString("id"));
				map.put("name", Restaurant.getString("name"));
				map.put("address", "Address: " + Restaurant.getString("address"));
				map.put("country", "Country: " + Restaurant.getString("country"));
				map.put("state", "State: " + Restaurant.getString("state"));
				mylist.add(map);
				

			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());

		}
		
		String[] from = new String[]{"name","address","country","state"};
        int[] to = new int[]{R.id.txt_name,R.id.txt_address,R.id.txt_country,R.id.txt_state};
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
				
				Intent detailIntent = new Intent(getApplicationContext(),RestaurantDetail.class);
				
				detailIntent.putExtra("Query", o.get("id").toString());
				startActivity(detailIntent);
				//Toast.makeText(getApplicationContext(),"ID '" + o.get("id") + "' was clicked.",Toast.LENGTH_SHORT).show();

			}
		});

	}



}
