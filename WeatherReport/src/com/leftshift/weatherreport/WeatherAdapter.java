package com.leftshift.weatherreport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leftshift.weatherreport.pojo.CityWeatherForecast;

public class WeatherAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<CityWeatherForecast> arrCityWeather;
	LayoutInflater mLayoutInflater;

	public WeatherAdapter(Context _con, ArrayList<CityWeatherForecast> arrCity) {
		this.mContext = _con;
		this.arrCityWeather = arrCity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrCityWeather.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrCityWeather.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ViewHolder holder;
		if (convertView == null) {
			mLayoutInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mLayoutInflater.inflate(R.layout.listview_forecarst, null);
			holder = new ViewHolder();
			holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
			holder.tvDegree = (TextView) view.findViewById(R.id.tvDegree);
			holder.tvDescription = (TextView) view
					.findViewById(R.id.tvDescription);
			holder.tvSpeed = (TextView) view.findViewById(R.id.tvSpeed);
			holder.tvClouds = (TextView) view.findViewById(R.id.tvCloud);
			holder.tvPressure = (TextView) view.findViewById(R.id.tvPressure);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.tvClouds.setText("Clouds:"
				+ arrCityWeather.get(position).getWeatherInfo().get(position)
						.getClouds() + "%");

		holder.tvDegree.setText(Html.fromHtml("<B>"
				+ arrCityWeather.get(position).getWeatherInfo().get(position)
						.getDegree() + "</B>" + "<sup><small>" + "&nbsp;0"
				+ "</small></sup>F"));

		holder.tvDescription.setText(arrCityWeather.get(position)
				.getWeatherInfo().get(position).getDesciption());
		holder.tvPressure.setText("Pressure:"
				+ arrCityWeather.get(position).getWeatherInfo().get(position)
						.getPressure() + "hpa");
		holder.tvSpeed.setText(""
				+ arrCityWeather.get(position).getWeatherInfo().get(position)
						.getSpeed() + "m/s");

		holder.tvDate.setText("Day :"
				+ arrCityWeather.get(position).getWeatherInfo().get(position)
						.getDayCount());

		return view;
	}

	private class ViewHolder {
		TextView tvDate, tvDegree, tvDescription, tvSpeed, tvClouds,
				tvPressure;
	}

	public Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}
}
