package com.whosupnext;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;


public class ListEvents extends ListActivity {

	private static EventArrayAdapter mAdapter;
//	private static ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mAdapter = new EventArrayAdapter(this);
//        mDialog = new ProgressDialog(this);
//        mDialog.setIndeterminate(true);
//        mDialog.setMessage("Loading...");
//        new getEvents().execute();
        findEvents();
        
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Intent intent = new Intent(this, EventDetail.class);
    	Event event = (Event) mAdapter.getItem(position);
		intent.putExtra("id", event.getId());
		startActivity(intent);
    }
    
    private void findEvents ()
    {
    	ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

        try
        {
			List<Event> results = query.find();
			for (Event event : results)
            {
                Log.d("ListEvent", "Found event id=" + event.getId());
            	mAdapter.add(event);
            }
			setListAdapter(mAdapter);
		}
        catch (ParseException e)
        {
        	Log.e("ListEvents", e.getMessage());
		}
    }
    
//    private class getEvents extends AsyncTask<Void, Void, Void>
//    {
//		@Override
//		protected Void doInBackground(Void... params)
//		{
//			ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
//	        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
//
//	        try
//	        {
//				List<Event> results = query.find();
//				for (Event event : results)
//	            {
//	                Log.d("ListEvent", "Found event id=" + event.getId());
//	            	mAdapter.add(event);
//	            }
//			}
//	        catch (ParseException e)
//	        {
//	        	Log.e("ListEvents", e.getMessage());
//			}
//			return null;
//		}
//		
//		protected void onPreExecute ()
//		{
//			mDialog.show();
//		}
//		
//		protected void onPostExecute (Void result)
//		{
//			setListAdapter(mAdapter);
//			mDialog.dismiss();
//		}
//    }
} 


