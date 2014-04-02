package com.whosupnext;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ListEvents extends ListActivity {

	private static EventArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        adapter = new EventArrayAdapter(this);
        
        eventsFromParse();
        
        setListAdapter(adapter);		
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Intent intent = new Intent(this, EventDetail.class);
    	Event event = (Event) adapter.getItem(position);
		intent.putExtra("id", event.getId());
		startActivity(intent);
		finish();
    }

    private void eventsFromParse() {
        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
        query.findInBackground(new FindCallback<Event>()
        {
            @Override
            public void done(List<Event> results, ParseException e)
            {
                for (Event event : results)
                {
                    Log.d("ListEvent", "Found event id=" + event.getId());
                	adapter.add(event);
                }
            }
        });
    }
} 
