package com.whosupnext;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class MainActivity extends Activity {

	private static boolean parseInit = false;
    private static String  CLASS = "Main Activity";
    private final String [] MENU = {"Nearby Events","Upcoming Events", "My Events", "New Event", "Sign In","Sign Up","Sign Out","Settings", "About"};
    
    ListView listView ;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		// Get ListView object from xml
        listView = (ListView) findViewById(R.id.left_drawer);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, MENU);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        		R.layout.menu_item, R.id.menu_item_id, MENU);

        
        listView.setAdapter(adapter); 
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition     = position;
               
               // ListView Clicked item value
               String  itemValue    = (String) listView.getItemAtPosition(position);
                  
                // Show Alert 
                Toast.makeText(getApplicationContext(),"Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG).show();
             
              }

         }); 
		
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
	
	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/
	
	/*
	 * 
	 * Nearby Events
	 * Upcoming Events
	 * My Events
	 * New Event
	 * -----
	 * Sign In
	 * Sign Up
	 * Sign Out
	 * -----
	 * About
	 * Settings
	 * 
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
		intent.putExtra("id", 0);
		
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
	
	public void clearCache (View v)
	{
		Log.d("MainActivity", "Clearing Cache");
		ParseQuery.clearAllCachedResults();
	}

}
