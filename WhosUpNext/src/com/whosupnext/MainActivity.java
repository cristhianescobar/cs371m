package com.whosupnext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Parse.initialize(this, "AaxBmFVakIxhS7XajgFg8CveAlMxyX5zifrU00If", "N9lPXcTEmixoJOkVNpxKb6CRpNoNkqP8LUetMvFv");


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void listEvents(View v) {
		
		
	}
	
	public void addEvent(View v) {
		Intent intent = new Intent(this, AddEvent.class);
		startActivity(intent);
	}

}
