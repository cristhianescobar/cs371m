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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.Locale;

public class EventDetail extends Activity
{
	private static Event mEvent;
	private static loadingDialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
        mDialog = new loadingDialog(this);
		
		Intent intent = getIntent();
		String objectId = intent.getStringExtra("id");
		
		Log.d("EventDetail", "Details for event " + objectId);
		
		mDialog.startLoading();
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		try
		{
			mEvent = query.get(objectId);
		}
		catch (Exception e)
		{
			Log.e("EventDetail", e.getMessage());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
		mDialog.stopLoading();
		
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
		catch (ParseException e1)
		{
			Log.wtf("EventDetail", e1.getMessage());
			assert(false);
		}
		Date date = mEvent.getDate();
		
		((TextView) findViewById(R.id.date_value)).setText(AddEvent.parseDate(date));
		((TextView) findViewById(R.id.time_value)).setText(AddEvent.parseTime(date));
		((TextView) findViewById(R.id.sport_value)).setText(mEvent.getSport());
		((TextView) findViewById(R.id.location_value)).setText(parseToAddress(mEvent.getLocation()));
		((TextView) findViewById(R.id.details_value)).setText(mEvent.getDetails());
		
		Button delete = ((Button) findViewById(R.id.delete_event));
		
		ParseUser current = ParseUser.getCurrentUser();
		if (current == null)
		{
			delete.setVisibility(View.GONE);
		}
		else if (host.getEmail().equals(current.getEmail()))
		{
			delete.setVisibility(View.VISIBLE);
		}
		else
		{
			delete.setVisibility(View.GONE);
		}
	}
	
	public void deleteEvent(View v)
	{
		mEvent.deleteInBackground();
		finish();
	}
	
	public String parseToAddress(ParseGeoPoint location){
	Geocoder geocoder = new Geocoder(this, Locale.US);
		String address = "";
		try
		{
			Address tmp = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1).get(0);
			
			for(int i=0;i<tmp.getMaxAddressLineIndex();i++){
				address += tmp.getAddressLine(i) + "\n";
			}
			address = address.substring(0,address.length() -1);
			
			LatLng loc = new LatLng (location.getLatitude(), location.getLongitude());
			GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
			map.addMarker(new MarkerOptions().position(loc));
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
			
		}
		catch (Exception e)
		{
			Log.e("Event Detail", e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			address= "Address not found";
		}
		return address;
	}
}
