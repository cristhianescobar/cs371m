package com.whosupnext;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class ListEvents extends ListActivity {

	private static EventArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mAdapter = new EventArrayAdapter(getApplicationContext());
        
        Intent intent = getIntent();
		switch (intent.getIntExtra("id", 0))
		{
			case 0:
		        new getEvents().execute();
		        break;
		}
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Event event = (Event) mAdapter.getItem(position);
    	
    	Intent intent = new Intent(getApplicationContext(), EventDetail.class);
		intent.putExtra("id", event.getId());
		
		startActivity(intent);
    }
    
    private class getEvents extends AsyncTask<Void, Void, Void>
    {
		@Override
		protected Void doInBackground(Void... params)
		{
	        try
	        {
				ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		        
				List<Event> results = query.find();
				for (Event event : results)
	            {
	                Log.d("ListEvent", "Found event: id = " + event.getId());
	            	mAdapter.add(event);
	            }
			}
	        catch (Exception e)
	        {
	        	Log.e("ListEvents", "Get Events: " + e.toString());
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
			return null;
		}
		
		protected void onPostExecute (Void result)
		{
			setListAdapter(mAdapter);
		}
    }
} 


