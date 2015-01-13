package com.leftshift.weatherreport.commonutils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.leftshift.weatherreport.pojo.CityWeatherForecast;
import com.leftshift.weatherreport.pojo.SingleWeatherForecast;

public class JsonRequestParser {

	String cityname;
	Context mContext;
	String url, TAG = "JsonRequestParser";
	ToastMessage mToastMessage;

	// Json Response

	JSONObject jObjCity;
	JSONArray jArrjList, jArrWeather;
	int errorCode;
	String strCounrty, strCity, strMain, strDescription;
	int deg, clouds, humidity, pressure, speed;
	ArrayList<CityWeatherForecast> arrCityWeather;
	ArrayList<SingleWeatherForecast> arrSingleWeather;

	public JsonRequestParser(String _cityname, Context _con) {
		this.cityname = _cityname;
		this.mContext = _con;
	}

	// Return arraylist with 14 days forecast for entered city
	public ArrayList<CityWeatherForecast> getWeatherInfoFromJson(JSONObject json) {
		mToastMessage = new ToastMessage(mContext);
		arrCityWeather = new ArrayList<CityWeatherForecast>();
		JSONObject jsonRespone = json;
		try {
			errorCode = jsonRespone.getInt("cod");
			if (errorCode == 404) {
				mToastMessage
						.showToast("Please check city name, no record for such City name");
			} else if (errorCode == 200) {
				CityWeatherForecast cwf = new CityWeatherForecast();
				jObjCity = jsonRespone.getJSONObject("city");
				strCounrty = jObjCity.getString("country");
				strCity = jObjCity.getString("name");

				for (int i = 0; i < arrCityWeather.size(); i++)
					Log.d(TAG, strCity);
				cwf.setCityName(strCity);
				cwf.setCountryName(strCounrty);

				arrSingleWeather = new ArrayList<SingleWeatherForecast>();
				// Json Array with name "list"
				jArrjList = jsonRespone.getJSONArray("list");

				for (int i = 0; i < jArrjList.length(); i++) {
					SingleWeatherForecast swf = new SingleWeatherForecast();
					JSONObject jObjDetails = (JSONObject) jArrjList.get(i);
					deg = jObjDetails.getInt("deg");
					clouds = jObjDetails.getInt("clouds");
					humidity = jObjDetails.getInt("humidity");
					pressure = jObjDetails.getInt("pressure");
					speed = jObjDetails.getInt("speed");

					// Weather Array
					jArrWeather = jObjDetails.getJSONArray("weather");
					JSONObject jObjWe = jArrWeather.getJSONObject(0);
					strMain = jObjWe.getString("main");
					strDescription = jObjWe.getString("description");
					System.out.println("strDescription" + strDescription);
					swf.setDayCount(i + 1);
					swf.setClouds(clouds);
					swf.setDegree(deg);
					swf.setHumidity(humidity);
					swf.setPressure(pressure);
					swf.setSpeed(speed);
					swf.setMain(strMain);
					swf.setDesciption(strDescription);
					arrSingleWeather.add(swf);
					cwf.setWheatherInfo(arrSingleWeather);
					arrCityWeather.add(cwf);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			mToastMessage.showToast("No records for such city");
			e.printStackTrace();
		}
		return arrCityWeather;
	}
}
