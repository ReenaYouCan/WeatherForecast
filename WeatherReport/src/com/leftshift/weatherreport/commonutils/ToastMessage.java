package com.leftshift.weatherreport.commonutils;

import android.content.Context;
import android.widget.Toast;

public class ToastMessage {

	Context mContext;

	public ToastMessage(Context c) {
		mContext = c;
	}

	public void showToast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	public void showToast(int msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

}
