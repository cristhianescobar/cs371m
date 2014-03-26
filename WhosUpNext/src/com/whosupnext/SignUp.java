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
			if (email.equals(""))
			{
				throw new Exception("email is empty");
			}
			
			String password = ((EditText) findViewById(R.id.password_value)).getText().toString();
			if (password.equals(""))
			{
				throw new Exception("password is empty");
			}
			if (password.length() < 6)
			{
				throw new Exception("password too short");
			}
			
			String phone = ((EditText) findViewById(R.id.phone_value)).getText().toString();
			if (phone.length() != 10)
			{
				throw new Exception("invalid phone number");
			}
			
			user.setUsername(email);
			user.setPassword(password);
			user.setEmail(email);
			user.put("phone", Integer.parseInt(phone));
			
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
						Log.e("SignUp", e.getMessage());
						String msg = "";
						if (e.getMessage().substring(0, 13).equals("invalid email"))
						{
							msg = "Invalid Email";
						}
						if (e.getMessage().substring(0, 8).equals("username"))
						{
							msg = "Email is already in use";
						}
						else
						{
							msg = "An unknown error ocurred, try again.";
						}
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
					}
				}
			});
		}
		catch (Exception e)
		{
			Log.e("SignUp", e.getMessage());
			String msg = "";
			if (e.getMessage().substring(0, 11).equals("Invalid int"))
			{
				msg = "Invalid phone number, use only numbers.";
			}
			else if (e.getMessage().equals("Username cannot be missing or blank"))
			{
				msg = "Email is blank, this field is required";
			}
			else if (e.getMessage().equals("email is empty"))
			{
				msg = "Email is blank, this field is required";
			}
			else if (e.getMessage().equals("password is empty"))
			{
				msg = "Password is blank, this field is required";
			}
			else if (e.getMessage().equals("password too short"))
			{
				msg = "Password is too short, must be at least 6 characters";
			}
			else if (e.getMessage().equals("invalid phone number"))
			{
				msg = "Invalid phone number, must be 10 digits";
			}
			else
			{
				msg = "An unknown error ocurred, try again.";
			}
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}
	}
}
