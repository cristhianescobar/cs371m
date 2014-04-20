package com.whosupnext;


import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Event")
public class Event extends ParseObject
{
    private String NAME = "name";
    private String DATE = "date";
    private String SPORT = "sport";
    private String DETAILS = "details";
    private String LOCATION = "location";
    private String HOST = "host";

	public Event ()
	{
		
	}
	
	public String getId() {
		return getObjectId();
	}
	
	public String getName(){
        return getString(NAME);
    }
	
	public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Missing Event");
        put(NAME, name);
    }
	
	public Date getDate(){
        return getDate(DATE);
    }
	
	public void setDate(Date date) throws IllegalArgumentException {
        if (date == null)
            throw new IllegalArgumentException("Missing Date");
        put(DATE, date);
    }
	
	public String getSport(){
        return getString(SPORT);
    }
	
	public void setSport(String sport) throws IllegalArgumentException {
        if (sport == null || sport.equals(""))
            throw new IllegalArgumentException("Missing Sport");
        put(SPORT, sport);
    }
	
	public String getDetails(){
        return getString(DETAILS);
    }
	
	public void setDetails(String details) {
        put(DETAILS, details);
    }
	
	public ParseGeoPoint getLocation(){
        return getParseGeoPoint(LOCATION);
    }
	
	public void setLocation(LatLng location) throws IllegalArgumentException {
        if (location == null)
            throw new IllegalArgumentException("Missing Location");
        put(LOCATION, new ParseGeoPoint(location.latitude, location.longitude));
    }
	
	public ParseUser getHost() {
		return getParseUser(HOST);
	}
	
	public void setHost(ParseUser host) throws IllegalArgumentException {
        if (host == null)
            throw new IllegalArgumentException("Missing Host");
        put(HOST, host);
    }
}