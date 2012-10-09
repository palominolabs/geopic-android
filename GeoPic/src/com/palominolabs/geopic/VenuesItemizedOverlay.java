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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;

public class VenuesItemizedOverlay extends ItemizedOverlay<VenueOverlayItem> {
	private static final int TOUCH_PADDING_PX = 10;
	private static final int TOOLTIP_TEXT_SIZE_PX = 25;
	private static final int TOOLTIP_RADIUS_PX = 10;
	private static final int TOOLTIP_BORDER_WIDTH_PX = 1;
	private static final int TOOLTIP_BACKGROUND_COLOR = Color.WHITE;
	private static final int TOOLTIP_TEXT_COLOR = Color.DKGRAY;

	private final Paint tooltipPaint;

	private List<Venue> venues;

	private Venue venueShowingTooltip;

	private OnVenueTooltipClickListener onVenueTooltipClickListener;

	public VenuesItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));

		tooltipPaint = new Paint();
		tooltipPaint.setTextSize(TOOLTIP_TEXT_SIZE_PX);
		tooltipPaint.setAntiAlias(true);
		tooltipPaint.setStyle(Style.FILL_AND_STROKE);
		tooltipPaint.setStrokeWidth(TOOLTIP_BORDER_WIDTH_PX);
		tooltipPaint.setTextAlign(Align.CENTER);

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
	public synchronized boolean onTap(GeoPoint p, MapView mapView) {
		if (onVenueTooltipClickListener != null && venueShowingTooltip != null) {
			Rect tooltipBounds = getTooltipTextBounds(mapView);
			tooltipBounds.inset(-(TOUCH_PADDING_PX + TOOLTIP_RADIUS_PX),
					-(TOUCH_PADDING_PX + TOOLTIP_RADIUS_PX));

			Point tapScreenPosition = new Point();
			mapView.getProjection().toPixels(p, tapScreenPosition);

			if (tooltipBounds
					.contains(tapScreenPosition.x, tapScreenPosition.y)) {
				onVenueTooltipClickListener
						.onVenueTooltipClicked(venueShowingTooltip);
				return true;
			}
		}

		return super.onTap(p, mapView);
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

		Rect textBounds = getTooltipTextBounds(mapView);

		RectF backgroundBounds = new RectF(textBounds);
		backgroundBounds.inset(-TOOLTIP_RADIUS_PX, -TOOLTIP_RADIUS_PX);

		tooltipPaint.setColor(TOOLTIP_BACKGROUND_COLOR);
		canvas.drawRoundRect(backgroundBounds, TOOLTIP_RADIUS_PX,
				TOOLTIP_RADIUS_PX, tooltipPaint);

		tooltipPaint.setColor(TOOLTIP_TEXT_COLOR);
		tooltipPaint.setStyle(Style.STROKE);
		canvas.drawRoundRect(backgroundBounds, TOOLTIP_RADIUS_PX,
				TOOLTIP_RADIUS_PX, tooltipPaint);

		tooltipPaint.setStyle(Style.FILL);
		canvas.drawText(venueShowingTooltip.getName(), textBounds.centerX(),
				textBounds.centerY() + TOOLTIP_RADIUS_PX, tooltipPaint);
	}

	private Rect getTooltipTextBounds(MapView mapView) {
		if (venueShowingTooltip == null) {
			throw new IllegalStateException(
					"venueShowingTooltip shouldn't be null when this is called");
		}

		Point tooltipScreenPosition = new Point();
		mapView.getProjection().toPixels(
				venueShowingTooltip.getLocation().getGeoPoint(),
				tooltipScreenPosition);

		Rect textBounds = new Rect();
		tooltipPaint.getTextBounds(venueShowingTooltip.getName(), 0,
				venueShowingTooltip.getName().length(), textBounds);
		textBounds.offset(-textBounds.centerX(), 0);
		textBounds.offset(tooltipScreenPosition.x, tooltipScreenPosition.y);

		return textBounds;
	}

	public synchronized void setOnVenueTooltipClickListener(
			OnVenueTooltipClickListener onVenueTooltipClickListener) {
		this.onVenueTooltipClickListener = onVenueTooltipClickListener;
	}

	public interface OnVenueTooltipClickListener {
		void onVenueTooltipClicked(Venue venue);
	}
}
