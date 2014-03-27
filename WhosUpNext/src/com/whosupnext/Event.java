package com.whosupnext;


import java.util.Date;

import com.parse.*;

@ParseClassName("Event")
public class Event extends ParseObject
{
	public Event ()
	{
		
	}
	
	public String getId() {
		return getObjectId();
	}
	
	public String getName(){
        return getString("name");
    }
	
	public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Missing Event");
        put("name", name);
    }
	
	public Date getDate(){
        return getDate("date");
    }
	
	public void setDate(Date date) throws IllegalArgumentException {
        if (date == null)
            throw new IllegalArgumentException("Missing Date");
        put("date", date);
    }
	
	public String getSport(){
        return getString("sport");
    }
	
	public void setSport(String sport) throws IllegalArgumentException {
        if (sport == null || sport.equals(""))
            throw new IllegalArgumentException("Missing Sport");
        put("sport", sport);
    }
	
	public String getDetails(){
        return getString("details");
    }
	
	public void setDetails(String details) {
        put("details", details);
    }
	
	public ParseGeoPoint getLocation(){
        return getParseGeoPoint("location");
    }
	
	public void setLocation(ParseGeoPoint location) throws IllegalArgumentException {
        if (location == null)
            throw new IllegalArgumentException("Missing Location");
        put("location", location);
    }
	
	public ParseUser getHost() {
		return getParseUser("host");
	}
	
	public void setHost(ParseUser host) throws IllegalArgumentException {
        if (host == null)
            throw new IllegalArgumentException("Missing Host");
        put("host", host);
    }
}






/*
//@ParseClassName("Event")
public class Event {

    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_PHONE_NUMBER = "phone";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DETAILS = "details";
    private ParseObject json;
    private String KEY_ID;
    
    public Event(String parseTableName){
        json = new ParseObject(parseTableName);
        KEY_ID = json.getObjectId();
      
    }
    public Event(ParseObject obj ){
    	 json = new ParseObject(obj.getClassName());
    	 setName(obj.get(KEY_NAME).toString());
    	 setDate(obj.get(KEY_DATE).toString());
    	 setLocation(obj.get(KEY_LOCATION).toString());
    	 setPhoneNumber(obj.get(KEY_PHONE_NUMBER).toString());
    	 setDetails(obj.get(KEY_DETAILS).toString());
    	
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Missing Event Name");
        json.put(KEY_NAME, name);
    }

    public void setDate(String date){
//        if (date == null)
//            throw new IllegalArgumentException("Param may not be null.");
        json.put(KEY_DATE, date);
    }

    public void setPhoneNumber(String phoneNumber) {
//        if (phoneNumber == null)
//            throw new IllegalArgumentException("Param may not be null.");
        json.put(KEY_PHONE_NUMBER, phoneNumber);
    }

    public void setLocation(String location)  throws IllegalArgumentException{
        if (location == null || location.equals(""))
            throw new IllegalArgumentException("Missing Location");
        json.put(KEY_LOCATION, location);
    }

    public void setDetails(String details){
//        if (details == null)
//            throw new IllegalArgumentException("Param may not be null.");
        json.put(KEY_DETAILS, details);
    }

    public String getName(){
        return json.getString(KEY_NAME);
    }

    public String getDate(){
        return json.getString(KEY_DATE);
    }

    public String getPhoneNumber(){
        return json.getString(KEY_PHONE_NUMBER);
    }

    public String getLocation(){
        return json.getString(KEY_LOCATION);
    }

    public String getDetails(){
        return json.getString(KEY_DETAILS);
    }

    public void send() {
        json.saveInBackground();
    }
    
    public String getID(){
    	return KEY_ID;
    }
    
    public String getParseObjectID(){
    	return KEY_ID;
    }
    
    public static void delete(String table, String id){
    	ParseObject toDelete = new ParseObject(table);
    	toDelete.remove(id);
    	toDelete.saveInBackground();
    }
}



*/