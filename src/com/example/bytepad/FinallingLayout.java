package com.example.bytepad;
import java.util.ArrayList;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
public class FinallingLayout extends ActionBarActivity implements OnClickListener{
LinearLayout ll;
ListView list;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
Log.d("Welcome","Welcome");
setContentView(R.layout.finallylayout);
ll=(LinearLayout)findViewById(R.id.inside);
Button btn=(Button)findViewById(R.id.btn1);
btn.setOnClickListener(this);
}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.main, menu);
return true;
}
@Override
public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
int id = item.getItemId();
if (id == R.id.action_settings) {
return true;
}
return super.onOptionsItemSelected(item);
}
@Override
public void onClick(View v) {
final ImageView image=(ImageView)findViewById(R.id.img);
Animation animation=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.trans);
ll.startAnimation(animation);
// overridePendingTransition(R.anim.trans, R.anim.trans);
animation.setAnimationListener(new AnimationListener() {
@Override
public void onAnimationEnd(Animation arg0) {
list.setVisibility(0);
/*
*/
//image.setVisibility(View.GONE);
Log.d("Animation ends","Reached end of anim");
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
list=(ListView)findViewById(android.R.id.list);
String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
"Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
"OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
"Android", "iPhone", "WindowsMobile" };
final ArrayList<String> list1 = new ArrayList<String>();
for (int i = 0; i < values.length; ++i) {
list1.add(values[i]);
}
final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
android.R.layout.simple_list_item_1, list1);
list.setAdapter(adapter);
}
}