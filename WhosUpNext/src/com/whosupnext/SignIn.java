package com.whosupnext;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		
	}
	
	public void submit (View v)
	{
		String email = ((EditText) findViewById(R.id.email_value)).getText().toString();
		String password = ((EditText) findViewById(R.id.password_value)).getText().toString();
		
		ParseUser.logInInBackground(email, password, new LogInCallback()
		{
			public void done(ParseUser user, ParseException e)
			{
				if (user != null)
				{
					finish();
			    }
				else
				{
					Log.e("SignIn", e.getMessage());
					String msg = "";
					if (e.getMessage().equals("invalid login credentials"))
					{
				    	msg = "Invalid email and/or password, try again.";
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
}
