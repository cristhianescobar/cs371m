package com.whosupnext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class MainActivity extends Activity {

	private static boolean parseInit = false;
    private static String  CLASS = "Main Activity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        /*Animation Splash Screen*/



        /*End Animation*/


		setContentView(R.layout.activity_main);
		
		if (!parseInit)
		{
			Log.d("MainActivity", "Initializing Parse");
	        //Application ID AaxBmFVakIxhS7XajgFg8CveAlMxyX5zifrU00If
	        //Client Key N9lPXcTEmixoJOkVNpxKb6CRpNoNkqP8LUetMvFv
			
			ParseObject.registerSubclass(Event.class);
	        Parse.initialize(this, "AaxBmFVakIxhS7XajgFg8CveAlMxyX5zifrU00If", "N9lPXcTEmixoJOkVNpxKb6CRpNoNkqP8LUetMvFv");
	        parseInit = true;
		}
		
        checkUser();
	}
	
	
	protected void onResume ()
	{
		super.onResume();
		checkUser();
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/
	
	public boolean checkUser ()
	{
		TextView welcome = ((TextView) findViewById(R.id.welcome));
        Button sign_in = ((Button) findViewById(R.id.sign_in));
        Button sign_up = ((Button) findViewById(R.id.sign_up));
        Button add_event = ((Button) findViewById(R.id.add_event));
        Button sign_out = ((Button) findViewById(R.id.sign_out));
        
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null)
        {
            welcome.setText("Welcome guest!");
            sign_in.setVisibility(View.VISIBLE);
            sign_up.setVisibility(View.VISIBLE);
            add_event.setVisibility(View.GONE);
            sign_out.setVisibility(View.GONE);
            return false;
        }
        else
        {
            welcome.setText("Welcome " + currentUser.getUsername() + "!");
            sign_in.setVisibility(View.GONE);
            sign_up.setVisibility(View.GONE);
            add_event.setVisibility(View.VISIBLE);
            sign_out.setVisibility(View.VISIBLE);
            return true;
        }  
	}
	
	public void signIn (View v)
	{
		Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
	}
	
	public void signUp (View v)
	{
		Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
	}
	
	public void listEvents(View v) {
        Intent intent = new Intent(this, ListEvents.class);
        startActivity(intent);
		
	}
	
	public void addEvent(View v) {
		Intent intent = new Intent(this, AddEvent.class);
		startActivity(intent);
	}
	
	public void signOut (View v)
	{
		Log.d(CLASS, "signing out");
		ParseUser.logOut();
		Log.d(CLASS, "signed out");
		assert(!checkUser());
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		this.finish();
		
		Log.d(CLASS, "done");
	}

}
