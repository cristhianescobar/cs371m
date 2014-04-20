package com.whosupnext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
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
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class AddEvent extends Activity {

	public static final String EVENTS_TABLE = "CreateEvent";
    private String ADD_EVENT = "AddEvent";
	private static Date mDate;
	
	private ParseUser mUser;
	private LatLng mLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);
		
		mUser = ParseUser.getCurrentUser();
		if (mUser == null)
		{
			Log.wtf(ADD_EVENT, "Must be logged in to add event!");
			assert(false);
		}
		
		LocationManager locMgr = ((LocationManager) getSystemService(LOCATION_SERVICE));
        Location location = locMgr.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        mLocation = new LatLng(location.getLatitude(), location.getLongitude());

		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
		map.addMarker(new MarkerOptions().position(mLocation));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 16));
		
		mDate = new Date();
		
		Button time = (Button) findViewById(R.id.time_value);
		time.setText(parseTime(mDate));
		
		Button date = (Button) findViewById(R.id.date_value);
		date.setText(parseDate(mDate));
	}
	
	public void updateMap(View V)
	{
		String address = ((EditText) findViewById(R.id.location_value)).getText().toString();
		
		Geocoder geocoder = new Geocoder(this, Locale.US);
		
		try
		{
			Address location = geocoder.getFromLocationName(address, 1).get(0);
			mLocation = new LatLng(location.getLatitude(), location.getLongitude());
		}
		catch (Exception e)
		{
			Log.e("AddEvent", e.getMessage());
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		
		GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.location_map)).getMap();
		map.addMarker(new MarkerOptions().position(mLocation));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, 16));
	}
	
	public void submitEvent(View v)
	{
		try
		{
			EditText name = (EditText) findViewById(R.id.name_value);
			EditText sport = (EditText) findViewById(R.id.sport_value);
			EditText details = (EditText) findViewById(R.id.details_value);
			
			final Context context = this;
			
			final Event event = new Event();
			event.setName(name.getText().toString());
			event.setDate(mDate);
			event.setSport(sport.getText().toString());
			event.setLocation(mLocation);
			event.setDetails(details.getText().toString());
			event.setHost(mUser);
			event.saveInBackground(new SaveCallback()
			{
				public void done(ParseException e)
				{
					if (e == null)
					{
						Log.d(ADD_EVENT, "Created event " + event.getId());
						
						Intent intent = new Intent(context, EventDetail.class);
						intent.putExtra("id", event.getId());
						startActivity(intent);
						finish();
					}
					else
					{
						Log.e(ADD_EVENT, e.toString());
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			});
			
			

			
		} 
		catch (IllegalArgumentException e)
		{
			Log.e(ADD_EVENT, e.toString());
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the value of mDate as the default date in the picker
			int year = mDate.getYear() + 1900;
			int month = mDate.getMonth();
			int day = mDate.getDate();
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			mDate.setYear(year - 1900);
			mDate.setMonth(month);
			mDate.setDate(day);

			Button date = (Button) findViewById(R.id.date_value);
			date.setText(parseDate(mDate));
		}

	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the value of mDate as the default values for the picker
			int hour = mDate.getHours();
			int minute = mDate.getMinutes();

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hour, int minute) {
			mDate.setHours(hour);
			mDate.setMinutes(minute);

			Button time = (Button) findViewById(R.id.time_value);
			time.setText(parseTime(mDate));
		}
	}

	public static String parseTime(Date time) {
		int hour = time.getHours();
		int minute = time.getMinutes();
		
		if (hour < 12) {
			if (hour == 0) {
				hour = 12;
			}
			if (minute < 10)
			{
				return hour + ":0" + minute + " AM";
			}
			else
			{
				return hour + ":" + minute + " AM";
			}
		} else {
			hour = hour - 12;
			if (hour == 0) {
				hour = 12;
			}
			if (minute < 10)
			{
				return hour + ":0" + minute + " PM";
			}
			else
			{
				return hour + ":" + minute + " PM";
			}
		}
	}

	public static String parseDate(Date date) {
		int year = date.getYear() + 1900;
		int month = date.getMonth();
		int day = date.getDate();
		
		String m = "None";
		switch ((int) month) {
		case 0:
			m = "Jan";
			break;
		case 1:
			m = "Feb";
			break;
		case 2:
			m = "Mar";
			break;
		case 3:
			m = "Apr";
			break;
		case 4:
			m = "May";
			break;
		case 5:
			m = "Jun";
			break;
		case 6:
			m = "Jul";
			break;
		case 7:
			m = "Aug";
			break;
		case 8:
			m = "Sept";
			break;
		case 9:
			m = "Oct";
			break;
		case 10:
			m = "Nov";
			break;
		case 11:
			m = "Dec";
			break;
		}

		return m + " " + day + ", " + year;
	}
	
}
