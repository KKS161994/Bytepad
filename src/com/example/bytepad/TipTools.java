package com.example.bytepad;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class TipTools extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.tiptools);
	Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
	Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadein);

	}

}