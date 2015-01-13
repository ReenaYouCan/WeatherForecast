package com.leftshift.weatherreport;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.leftshift.weatherreport.commonutils.CommonMethods;
import com.leftshift.weatherreport.commonutils.ConnectionDetector;
import com.leftshift.weatherreport.commonutils.JsonRequestParser;
import com.leftshift.weatherreport.commonutils.LocationAddress;
import com.leftshift.weatherreport.commonutils.ToastMessage;
import com.leftshift.weatherreport.pojo.CityWeatherForecast;
import com.leftshift.weatherreport.pojo.SingleWeatherForecast;
import com.leftshift.weatherreport.volley.AppController;

public class WeatherForecastSpecificCity extends ActionBarActivity implements
		CommonMethods, LocationListener {
	Context mContext;
	// Classes
	ToastMessage mToastMessage;
	JsonRequestParser mJsonRequestParser;
	ConnectionDetector mConnectionDetector;
	LocationAddress mLocationAddress;

	// Views. Variables, Collections
	String strCityName;
	ListView lstCityForecast;
	TextView tvCityName, tvDegree;

	// JsonParsing
	String tag_json_obj = "json_obj_req";
	public static String APPID = "f9dc7b7bb6733bf2baac339fc0a9e18f";
	String url, TAG = "WeatherForecastSpecificCity";
	JSONObject jObjCity;
	JSONArray jArrjList, jArrWeather;
	int errorCode;
	String strCounrty, strCity, strMain, strDescription;
	int deg, clouds, humidity, pressure, speed;
	ArrayList<CityWeatherForecast> arrCityWeather;
	ArrayList<SingleWeatherForecast> arrSingleWeather;

	// GetCurrent City Using GPS
	LocationManager mLocationManager;
	Location mLocation;
	Double currentLat, currentLong;
	boolean isGpsEnable;
	String provider, currentCity, strFrom;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_specificcity);
		setClasses();
		declareView();
		setLocation();
		getDataFromIntent();

	}

	@Override
	public void setClasses() {
		mToastMessage = new ToastMessage(mContext);
		mJsonRequestParser = new JsonRequestParser(strCityName, mContext);
		mConnectionDetector = new ConnectionDetector(mContext);
		mLocationAddress = new LocationAddress(mContext);

	}

	@Override
	public void declareView() {
		lstCityForecast = (ListView) findViewById(R.id.lstCityForecast);
		tvCityName = (TextView) findViewById(R.id.tvCityName);
		tvDegree = (TextView) findViewById(R.id.tvDegree);
	}

	// Receive City Name from previous Activity using intent
	public void getDataFromIntent() {
		if(!mConnectionDetector.isConnectingToInternet())
		{
			mToastMessage.showToast("Please check internet connection!");
		}
			else
				{try {
			strFrom = getIntent().getExtras().getString("from");
		} catch (NullPointerException e) {
			// TODO: handle exception
		}

		if (strFrom == null) {
			new GetCurrentLocationAsyntask().execute();
		} else {
			strCityName = getIntent().getExtras().getString("city");
			getWeatherInfo();
		}
				}
	}

	// Create Json Object Request using volley and get back response
	public void getWeatherInfo() {
		url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="
				+ strCityName + "&cnt=14&APPID=" + APPID;
		final ProgressDialog pDialog = new ProgressDialog(mContext);
		pDialog.setMessage("Loading...");
		pDialog.show();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG, response.toString());
						arrCityWeather = mJsonRequestParser
								.getWeatherInfoFromJson(response);
						try {
							tvCityName.setText(arrCityWeather.get(0)
									.getCityName()
									+ ","
									+ arrCityWeather.get(0).getCountryName());
							tvDegree.setText(arrCityWeather.get(0)
									.getWeatherInfo().get(0).getDegree()
									+ "C");
							tvDegree.setText(Html.fromHtml("<B>"
									+ arrCityWeather.get(0).getWeatherInfo()
											.get(0).getDegree() + "</B>"
									+ "<sup><small>" + "&nbsp;0"
									+ "</small></sup>F"));
							lstCityForecast.setAdapter(new WeatherAdapter(
									mContext, arrCityWeather));
						} catch (IndexOutOfBoundsException e) {
							// TODO: handle exception

						}
						pDialog.hide();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						// hide the progress dialog
						pDialog.hide();
					}
				});

		AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
	}

	// Current Location Stuff

	public void setLocation() {
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = mLocationManager.getBestProvider(criteria, false);
		Location location = mLocationManager.getLastKnownLocation(provider);
		// Initialize the location fields
		if (location != null) {
			System.out.println("Provider " + provider + " has been selected.");
			onLocationChanged(location);
		} else {
			mToastMessage.showToast("Location not available");
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		currentLat = location.getLatitude();
		currentLong = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLocationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mLocationManager.removeUpdates(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mLocationManager.removeUpdates(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(mContext, MainActivity.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Get Address in backgound
	public class GetCurrentLocationAsyntask extends
			AsyncTask<Void, Boolean, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(mContext);
			pDialog.setCancelable(false);
			pDialog.setTitle("Please wait getting your current location");
			pDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			currentCity = mLocationAddress.getAddressFromLatLong(currentLat,
					currentLong);
			return currentCity;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			strCityName = result;
			getWeatherInfo();
		}

	}
}
