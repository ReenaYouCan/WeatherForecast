package com.leftshift.weatherreport.pojo;

public class SingleWeatherForecast {

	int _deg, _clouds, _humidity;
	float _pressure, _speed;

	String _main, _description;

	int _daycount;

	public int getDayCount() {
		return _daycount;
	}

	public void setDayCount(int count) {
		_daycount = count;
	}

	public int getDegree() {
		return _deg;
	}

	public void setDegree(int dgr) {
		_deg = dgr;
	}

	public int getClouds() {
		return _clouds;
	}

	public void setClouds(int cld) {
		_clouds = cld;
	}

	public int getHumidity() {
		return _clouds;
	}

	public void setHumidity(int hmdt) {
		_humidity = hmdt;
	}

	public float getPressure() {
		return _pressure;
	}

	public void setPressure(float prsr) {
		_pressure = prsr;
	}

	public float getSpeed() {
		return _speed;
	}

	public void setSpeed(float spd) {
		_speed = spd;
	}

	public String getMain() {
		return _main;
	}

	public void setMain(String mn) {
		_main = mn;
	}

	public String getDesciption() {
		return _description;
	}

	public void setDesciption(String dec) {
		_description = dec;
	}

}