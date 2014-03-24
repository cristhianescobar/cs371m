package com.whosupnext;

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
    private static Time mTime = new Time();
    private EditText mEventName;
    private EditText mPhoneNumber;
    private EditText mLocation;
    private EditText mDetails;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);

        mTime = new Time();
        mTime.setToNow();

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

    	
    	try{
    		Event request = new Event(EVENTS_TABLE);
	        request.setName(mEventName.getText().toString());
	        request.setDate(mTime.toString());
	        request.setPhoneNumber(mPhoneNumber.getText().toString());
	        request.setLocation(mLocation.getText().toString());
	        request.setDetails(mDetails.getText().toString());
	        request.send();
	
	
	        Intent intent = new Intent(this, MainActivity.class);
	        startActivity(intent);
    	}
    	catch(IllegalArgumentException e){
    		printToast(e.getMessage());
    	}
    }
	
    public void printToast(String text){
    	int duration = Toast.LENGTH_SHORT;
    	Context c = getApplicationContext();
    	Toast toast = Toast.makeText(c, text, duration);
    	
    	toast.show();
    }
    
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the value of mTime as the default date in the picker
			int year = mTime.year;
			int month = mTime.month;
			int day = mTime.monthDay;
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {

			mTime.year = year;
			mTime.month = month;
			mTime.monthDay = day;

            Button date = (Button) findViewById(R.id.Date);
            date.setText(parseDate(mTime.toString()));
		}

        
    }
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the value of mTime as the default values for the picker
			int hour = mTime.hour;
			int minute = mTime.minute;
			
			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
			DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hour, int minute) {
			mTime.hour = hour;
			mTime.minute = minute;

            Button time = (Button) findViewById(R.id.Time);
            time.setText(parseTime(mTime.toString()));
		}
	}
	
	public static String parseTime (String time)
	{
		Log.d("time", "Len: " + time.length());
		Integer hour = Integer.parseInt(time.substring(9, 11));
		String minute = time.substring(11, 13);
		if ( hour < 12)
		{
			if (hour == 0)
			{
				hour = 12;
			}
			return hour + ":" + minute + " AM";
		}
		else
		{
			hour  = hour - 12;
			if (hour == 0)
			{
				hour = 12;
			}
			return hour + ":" + minute + " PM";
		}
	}
	
	public static String parseDate (String date)
	{
		Log.d("date", "Len: " + date.length());
		Log.d("date", "Date: " + date);
		Log.d("date", "month: " + date.substring(4, 6));
		
		String year = date.substring(0, 4);
		Integer month = Integer.parseInt(date.substring(4, 6));
		String day = date.substring(6, 8);
		Log.d("date", "month: " + month);
		String m = "None";
        switch ((int) month) {
            case 1:
                m = "Jan";
                break;
            case 2:
                m = "Feb";
                break;
            case 3:
                m = "Mar";
                break;
            case 4:
                m = "Apr";
                break;
            case 5:
                m = "May";
                break;
            case 6:
                m = "Jun";
                break;
            case 7:
                m = "Jul";
                break;
            case 8:
                m = "Aug";
                break;
            case 9:
                m = "Sept";
                break;
            case 10:
                m = "Oct";
                break;
            case 11:
                m = "Nov";
                break;
            case 12:
                m = "Dec";
                break;
        }
		
        return m + " " + day + ", " + year;
	}
}
