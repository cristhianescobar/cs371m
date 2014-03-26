package com.whosupnext;

import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class EventArrayAdapter extends ArrayAdapter<Event>
{	
	public EventArrayAdapter(Context context)
	{
		super(context, R.layout.list_item);
	}
	
	public void add (Event object)
	{
		super.add(object);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.list_item, parent, false);
		
		Event e = this.getItem(position);
		
		TextView name = (TextView) v.findViewById(R.id.itemName);
		name.setText(e.getName());
		
		Date date = e.getDate();
		TextView time = (TextView) v.findViewById(R.id.itemDate);
		time.setText(AddEvent.parseDate(date) + ", " + AddEvent.parseTime(date));
		
		return v	;
	}
}
