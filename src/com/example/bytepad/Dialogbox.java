package com.example.bytepad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

public class Dialogbox {
public void dialogbox3(final Context context, final ConnectivityManager conn) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Not Connected")
        .setMessage("Internet Connection is required")
        .setCancelable(false)
        .setIcon(R.drawable.error)
        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

	}

