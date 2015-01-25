package com.example.bytepad;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPrefenceActivity extends Activity{
public static final String PREFS_NAME="MyPrefence";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharedpref);
final TextView tv=(TextView)findViewById(R.id.lt);
		final EditText et1=(EditText)findViewById(R.id.pol);
Button btn=(Button) findViewById(R.id.Click);
btn.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	SharedPreferences settings=getSharedPreferences("Ankit", 0);	
	SharedPreferences.Editor editor=settings.edit();
	String x=settings.getString("value","No value Inserted");
	tv.setText("The Value Is "+x);
	
	editor.putString("value",et1.getText().toString());
	editor.commit();
	}
});

	}

}
