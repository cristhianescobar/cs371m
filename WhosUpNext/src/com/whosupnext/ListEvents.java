package com.whosupnext;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ListEvents extends ListActivity {

	EventArrayAdapter eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        eventList = new EventArrayAdapter(this);
        
        eventsFromParse();
        
        
        setListAdapter(eventList);

		
    }

    private void eventsFromParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(AddEvent.EVENTS_TABLE);
        query.whereExists(Json.KEY_NAME);
        query.findInBackground(new FindCallback<ParseObject>() {


            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                for (ParseObject comment : parseObjects) {
                    // This does not require a network access.
                    ParseObject post = comment.getParseObject("post");
                    eventList.add(comment);
                    Log.d("post", "retrieved a related post");
                }
            }
        });



    }


} 
