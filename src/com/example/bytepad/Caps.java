package com.example.bytepad;

import java.io.IOException;

public class Caps {
	public String getCaps(String info) {

		char c = info.charAt(0);
		String w = c + "";
		info = info.toLowerCase();
		for (int i = 1; i < info.length(); i++) {
			if (info.charAt(i) != ' ')
				w = w + info.charAt(i);
			else {
				if(info.charAt(i+1)>96&&info.charAt(i+1)<123)
				w = w + " " + (char) (info.charAt(i + 1) - 32);
				else
					w = w +" "+ info.charAt(i+1);
				i++;
			}
		}

		return w;
	}
}
