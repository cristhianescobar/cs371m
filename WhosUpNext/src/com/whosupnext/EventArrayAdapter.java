package com.whosupnext;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

public class EventArrayAdapter extends ArrayAdapter<ParseObject>
{	
	public EventArrayAdapter(Context context)
	{
		super(context, R.layout.list_item);
	}
	
	public void add (ParseObject object)
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
		name.setText(this.getItem(position).get(Json.KEY_NAME).toString());
		
		TextView date = (TextView) listItem.findViewById(R.id.itemDate);
		date.setText(this.getItem(position).get(Json.KEY_DATE).toString());
		
		return listItem	;
	}
}
