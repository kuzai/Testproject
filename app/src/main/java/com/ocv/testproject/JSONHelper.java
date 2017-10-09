package com.ocv.testproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by walke on 10/3/2017.
 */

public class JSONHelper {

    public JSONHelper() {

    }

    public String jsonParser(JSONObject o, String key) {
        String res;
        try{
            res = o.getString(key);
        } catch (JSONException e) {
            //Log.e("JSON Error: ", "The given key: "+key + " could not be found");
            return null;
        }
        return res;
    }

    public JSONArray jsonSubarray(JSONObject o, String key) {
        JSONArray array;
        try {
            array = o.getJSONArray(key);
        } catch (JSONException e) {
            //Log.e("Array Key: ", "Array key wasn't found or something");
            return null;
        }
        return array;
    }

    public String getJSONImages(JSONObject o, String key, String imageKey) {
        JSONArray images = jsonSubarray(o, key);
        try {
            return jsonParser(images.getJSONObject(0), imageKey);
        } catch (JSONException e) {
            return null;
        }
    }


    public ArrayList<String> getAllImages(JSONObject o, String key) {
        JSONArray images = jsonSubarray(o, key);
        ArrayList<String> urls = new ArrayList<>();
        try {
            String res;
            if ((res = jsonParser(images.getJSONObject(0), "small")) != null) {
                urls.add(res);
            }
            if ((res = jsonParser(images.getJSONObject(0), "large")) != null) {
                urls.add(res);
            }
        } catch (JSONException e) {
            //Log.e("JSON Error", "Unknown index");
            return null;
        }
        return urls;
    }


    public long jsonDateParser(JSONObject o, String key) {
        long millis;
        JSONObject date;
        try {
            date = o.getJSONObject(key);
            //Log.e("Date", date.toString());
            millis = date.getLong("usec");
            millis += date.getLong("sec") * 1000;
            //Log.e("Millis", "" + millis);
            return millis;

        } catch (JSONException e) {
            //Log.e("jsonDate", "Messed up somehow");
            return 0;
        }
    }

}
