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
		}
		catch (Exception e)
		{
			Log.e("EventDetail", e.getMessage());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		displayData();
	}
	
	public void displayData()
	{
		((TextView) findViewById(R.id.name_value)).setText(mEvent.getName());
		ParseUser host = mEvent.getHost();
		try
		{
			((TextView) findViewById(R.id.host_value)).setText(host.fetchIfNeeded().getEmail());
			((TextView) findViewById(R.id.phone_value)).setText(host.fetchIfNeeded().getNumber("phone").toString());
		}
		catch (ParseException e)
		{
			Log.wtf("EventDetail", e.getMessage());
			assert(false);
		}
		Date date = mEvent.getDate();
		
		((TextView) findViewById(R.id.date_value)).setText(AddEvent.parseDate(date));
		((TextView) findViewById(R.id.time_value)).setText(AddEvent.parseTime(date));
		((TextView) findViewById(R.id.sport_value)).setText(mEvent.getSport());
		((TextView) findViewById(R.id.location_value)).setText(parseAddress(mEvent.getLocation()));
		((TextView) findViewById(R.id.details_value)).setText(mEvent.getDetails());
		
		Button delete = ((Button) findViewById(R.id.delete_event));
		
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
	
	public void deleteEvent(View v)
	{
		mEvent.deleteInBackground();
		finish();
	}
	
	public String parseAddress(LatLng location){
	Geocoder geocoder = new Geocoder(this, Locale.US);
		String address = "";
		try
		{
			Address tmp = geocoder.getFromLocation(location.latitude, location.longitude,1).get(0);
			
			for(int i=0;i<tmp.getMaxAddressLineIndex();i++){
				address += tmp.getAddressLine(i);
			}
			address = address.substring(0,address.length() -1);
			
			GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
			map.getUiSettings().setScrollGesturesEnabled(false);
			map.addMarker(new MarkerOptions().position(location));
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
		}
		catch (Exception e)
		{
			Log.e("EventDetail", e.toString() );
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			address= "Address not found\nLat: " + location.latitude + ", Log: " + location.longitude;
		}
		return address;
	}
}
