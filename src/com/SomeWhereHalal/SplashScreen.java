package com.SomeWhereHalal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		Thread splashThread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
						int waited = 0;
						while (waited < 2000) 
						{
							//Five second timer
							sleep(100);
							waited += 100;
						}
				}catch(InterruptedException e)
				{ //Any errors that might occur
					// do nothing
				}
				finally
				{
					finish();
					Intent myIntent= new Intent(getApplicationContext(),Main.class);
			    	startActivity(myIntent);
				}
			}
		};
		splashThread.start();
	}
}
