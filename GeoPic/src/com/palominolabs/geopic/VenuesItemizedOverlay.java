package com.palominolabs.geopic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;

public class VenuesItemizedOverlay extends ItemizedOverlay<VenueOverlayItem> {
	private static final int TOOLTIP_TEXT_SIZE_PX = 25;
	private static final int TOOLTIP_RADIUS_PX = 10;
	private static final int TOOLTIP_BORDER_WIDTH_PX = 1;
	private static final int TOOLTIP_BACKGROUND_COLOR = Color.WHITE;
	private static final int TOOLTIP_TEXT_COLOR = Color.DKGRAY;

	private List<Venue> venues;

	private Venue venueShowingTooltip;

	public VenuesItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

		this.venues = Collections.emptyList();
		setVenues(new LinkedList<Venue>());
	}

	@Override
	protected synchronized VenueOverlayItem createItem(int i) {
		return new VenueOverlayItem(venues.get(i));
	}

	@Override
	public synchronized int size() {
		return venues.size();
	}

	public synchronized void setVenues(List<Venue> venues) {
		this.venues = venues;
		this.venueShowingTooltip = null;

		populate();
		setLastFocusedIndex(-1);
	}

	@Override
	protected synchronized boolean onTap(int index) {
		venueShowingTooltip = venues.get(index);
		return true;
	}

	@Override
	public synchronized void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);

		if (shadow) {
			return;
		}

		if (venueShowingTooltip == null) {
			return;
		}

		Point tooltipScreenPosition = new Point();
		mapView.getProjection().toPixels(
				venueShowingTooltip.getLocation().getGeoPoint(),
				tooltipScreenPosition);

		Paint paint = new Paint();
		paint.setTextSize(TOOLTIP_TEXT_SIZE_PX);
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL_AND_STROKE);
		paint.setStrokeWidth(TOOLTIP_BORDER_WIDTH_PX);
		paint.setTextAlign(Align.CENTER);

		Rect textBounds = new Rect();
		paint.getTextBounds(venueShowingTooltip.getName(), 0,
				venueShowingTooltip.getName().length(), textBounds);
		textBounds.offset(-textBounds.centerX(), 0);
		textBounds.offset(tooltipScreenPosition.x, tooltipScreenPosition.y);

		RectF backgroundBounds = new RectF(textBounds);
		backgroundBounds.inset(-TOOLTIP_RADIUS_PX, -TOOLTIP_RADIUS_PX);

		paint.setColor(TOOLTIP_BACKGROUND_COLOR);
		canvas.drawRoundRect(backgroundBounds, TOOLTIP_RADIUS_PX,
				TOOLTIP_RADIUS_PX, paint);

		paint.setColor(TOOLTIP_TEXT_COLOR);
		paint.setStyle(Style.STROKE);
		canvas.drawRoundRect(backgroundBounds, TOOLTIP_RADIUS_PX,
				TOOLTIP_RADIUS_PX, paint);
		
		paint.setStyle(Style.FILL);
		canvas.drawText(venueShowingTooltip.getName(), textBounds.centerX(), textBounds.centerY() + TOOLTIP_RADIUS_PX, paint);
	}
}
