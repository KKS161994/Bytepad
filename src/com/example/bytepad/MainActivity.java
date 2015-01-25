package com.example.bytepad;

import java.io.InputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	Button search;
	EditText find;
	RelativeLayout rl;
	String array[] = new String[2000];
	String paper_url[] = new String[2000];
	String paper_title[] = new String[2000];
	String searchText;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	String st1;
	int item_selected_position = 0, j, k = 0, click_status = 0, ifnj = 0;
	JSONObject obj;
	ListView list;
	Connectivity object = new Connectivity();
	Dialogbox dbox = new Dialogbox();
	GetStringFromStream gsfs = new GetStringFromStream();
	LinearLayout ins2;
	RelativeLayout ll, ins;
	String data,
			url = "http://silive.in/bytepad/rest/api/paper/getallpapers?query=",
			furl, surl;
	ImageView image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.finallylayout);
		initialise();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	public void initialise() {
		Log.d("Reached", " On Initialise");
		// ins = (LinearLayout) findViewById(R.id.inside);
		find = (EditText) findViewById(R.id.edit_text);
		ins = (RelativeLayout) findViewById(R.id.inside);
		ins2 = (LinearLayout) findViewById(R.id.inside2);
		search = (Button) findViewById(R.id.btn1);
		list = (ListView) findViewById(android.R.id.list);
		image = (ImageView) findViewById(R.id.image);
		ll = (RelativeLayout) findViewById(R.id.ll);
		if (click_status == 0) {
			ins.setY(500);
		}
		Log.d("Animation first ends", "Reached end of anim" + ins.getTop());

	}

	public void clicked(View view) throws HttpRetryException,
			InterruptedException {

		if (click_status == 0) {
			final Animation animation = AnimationUtils.loadAnimation(
					getApplicationContext(), R.anim.trans);
			ins.startAnimation(animation);

			animation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
				    finalcheck();
					list.setVisibility(View.VISIBLE);
				
					click_status = 1;
					Log.d("Reached","Reache before view group");
					
				/*	ViewGroup parent = (ViewGroup) ins.getParent();
		            parent.removeView(ins);
		        */    Log.d("Reached","Reache after view group");
					
		        }

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub

				}

			});

		} 
	}

	public void finalcheck() {
		// ins2.setVisibility(View.VISIBLE);

		searchText = find.getText().toString();
		String st2 = getFinalUrl(searchText);
		furl = url + st2;
		Log.d("sear", "" + searchText + " " + surl);
		try {
			j = 0;
			k = 0;
			if (connectivitycheck() == false) {
				Log.d("error", "connectivity");
				ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				dbox.dialogbox2(MainActivity.this, conn);
			} else
				new getData().execute(url);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("Log log", "Could not create object" + e.toString());
		}

	}

	public boolean connectivitycheck() {
		ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		return (object.isConnected(conn));
	}

	public void startDownload(int pos) {
		String s = getFinalUrl(paper_url[pos]);
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(s));
		Toast.makeText(this, "Download Started", Toast.LENGTH_LONG).show();
		request.setTitle("Downloading");
		request.setDescription("File is being downloaded");
		Log.d("Downloading", "File is being downloaded");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
		request.setDestinationInExternalPublicDir(
				Environment.DIRECTORY_DOWNLOADS, paper_title[pos]);
		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);
		Log.d("Finished", "Download Finished");
		Toast.makeText(this, "Download Finished", Toast.LENGTH_SHORT).show();
	}

	public String getFinalUrl(String s) {
		String q = "";
		int i = 0;
		while (i < s.length()) {
			if (s.charAt(i) == ' ')
				q += "%20";
			else
				q += s.charAt(i);
			i++;
		}

		return q;
	}

	@SuppressLint("DefaultLocale")
	public class getData extends AsyncTask<String, Integer, String> {
		List<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
		SimpleAdapter itemsView;
		int img = R.drawable.word;
		String from[] = { "byt", "tv" };
		int to[] = { R.id.byt, R.id.tv };

		@Override
		protected String doInBackground(String... params) {
			InputStream in = null;
			try {
				URL httpUrl;
				Log.d("urlll", "furl     " + furl);
				httpUrl = new URL(furl);
				furl = null;
				HttpURLConnection connection = (HttpURLConnection) httpUrl
						.openConnection();
				connection.connect();
				Log.d("Log log", "Connected to network");
				in = connection.getInputStream();
				data = getStringFromInputStream(in);
				if (data != null)
					Log.d("INFO_TAG", "Received data" + data);
				try {
					JSONArray get = new JSONArray(data);
				} catch (Exception e) {

					ifnj = 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return data;
		}

		@Override
		protected void onPostExecute(String data) {
			// TODO Auto-generated method stub
			HashMap<String, String> hm = null;
			if (ifnj == 1) {
				ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				dbox.dialogbox2(MainActivity.this, conn);
				ifnj = 0;
			}
			int error_status = 1;
			try {
				JSONArray get = new JSONArray(data);
				int l = get.length();
				while (j < l) {
					obj = get.getJSONObject(j);
					array[j] = obj.getString("Title").toLowerCase();
					String f = find.getText().toString().toLowerCase();
					String info = obj.getString("Title") + "\n"
							+ obj.getString("Size");
					hm = new HashMap<String, String>();
					hm.put("byt", (Integer.toString(img)));
					hm.put("tv", info);
					items.add(hm);
					paper_title[k] = obj.getString("Title");
					paper_url[k++] = obj.getString("URL");
					error_status = 0;
					j++;
				}
				if (error_status == 1)
					image.setVisibility(0);
				else
					image.setVisibility(4);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			itemsView = new SimpleAdapter(getBaseContext(), items,
					R.layout.disp, from, to);
			list.setAdapter(itemsView);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int pos, long arg3) {
					// TODO Auto-generated method stub
					Log.d("log log", "You Selected" + pos + "whose URL is "
							+ paper_url[pos]);
					startDownload(pos);
				}
			});
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub

			super.onProgressUpdate(values);
		}

		public String getStringFromInputStream(InputStream is) {
			Log.e("Reached", "Reached Stringfromstream");
			return gsfs.getString(is);
		}
	}
}