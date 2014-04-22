package com.whosupnext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;

import java.util.Date;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class AddEvent extends Activity
{
	private ParseUser mUser;
	private Event mEvent;
	private LatLng mLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);

		mUser = ParseUser.getCurrentUser();
		if (mUser == null)
		{
			Log.wtf("AddEvent", "Must be logged in to add event!");
			assert(false);
		}
		
		mEvent = new Event();
		try
		{
			// Get current location
			LocationManager locMgr = ((LocationManager) getSystemService(LOCATION_SERVICE));
	        Location location = locMgr.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
	        LatLng address = new LatLng(location.getLatitude(), location.getLongitude());
	        
	        // Set up map
			GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
			map.getUiSettings().setScrollGesturesEnabled(false);
			map.addMarker(new MarkerOptions().position(address));
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 16));
	        
	        mLocation = address;
		}
		catch (Exception e)
		{
			Log.e("AddEvent", "Display Map: " + e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			
			mLocation = new LatLng(0, 0);
		}
		
		// Create an TextChangedListener and OnFocusChange to update map
		EditText location = (EditText) findViewById(R.id.location_value);
		location.addTextChangedListener(new TextWatcher()
		{
			// Calls Update Map After Each Space
			@Override
			public void afterTextChanged(Editable s)
			{
				if (s.charAt(s.length() - 1) == ' ')
	        	{
					updateMap();
	        	}
	        }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
		});
		location.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			// Calls Update Map on Focus Loss
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (!hasFocus)
				{
					updateMap();
				}
			}
		});
	}
	
	// Updates the map
	private void updateMap()
	{
		try
		{
    		EditText location = (EditText) findViewById(R.id.location_value);
    		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.US);
    		Address address = geocoder.getFromLocationName(location.getText().toString(), 1).get(0);
    		mLocation = new LatLng(address.getLatitude(), address.getLongitude());

    		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
    		map.clear();
    		map.addMarker(new MarkerOptions().position(mLocation));
    		map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 16));
        }
		catch (IndexOutOfBoundsException e)
		{
			Log.e("AddEvent", "No Valid Address Found");
		}
		catch (Exception e)
		{
        	Log.e("AddEvent", "TextChangeListener: " + e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	// Stuff all data in mEvent
	public void submitEvent(View v)
	{
		try
		{			
			// Name
			EditText name = (EditText) findViewById(R.id.name_value);
			mEvent.setName(name.getText().toString());
			
			// Date/Time
			DatePicker date = (DatePicker) findViewById(R.id.date_value);
			TimePicker time = (TimePicker) findViewById(R.id.time_value);
			mEvent.setDate(new Date(date.getYear() - 1900,
								   date.getMonth(),
								   date.getDayOfMonth(), 
					               time.getCurrentHour(),
					               time.getCurrentMinute()));
			
			// Sport
			EditText sport = (EditText) findViewById(R.id.sport_value);
			mEvent.setSport(sport.getText().toString());
			
			// Location
			mEvent.setLocation(mLocation);
			
			// Details
			EditText details = (EditText) findViewById(R.id.details_value);
			mEvent.setDetails(details.getText().toString());
			
			// Host
			mEvent.setHost(mUser);
			
			// Run Save in AsyncTask
			new saveEvent().execute();
			
			// Set Submit Button to unclickable
			Button submit = ((Button) findViewById(R.id.submit_button));
			submit.setClickable(false);
		} 
		catch (IllegalArgumentException e)
		{
			Log.e("AddEvent", e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	// Saves Event in AsyncTask
	private class saveEvent extends AsyncTask<Void, Void, Void>
    {
		@Override
		protected Void doInBackground(Void... params)
		{
	        try
	        {
				mEvent.save();
			}
	        catch (Exception e)
	        {
	        	Log.e("AddEvent", "Save Event: " + e.toString());
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return null;
		}
		
		protected void onPostExecute (Void result)
		{
			Intent intent = new Intent(getApplicationContext(), EventDetail.class);
			intent.putExtra("id", mEvent.getId());
			startActivity(intent);
			finish();
		}
    }
}
