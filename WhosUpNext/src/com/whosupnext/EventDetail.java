package com.whosupnext;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.Locale;

public class EventDetail extends Activity
{
	private static Event mEvent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
		Intent intent = getIntent();
		String objectId = intent.getStringExtra("id");
		Log.d("EventDetail", "Details for event " + objectId);
		
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
		try
		{
			mEvent = query.get(objectId);
			displayData();
		}
		catch (Exception e)
		{
			Log.e("EventDetail", "Getting Data: " + e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void displayData()
	{
		try
		{
			ParseUser host = mEvent.getHost();
			Date date = mEvent.getDate();
			LatLng location = mEvent.getLocation();
			
			// Display Data
			((TextView) findViewById(R.id.name_value)).setText(mEvent.getName());
			((TextView) findViewById(R.id.host_value)).setText(host.fetchIfNeeded().getEmail());
			((TextView) findViewById(R.id.phone_value)).setText(host.fetchIfNeeded().getNumber("phone").toString());
			((TextView) findViewById(R.id.date_value)).setText(DateTimeParser.date(date));
			((TextView) findViewById(R.id.time_value)).setText(DateTimeParser.time(date));
			((TextView) findViewById(R.id.sport_value)).setText(mEvent.getSport());
			((TextView) findViewById(R.id.details_value)).setText(mEvent.getDetails());
			((TextView) findViewById(R.id.location_value)).setText(parseAddress(location));
			

			// Setup Map
			GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
			map.getUiSettings().setScrollGesturesEnabled(false);
			map.addMarker(new MarkerOptions().position(location));
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
			
			// Choose whether or not 
			Button delete = ((Button) findViewById(R.id.delete_button));
			ParseUser current = ParseUser.getCurrentUser();
			if ((current == null) || (!host.getEmail().equals(current.getEmail())))
			{
				delete.setVisibility(View.GONE);
			}
			else
			{
				delete.setVisibility(View.VISIBLE);
			}
		}
		catch (Exception e)
		{
			Log.e("EventDetail", "Display Data: " + e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	// Gets string address from LatLng
	private String parseAddress(LatLng location)
	{
		try
		{
			Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.US);
			Address address = geocoder.getFromLocation(location.latitude, location.longitude,1).get(0);
			
			String output = address.getAddressLine(0);
			for(int i = 1; i < address.getMaxAddressLineIndex(); ++i)
			{
				output += "\n" + address.getAddressLine(i);
			}
			return output;
		}
		catch (Exception e)
		{
			Log.e("EventDetail", "Parse Address: " + e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			return "Address not found\nLat: " + location.latitude + ", Log: " + location.longitude;
		}
	}
	
	// Called by Delete Button
	public void deleteEvent(View v)
	{
		mEvent.deleteInBackground();
		Toast.makeText(getApplicationContext(), "Event deleted.", Toast.LENGTH_SHORT).show();
		finish();
	}
}
