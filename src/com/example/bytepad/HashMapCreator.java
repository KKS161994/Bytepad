package com.example.bytepad;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HashMapCreator {
	public static void jsonToMap(String t) throws JSONException {

        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jObject.getString(key); 
            map.put(key, value);

        }
Log.d("HashMaps ",""+map);
        System.out.println("json : "+jObject);
        System.out.println("map : "+map);
    }
}
