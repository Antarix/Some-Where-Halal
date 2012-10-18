package com.SomeWhereHalal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splashscreen);
		Thread splashThread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
						int waited = 0;
						while (waited < 3000) 
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
