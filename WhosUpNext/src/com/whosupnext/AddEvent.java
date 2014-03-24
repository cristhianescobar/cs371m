package com.whosupnext;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class AddEvent extends Activity {


    public static final String EVENTS_TABLE = "CreateEvent";
    private static Date mDate = new Date();
    private EditText mEventName;
    private EditText mPhoneNumber;
    private EditText mLocation;
    private EditText mDetails;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_event);

        mDate = new Date();

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

        Json request = new Json(EVENTS_TABLE);
        request.setName(mEventName.getText().toString());
        request.setDate(mDate.toString());
        request.setPhoneNumber(mPhoneNumber.getText().toString());
        request.setLocation(mLocation.getText().toString());
        request.setDetails(mDetails.getText().toString());
        request.send();


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

            Button date = (Button) findViewById(R.id.Date);
            String m = monthToString(mDate.getMonth());

            date.setText( m + " " + mDate.getDay() +  ", " + mDate.getYear());
		}

        private String monthToString(int month) {
            String m ="NONE";
            switch (month) {
                case 1:
                    m = "Jan";
                case 2:
                    m = "Feb";
                case 3:
                    m = "Mar";
                case 4:
                    m = "Apr";
                case 5:
                    m = "May";
                case 6:
                    m = "Jun";
                case 7:
                    m = "Jul";
                case 8:
                    m = "Aug";
                case 9:
                    m = "Sept";
                case 10:
                    m = "Oct";
                case 11:
                    m = "Nov";
                case 12:
                    m = "Dec";
            }
            return m;
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

            int h = mDate.getHours();
            String aorp = " AM";

            if(h > 12){
                h = h -12;
                aorp = " PM";
            }
            time.setText(h + ":" +mDate.getMinutes() + aorp);

		}
	}
}
