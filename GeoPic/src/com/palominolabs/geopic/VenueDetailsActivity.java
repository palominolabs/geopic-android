package com.palominolabs.geopic;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.palominolabs.geopic.AuthHelper.Callback;
import com.stackmob.sdk.api.StackMobFile;

public class VenueDetailsActivity extends Activity {

	private static final int TAKE_PICTURE_REQUEST_CODE = 1;

	private Venue venue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_venue_details);

		venue = (Venue) getIntent().getExtras().getSerializable("venue");

		setTitle(venue.getName());

		TextView venueNameView = (TextView) findViewById(R.id.venue_details_nameValue);
		venueNameView.setText(venue.getName());

		TextView categoryView = (TextView) findViewById(R.id.venue_details_categoryValue);
		categoryView.setText(venue.getCategory());

		TextView checkinsView = (TextView) findViewById(R.id.venue_details_checkinsValue);
		checkinsView.setText(String.valueOf(venue.getCheckins()));

		TextView formattedPhoneView = (TextView) findViewById(R.id.venue_details_formattedPhoneValue);
		formattedPhoneView.setText(venue.getFormattedPhone());

		View takePictureButton = (View) findViewById(R.id.venue_details_takePicture);
		takePictureButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				takePicture();
			}
		});

	}

	private void takePicture() {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, TAKE_PICTURE_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TAKE_PICTURE_REQUEST_CODE) {
			handlePictureResult(data);
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void handlePictureResult(Intent data) {
		if (data == null || data.getExtras() == null || !data.getExtras().containsKey("data")) {
			return;
		}
		Bitmap bitmap = (Bitmap) data.getExtras().get("data");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);

		final byte[] pictureBytes = baos.toByteArray();

		AuthHelper.withLoggedInUser(this, new Callback() {

			public void call(String userId) {
				StackMobFile picture = new StackMobFile("image/jpeg",
						"image.jpg", pictureBytes);
				VenuePicture venuePicture = new VenuePicture(venue.getFoursquareId(), venue.getName(), picture);
				venuePicture.save();
			}
		});

	}

}
