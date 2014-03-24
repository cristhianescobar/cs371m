package com.whosupnext;

import com.parse.ParseObject;

/**
 * Created by escobarcristhian18 on 3/21/14.
 */

//@ParseClassName("Event")
public class Json {

    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_PHONE_NUMBER = "phone";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DETAILS = "details";
    private ParseObject json;

    public Json(String parseTableName){
        json = new ParseObject(parseTableName);
    }

    public void setName(String name) {
//        if (name == null)
//            throw new IllegalArgumentException("Param may not be null.");
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

    public void setLocation(String location){
//        if (location == null)
//            throw new IllegalArgumentException("Param may not be null.");
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



