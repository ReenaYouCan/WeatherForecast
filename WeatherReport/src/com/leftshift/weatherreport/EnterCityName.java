package com.leftshift.weatherreport;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.leftshift.weatherreport.commonutils.CommonMethods;
import com.leftshift.weatherreport.commonutils.ConnectionDetector;
import com.leftshift.weatherreport.commonutils.JsonRequestParser;
import com.leftshift.weatherreport.commonutils.ToastMessage;
import com.leftshift.weatherreport.pojo.CityName;

public class EnterCityName extends Fragment implements CommonMethods {

	// Classes
	ToastMessage mToastMessage;
	ConnectionDetector mConnectionDetector;
	JsonRequestParser mJsonRequestParser;
	// Views
	EditText edtSearch;
	View rootView;
	ListView lstCityName;
	CityAdapter mCityAdapter;
	CityName mCityName;
	String strCityNames, strSingleCityName;
	String[] strSetOfCity;
	int num;

	public EnterCityName() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_main, container, false);
		setClasses();
		declareView();
		selectCityForDetails();
		return rootView;
	}

	public void setClasses() {
		mToastMessage = new ToastMessage(getActivity());
		mConnectionDetector = new ConnectionDetector(getActivity());
	}

	public void declareView() {
		lstCityName = (ListView) rootView.findViewById(R.id.lstCityName);
		lstCityName.setEmptyView(rootView.findViewById(android.R.id.empty));
		edtSearch = (EditText) rootView.findViewById(R.id.edtSearch);
		edtSearch.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int DRAWABLE_RIGHT = 2;

				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (event.getRawX() >= (edtSearch.getRight() - edtSearch
							.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
							.width())) {

						if (arrCityName().isEmpty()) {
							mToastMessage.showToast("Please enter city name");
						} else {

							mCityAdapter = new CityAdapter(getActivity(),
									arrCityName());
							lstCityName.setAdapter(mCityAdapter);
						}
					}
				}
				return false;
			}
		});
	}

	public void selectCityForDetails() {
		lstCityName.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				strCityNames = arrCityName().get(position).getCityName();
				// mJsonRequestParser = new JsonRequestParser(strCityNames,
				// getActivity());
				// mJsonRequestParser.getWeatherInfo();
				if (mConnectionDetector.isConnectingToInternet()) {
					Intent cityIntent = new Intent(getActivity(),
							WeatherForecastSpecificCity.class);
					cityIntent.putExtra("city", strCityNames);
					cityIntent.putExtra("from", "search");
					startActivity(cityIntent);
				} else {
					mToastMessage
							.showToast("Please check your internet connection");
				}
				// mToastMessage.showToast("Position:" + position);
			}
		});
	}

	// Method returns city name entered in edittext
	public ArrayList<CityName> arrCityName() {
		ArrayList<CityName> arr = new ArrayList<CityName>();
		strCityNames = edtSearch.getText().toString();
		if (strCityNames.matches("")) {
			mToastMessage.showToast("Please Enter City Name");
		} else {
			strSetOfCity = strCityNames.split(",");
			for (int i = 0; i < strSetOfCity.length; i++) {
				mCityName = new CityName();
				strSingleCityName = strSetOfCity[i];
				mCityName.setCityNumber(i + 1);
				if (strSingleCityName.matches("")) {
				} else {
					mCityName.setCityName(strSingleCityName);
					arr.add(mCityName);
				}
			}
		}
		return arr;
	}

}
