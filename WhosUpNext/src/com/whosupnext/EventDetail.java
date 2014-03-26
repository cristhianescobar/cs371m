package com.whosupnext;

import java.util.Date;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetail extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
		Intent intent = getIntent();
		String objectId = intent.getStringExtra("id");
		
		Log.d("EventDetail", "Details for event " + objectId);
		
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		query.getInBackground(objectId, new GetCallback<Event>()
		{
			public void done(Event event, ParseException e)
			{
				if (e == null)
				{
					Log.d("EventDetail", "Found Event " + (event != null));
					
					((TextView) findViewById(R.id.name_value)).setText(event.getName());
					ParseUser host = event.getHost();
					try
					{
						((TextView) findViewById(R.id.host_value)).setText(host.fetchIfNeeded().getEmail());
					}
					catch (ParseException e1)
					{
						Log.wtf("EventDetail", e1.getMessage());
						assert(false);
					}
					Date date = event.getDate();
					((TextView) findViewById(R.id.date_value)).setText(AddEvent.parseDate(date));
					((TextView) findViewById(R.id.time_value)).setText(AddEvent.parseTime(date));
					((TextView) findViewById(R.id.sport_value)).setText(event.getSport());
					((TextView) findViewById(R.id.location_value)).setText(event.getLocation().toString());
					((TextView) findViewById(R.id.details_value)).setText(event.getDetails());
					
					Button delete = ((Button) findViewById(R.id.delete_event));
					if (host.getEmail().equals(ParseUser.getCurrentUser().getEmail()))
					{
						delete.setVisibility(View.VISIBLE);
					}
					else
					{
						delete.setVisibility(View.GONE);
					}
				}
				else
				{
					Log.wtf("EventDetail", e.getMessage());
					assert(false);
				}
			}
		});
	}
	
	
	public void deleteEvent(View v)
	{
		
		finish();
	}
}
