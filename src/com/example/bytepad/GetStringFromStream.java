package com.example.bytepad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

class GetStringFromStream {
	public String getString(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String fetchedData = "";
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			fetchedData = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();

					return fetchedData;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return fetchedData;
	}
}