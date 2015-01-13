package com.leftshift.weatherreport;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.leftshift.weatherreport.pojo.CityName;

public class CityAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<CityName> arrCityList;
	LayoutInflater mLayoutInflater;

	public CityAdapter(Context mCon, ArrayList<CityName> arrCity) {
		mContext = mCon;
		arrCityList = arrCity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrCityList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrCityList.get(position);
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
			view = mLayoutInflater.inflate(R.layout.listview_main, null);
			holder = new ViewHolder();
			holder.btnDetails = (Button) view.findViewById(R.id.btnDetails);
			holder.tvCityName = (TextView) view.findViewById(R.id.tvCityName);
			holder.tvNumber = (TextView) view.findViewById(R.id.tvNumber);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.tvCityName.setText(arrCityList.get(position).getCityName());
		holder.tvNumber.setText("" + arrCityList.get(position).getCityNumber());
		holder.btnDetails.setVisibility(View.GONE);
		// holder.btnDetails.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(mContext, "Weather Information",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		return view;
	}

	private class ViewHolder {
		Button btnDetails;
		TextView tvCityName, tvNumber;
	}
}
