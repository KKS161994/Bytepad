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
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	Button search, ttbt;
	FrameLayout fl;
	EditText find;
	int check = 0;// variable for checking whether orientation has changed
	RelativeLayout rl;
	String array[] = new String[2000];
	String paper_url[] = new String[2000];
	String paper_title[] = new String[2000];
	String searchText;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	String st1;
TextView pupna;
	int item_selected_position = 0, j, k = 0, click_status = 0, ifnj = 0;
	JSONObject obj;
	ListView list;
	int screenHeight, screenWidth;
	Connectivity object = new Connectivity();
	Dialogbox dbox = new Dialogbox();
	GetStringFromStream gsfs = new GetStringFromStream();
	LinearLayout ins2;
	RelativeLayout animax, ll, ll2, ins;
	String data,
			url = "http://silive.in/bytepad/rest/api/paper/getallpapers?query=",
			furl, surl;
	ImageView image;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		SharedPreferences settings = getSharedPreferences("Bytepad", 0);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.finallylayout);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenHeight = metrics.heightPixels;
		screenWidth = metrics.widthPixels;

		if ((settings.getInt("click_status", 0) == 1)) {
			click_status = 1;

			initialise();
			list.setVisibility(View.VISIBLE);

			if (screenHeight > screenWidth) {
				check = 1;

				finalcheck();
			} else {
				check = 1;
				finalcheck();

			}
		}
		initialise();
		find.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					try {
						clicked(findViewById(R.id.btn1));
					} catch (HttpRetryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setTitle("Exit")
				.setMessage("Do you really want to exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).setNegativeButton("No", null).show();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.d("Confoo changed", "Config changed");
		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}
	}

	public void initialise() {
		Log.d("Reached", " On Initialise");
		find = (EditText) findViewById(R.id.edit_text);
		ins = (RelativeLayout) findViewById(R.id.inside);
		ins2 = (LinearLayout) findViewById(R.id.inside2);
		search = (Button) findViewById(R.id.btn1);
		list = (ListView) findViewById(android.R.id.list);
		image = (ImageView) findViewById(R.id.image);
		pupna=(TextView)findViewById(R.id.pupna);
		ll = (RelativeLayout) findViewById(R.id.ll);
		ll2 = ins;

		animax = (RelativeLayout) findViewById(R.id.animateLayout);

		if (click_status == 0)
			ins.setY((float) (screenHeight / 2.75));
	}

	public void clicked(View view) throws HttpRetryException,
			InterruptedException {
		// COnnectivity is checked at every on clicked and dialogbox2 was
		// removed

		if (connectivitycheck() == false) {
			Log.d("error", "connectivity");
			ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			dbox.dialogbox3(MainActivity.this, conn);
		} else {

			if (click_status == 0) {
				searchText = find.getText().toString();
				if (searchText.matches("")) {
					Toast.makeText(MainActivity.this, "You left subject name blank",
							Toast.LENGTH_SHORT).show();
							list.setVisibility(View.INVISIBLE);
	pupna.setVisibility(View.VISIBLE);
				}

				else {
					pupna.setVisibility(View.GONE);
					image.setVisibility(View.GONE);
					ins.animate().y(1f);
					finalcheck();
					SharedPreferences settings = getSharedPreferences(
							"Bytepad", 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putInt("click_status", 1);
					editor.commit();
					click_status = 1;
					list.setVisibility(View.VISIBLE);
					// list.setAlpha( (float) 0.7);
				}
			} else {
				pupna.setVisibility(View.GONE);
				finalcheck();
			}
		}
	}

	public void finalcheck() {
		// list.setAlpha( (float) 0.7);
		SharedPreferences settings = getSharedPreferences("Bytepad", 0);
		SharedPreferences.Editor editor = settings.edit();

		if (check == 1) {
			searchText = settings.getString("edit_text", null);
			editor.remove("edit_text");
			check = 0;
		} else {
			searchText = find.getText().toString();

			editor.putString("edit_text", searchText);
			editor.commit();
		}
		String st2 = getFinalUrl(searchText);
		if (searchText.matches("")) {
			list.setVisibility(View.INVISIBLE);
			Toast.makeText(MainActivity.this, "You left subject name blank",
					Toast.LENGTH_SHORT).show();
		} else {
			list.setVisibility(View.VISIBLE);
			image.setVisibility(View.INVISIBLE);
			furl = url + st2;
			Log.d("sear", "" + searchText + " " + surl);
			try {
				j = 0;
				k = 0;
				if (connectivitycheck() == false) {
					Log.d("error", "connectivity");
					ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
					dbox.dialogbox3(MainActivity.this, conn);
				} else
					new getData().execute(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int checkextension(String title) {
		int i = 0, l = title.length();
		String k;
		while (title.charAt(i) != '.')
			i++;
		k = title.substring(i + 1, l);
		if (k.equals("pdf"))
			return 1;
		return 0;
	}

	public boolean connectivitycheck() {
		ConnectivityManager conn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		return (object.isConnected(conn));
	}

	public void startDownload(int pos, Context context) {
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
		String from[] = { "byt", "tv", "siz" };
		int to[] = { R.id.byt, R.id.tv, R.id.siz };

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
				connection.setConnectTimeout(5000);
				Log.d("Log log", "Connected to network");
				in = connection.getInputStream();
				data = getStringFromInputStream(in);
				Log.d("Getting Hash Map", "Loading Hash Maps");

				new HashMapCreator().jsonToMap(data);

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
				dbox.dialogbox3(MainActivity.this, conn);
				ifnj = 0;
			}
			int error_status = 1;
			try {
				JSONArray get = new JSONArray(data);
				int l = get.length();
				while (j < l) {
					obj = get.getJSONObject(j);

					array[j] = obj.getString("Title").toLowerCase();
					String info = obj.getString("Title");

					hm = new HashMap<String, String>();
					if (checkextension(obj.getString("Title")) == 1)
						hm.put("byt", (Integer.toString(R.drawable.pdf)));
					else
						hm.put("byt", (Integer.toString(img)));

					hm.put("siz", obj.getString("Size"));
					hm.put("tv", obj.getString("ExamCategory") + "-"
							+ new Caps().getCaps(info));
					items.add(hm);
					paper_title[k] = obj.getString("Title");
					paper_url[k++] = obj.getString("URL");
					error_status = 0;
					j++;
				}
				if (error_status == 1) {
					Log.d("Reaching error status ",
							"The visibility is " + ins.getTop()
									+ ins.getBottom());
					image.setVisibility(0);
				} else {
					Log.d("Reaching error status ",
							"The visibility is " + ins.getTop()
									+ ins.getBottom());
					image.setVisibility(8);
				}
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
						final int pos, long arg3) {
					// TODO Auto-generated method stub
					Log.d("log log", "You Selected" + pos + "whose URL is "
							+ paper_url[pos]);

					AlertDialog.Builder builder = new AlertDialog.Builder(
							MainActivity.this);
					builder.setTitle("Click to downlaod")
							.setMessage("Do You Want to download")
							.setCancelable(false)

							.setPositiveButton("Not Now",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									});
					builder.setNegativeButton("Download",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
									startDownload(pos, MainActivity.this);

								}
							});
					AlertDialog alert = builder.create();
					alert.show();

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