package com.whosupnext;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ListEvents extends ListActivity {

	private static EventArrayAdapter eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        eventList = new EventArrayAdapter(this);
        
        eventsFromParse();
        
        
        setListAdapter(eventList);		
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    	Intent intent = new Intent(this, EventDetail.class);
    	Event request = (Event) eventList.getItem(position);
    	intent.putExtra("id", request.getParseObjectID());
    	intent.putExtra("name", request.getName());
        intent.putExtra("date", request.getDate());
        intent.putExtra("location", request.getLocation());
        intent.putExtra("phone", request.getPhoneNumber());
        intent.putExtra("details", request.getDetails());
    	intent.putExtra("index", position);
    	startActivity(intent);
    }
    
    public static Event getEvent (int position)
    {
    	assert (position < 0 || position > eventList.getCount() - 1);
    	return (Event) eventList.getItem(position);
    }

    private void eventsFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(AddEvent.EVENTS_TABLE);
        query.whereExists(Event.KEY_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {


            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                for (ParseObject comment : parseObjects) {
                    
                    eventList.add(new Event(comment));
                    Log.d("post", "retrieved a related post");
                }
            }
        });



    }


} 
