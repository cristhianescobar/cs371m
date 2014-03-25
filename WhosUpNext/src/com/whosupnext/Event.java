package com.whosupnext;

import com.parse.ParseObject;

/**
 * Created by escobarcristhian18 on 3/21/14.
 */

//@ParseClassName("Event")
public class Event {

    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_PHONE_NUMBER = "phone";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DETAILS = "details";
    private ParseObject json;

    public Event(String parseTableName){
        json = new ParseObject(parseTableName);
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
}



