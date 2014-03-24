package com.whosupnext;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

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
		LayoutInflater inflater = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listItem = inflater.inflate(R.layout.list_item, parent, false);
		
		TextView name = (TextView) listItem.findViewById(R.id.itemName);
		name.setText(this.getItem(position).getName());
		
		TextView date = (TextView) listItem.findViewById(R.id.itemDate);
		String time = this.getItem(position).getDate();
		date.setText(AddEvent.parseDate(time) + ", " + AddEvent.parseTime(time));
		
		return listItem	;
	}
}
