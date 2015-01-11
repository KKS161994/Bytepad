package com.example.bytepad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

public class Dialogbox {
	@SuppressWarnings("deprecation")
	public void dialogbox1(Context context) {
		
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Not Connected To Internet");
		alertDialog.setMessage("Internet connection is required");
		alertDialog.setIcon(R.drawable.error);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			
			
			}
		});
		alertDialog.show();
	}
	@SuppressWarnings("deprecation")
	public void dialogbox2(final Context context,final ConnectivityManager conn) {
		
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle("Not Connected To Internet");
		
		alertDialog.setMessage("Internet connection is required");
		alertDialog.setIcon(R.drawable.error);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			if(new Connectivity().isConnected(conn)==false)
			new Dialogbox().dialogbox2(context, conn);
			}
		});
		alertDialog.show();
	}
	}
