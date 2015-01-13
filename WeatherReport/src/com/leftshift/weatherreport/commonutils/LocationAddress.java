package com.leftshift.weatherreport.commonutils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

public class LocationAddress {

	Context mContext;
	String TAG = "LocationAddress";
	ToastMessage mToastMessage;

	public LocationAddress(Context _con) {
		mContext = _con;
	}

	public String getAddressFromLatLong(Double latitude, Double longitude) {
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		String result = null;
		try {
			List<Address> addressList = geocoder.getFromLocation(latitude,
					longitude, 1);
			if (addressList != null && addressList.size() > 0) {
				Address address = addressList.get(0);
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
					sb.append(address.getAddressLine(i)).append("\n");
				}
				sb.append(address.getLocality()).append("\n");
				sb.append(address.getPostalCode()).append("\n");
				sb.append(address.getCountryName());
				result = address.getLocality();
				// System.out.print("Address:" + result);

			}
		} catch (IOException e) {
			Log.e(TAG, "Unable connect to Geocoder", e);
			Toast.makeText(mContext, "Unable connect to Geocoder",
					Toast.LENGTH_SHORT).show();

		}
		return result;
	}
}
