package com.whosupnext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

public class EventDetail extends Activity
{
	static Event mEvent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
		Intent intent = getIntent();
		String objectId = intent.getStringExtra("id");
		
		Log.d("EventDetail", "Details for event " + objectId);
		
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
		((TextView) findViewById(R.id.location_value)).setText(mEvent.getLocation().toString());
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
}
