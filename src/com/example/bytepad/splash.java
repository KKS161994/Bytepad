package com.example.bytepad;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

public class splash extends Activity {
	MediaPlayer ourSong;

	@Override
	protected void onCreate(Bundle splashbackground) {
		// TODO Auto-generated method stub
		super.onCreate(splashbackground);
		setContentView(R.layout.splash);
		SharedPreferences settings=getSharedPreferences("Bytepad",0);
		SharedPreferences.Editor editor=settings.edit();
		editor.clear();
		editor.commit();
		Log.d("In Splash","Getting "+settings.getInt("click_status", 0));
		Connectivity object=new Connectivity();
		ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
	if(object.isConnected(conn)==false){	
			new Dialogbox().dialogbox2(this,conn);
		}
			Thread timer = new Thread() {
				public void run() {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {

						Intent openCounter = new Intent(
								"com.example.bytepad.MAINACTIVITY");
						startActivity(openCounter);
					}
				}
			};
			timer.start();
		}
		


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		finish();
	}

}