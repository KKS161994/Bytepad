package com.example.bytepad;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Connectivity {
	public boolean isConnected(ConnectivityManager conn) {
		Log.e("Log log", "Connectivity()");
		NetworkInfo wifi = conn.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo dataPack = conn
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifi != null && dataPack != null) {
			if (wifi.isConnectedOrConnecting() && wifi.isAvailable()) {
				Log.e("Connected", "Is connected");
				return true;
			} else if (dataPack.isConnectedOrConnecting()
					&& dataPack.isAvailable())
				return true;
			else
				return false;
		} else
			return false;
	}
}