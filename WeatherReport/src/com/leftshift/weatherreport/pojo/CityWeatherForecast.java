package com.leftshift.weatherreport.pojo;

import java.util.ArrayList;

public class CityWeatherForecast {

	ArrayList<SingleWeatherForecast> _arrSingleWeather;
	String _cityname, _countryname;
	
	public String getCityName() {
		return _cityname;
	}

	public void setCityName(String city) {
		_cityname = city;
	}

	public String getCountryName() {
		return _countryname;
	}

	public void setCountryName(String country) {
		_countryname = country;
	}

	public ArrayList<SingleWeatherForecast> getWeatherInfo() {
		return _arrSingleWeather;
	}

	public void setWheatherInfo(
			ArrayList<SingleWeatherForecast> arrSingleWeather) {
		_arrSingleWeather = arrSingleWeather;
	}
}
