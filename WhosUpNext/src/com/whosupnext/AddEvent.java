package com.whosupnext;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.parse.ParseObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class AddEvent extends Activity {
	
	

	static Date mDate = new Date();
    EditText mEventName;
    EditText mPhoneNumber;
    EditText mLocation;
    EditText mDetails;
    Map<String, String> mEventMap;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);

        mEventName = (EditText) findViewById(R.id.EventName);
        mPhoneNumber = (EditText) findViewById(R.id.Phone) ;
        mLocation = (EditText) findViewById(R.id.Location);
        mDetails = (EditText) findViewById(R.id.Details);

	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}

    public void generateEvent(View v){

        ParseObject event = new ParseObject("Event");
//        mEventMap = new HashMap<String, String>();
//        mEventMap.put("Name", mEventName.toString());
//        mEventMap.put("Date",mDate.toString());
//        mEventMap.put("Phone", mPhoneNumber.toString());
//        mEventMap.put("Location", mLocation.toString());
//        mEventMap.put("Details", mDetails.toString());

        event.put("Name", mEventName.getText().toString());
        event.put("Date",mDate.toString());
        event.put("Phone", mPhoneNumber.getText().toString());
        event.put("Location", mLocation.getText().toString());
        event.put("Details", mDetails.getText().toString());


        //DrewMenu?

        event.saveInBackground();

    }
	
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			mDate.setYear(year);
			mDate.setMonth(month);
			mDate.setDate(day);

            Button time = (Button) findViewById(R.id.Date);
            time.setText( month + "/" + day +  "/" + year);
		}
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mDate.setHours(hourOfDay);
			mDate.setMinutes(minute);

            Button time = (Button) findViewById(R.id.Time);
            time.setText(mDate.getHours() + ":" +mDate.getMinutes());
		}
	}
}