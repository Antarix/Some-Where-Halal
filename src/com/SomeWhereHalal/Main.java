package com.SomeWhereHalal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class Main extends Activity {
	
	private Spinner spinnerCountry;
	private Spinner spinnerState;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		loadCountry();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void getNearby(View v)
	  {
		  Intent gPlaceIntent = new Intent(this,Gplaces.class);
		  startActivity(gPlaceIntent);
	  }
	public void showSearchResults(View v) {
		
		
		TextView txtZip = (TextView)findViewById(R.id.zip_code_text);
		String[] query = {spinnerCountry.getSelectedItem().toString(),spinnerState.getSelectedItem().toString(),txtZip.getText().toString()};
		Intent restaurantIntent = new Intent(this, RestaurantList.class);
		restaurantIntent.putExtra("SearchQuery", query);
		startActivity(restaurantIntent); 
	}
	
	public void loadCountry() {
		spinnerCountry = (Spinner) findViewById(R.id.spinner_country);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.country_array,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerCountry.setAdapter(adapter);
		spinnerCountry.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				Log.d("Selected Item", "Position : " + position);
				int pos = position + 1;
				loadState(pos);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

	}

	public void loadState(int position) {
		int country_array = R.array.england_state_array;

		if (position == 1) {
			country_array = R.array.england_state_array;
		}
		if (position == 2) {
			country_array = R.array.northern_ireland_state_array;
		}
		if (position == 3) {
			country_array = R.array.scotland_state_array;
		}
		if (position == 4) {
			country_array = R.array.wales_state_array;
		}
		spinnerState = (Spinner) findViewById(R.id.spinner_state);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, country_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerState.setAdapter(adapter);
	}

	
}
