package com.whosupnext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventDetail extends Activity {
	
	private String objectId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		String date = intent.getStringExtra("date");
		String location = intent.getStringExtra("location");
		String phone = intent.getStringExtra("phone");
		String details = intent.getStringExtra("details");
		objectId = intent.getStringExtra("id");
		
		
		((TextView) findViewById(R.id.name_value)).setText(name);
		((TextView) findViewById(R.id.date_value)).setText(AddEvent.parseDate(date));
		((TextView) findViewById(R.id.time_value)).setText(AddEvent.parseTime(date));
		((TextView) findViewById(R.id.phone_value)).setText(phone);
		((TextView) findViewById(R.id.location_value)).setText(location);
		((TextView) findViewById(R.id.details_value)).setText(details);
		
	}
	
	public void deleteEvent(View v) {
		Event.delete(AddEvent.EVENTS_TABLE, objectId);
		 Intent intent = new Intent(this, MainActivity.class);
	     startActivity(intent);
	}
}
