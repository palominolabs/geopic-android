package com.palominolabs.geopic;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VenuesAdapter extends BaseAdapter {

	private final int padding;
	private final Context context;
	private List<Venue> venues;

	public VenuesAdapter(Context context) {
		this.context = context;
		this.venues = Collections.emptyList();
		
		padding = context.getResources().getDimensionPixelSize(R.dimen.venues_list_padding);
	}

	public int getCount() {
		return venues.size();
	}

	public Venue getItem(int position) {
		return venues.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) convertView;
		if (view == null) {
			view = new TextView(context);
			view.setTextAppearance(context, android.R.style.TextAppearance_Large);
			view.setPadding(padding, padding, padding, padding);
		}

		Venue venue = venues.get(position);
		view.setText(venue.getName());

		return view;
	}

	public void setVenues(List<Venue> venues) {
		this.venues = venues;
		notifyDataSetChanged();
	}
}
