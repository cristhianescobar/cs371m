package com.whosupnext;

import java.util.Date;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AddEvent extends Activity {

	public static final String EVENTS_TABLE = "CreateEvent";
	private static Date mDate;
	
	private ParseUser mUser;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);
		
		mUser = ParseUser.getCurrentUser();
		if (mUser == null)
		{
			Log.wtf("AddEvent", "Must be logged in to add event!");
			assert(false);
		}
		
		mDate = new Date();
		
		Button time = (Button) findViewById(R.id.time_value);
		time.setText(parseTime(mDate));
		
		Button date = (Button) findViewById(R.id.date_value);
		date.setText(parseDate(mDate));
	}
	
	public void generateEvent(View v)
	{
		try
		{
			EditText name = (EditText) findViewById(R.id.name_value);
			EditText sport = (EditText) findViewById(R.id.sport_value);
			EditText location = (EditText) findViewById(R.id.location_value);
			EditText details = (EditText) findViewById(R.id.details_value);
			
			Event newEvent = new Event();
			newEvent.setName(name.getText().toString());
			newEvent.setDate(mDate);
			newEvent.setSport(sport.getText().toString());
			newEvent.setLocation(new ParseGeoPoint(40.0, -30.0));
			newEvent.setDetails(details.getText().toString());
			newEvent.setHost(mUser);
			newEvent.saveInBackground();

			Intent intent = new Intent(this, EventDetail.class);
			intent.putExtra("id", newEvent.getId());

			startActivity(intent);
		} 
		catch (IllegalArgumentException e)
		{
			Log.e("ERROR", "ToString: " + e.toString());
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
		int hour = mDate.getHours();
		int minute = mDate.getMinutes();
		
		if (hour < 12) {
			if (hour == 0) {
				hour = 12;
			}
			return hour + ":" + minute + " AM";
		} else {
			hour = hour - 12;
			if (hour == 0) {
				hour = 12;
			}
			return hour + ":" + minute + " PM";
		}
	}

	public static String parseDate(Date date) {
		int year = mDate.getYear() + 1900;
		int month = mDate.getMonth();
		int day = mDate.getDate();
		
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
