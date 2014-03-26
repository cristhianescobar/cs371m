package com.whosupnext;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		
	}
	
	public void submit (View v)
	{
		try
		{
			ParseUser user = new ParseUser();
			
			String email = ((EditText) findViewById(R.id.email_value)).getText().toString();
			String password = ((EditText) findViewById(R.id.password_value)).getText().toString();
			int phone = Integer.parseInt(((EditText) findViewById(R.id.phone_value)).getText().toString());
				
			user.setUsername(email);
			user.setPassword(password);
			user.setEmail(email);
			user.put("phone", phone);
			
			user.signUpInBackground(new SignUpCallback()
			{
				public void done(ParseException e)
				{
					if (e == null)
					{
						finish();
				    }
					else
					{
						Log.d("SignUp", "Parse Error: " + e.getMessage());
//						if (e.getMessage().substring(start, end))
					}
				}
			});
		}
		catch (Exception e)
		{
			Log.d("SignUp", e.getMessage());
			String msg = "";
			if (e.getMessage().substring(0, 11).equals("Invalid int"))
			{
				msg = "Invalid phone number, use only numbers.";
			}
			else if (e.getMessage().equals("Username cannot be missing or blank"))
			{
				msg = "Email is blank, this field is required";
			}
			else
			{
				msg = "An unknown error ocurred, try again.";
			}
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}
	}
}
