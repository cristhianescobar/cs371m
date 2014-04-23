package com.whosupnext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class SignIn extends Activity
{
	private Context mContext;
	private ProgressDialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		mContext = this;
	}
	
	public void submit (View v)
	{
		TextView email_label = (TextView) findViewById(R.id.email_label);
		TextView password_label = (TextView) findViewById(R.id.password_label);
		EditText email_value = (EditText) findViewById(R.id.email_value);
		EditText password_value = (EditText) findViewById(R.id.password_value);
		Button submit = (Button) findViewById(R.id.submit_button);
		
		try
		{
			// Hide keyboard
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(submit.getWindowToken(), 0);
			
			// Set label to black
			email_label.setTextColor(Color.BLACK);
			password_label.setTextColor(Color.BLACK);
			
			String email = email_value.getText().toString();
			String password = password_value.getText().toString();
			if (email.isEmpty() || password.isEmpty())
				throw new IllegalArgumentException("Fill all required fields.");
			
			// Set Submit Button to unclickable
			submit.setClickable(false);
			
			// Start Dialog
			mDialog = new ProgressDialog(mContext);
			mDialog.setMessage("Signing In...");
			mDialog.setIndeterminate(true);
			mDialog.setCancelable(false);
			mDialog.show();
			
			new signIn().execute();
		}
		catch (IllegalArgumentException e)
		{
			// Always clear password and set to red
			password_value.setText("");
			password_label.setTextColor(Color.RED);
			password_value.requestFocus();
			
			// If empty, set email to red
			if (email_value.getText().toString().isEmpty())
			{
				email_label.setTextColor(Color.RED);
				email_value.requestFocus();
			}
			
			Log.e("SignIn", "Submit:" + e.toString());
			Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private class signIn extends AsyncTask<Void, Void, Void>
    {
		String msg = "";
		@Override
		protected Void doInBackground(Void... params)
		{
	        try
	        {
	        	String email = ((EditText) findViewById(R.id.email_value)).getText().toString();
	    		String password = ((EditText) findViewById(R.id.password_value)).getText().toString();
				
	    		ParseUser.logIn(email, password);
			}
	        catch (ParseException e)
	        {
    			Log.e("SignIn", "SignIn: " + e.toString());
	        	switch (e.getCode())
	        	{
	        		case 100:
	        			msg = "Check network connection.";
	        			break;
	        		case 101:
	        			msg = "Invalid email and/or password.";
	        			break;
	        		default:
	        			msg = "An unknown problem has occured (" + e.getCode() + ").";
	        			break;
	        	}
			}
			return null;
		}
		
		protected void onPostExecute (Void result)
		{
			if (mDialog != null)
			{
				mDialog.dismiss();
			}
			
			if (msg.isEmpty())
			{
				finish();
			}
			else
			{
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();	        	
	        	
	        	// Set button clickable
	        	Button submit = ((Button) findViewById(R.id.submit_button));
				submit.setClickable(true);
				
				// Clear password and focus
	    		EditText password = (EditText) findViewById(R.id.password_value);
				password.setText("");
				password.requestFocus();
			}
		}
    }
}
