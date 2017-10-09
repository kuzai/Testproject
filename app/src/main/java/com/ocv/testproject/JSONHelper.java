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
            Log.e("JSON Error: ", "The given key: "+key + " could not be found");
            return null;
        }
        return res;
    }

    public JSONArray jsonSubarray(JSONObject o, String key) {
        JSONArray array;
        try {
            array = o.getJSONArray(key);
        } catch (JSONException e) {
            Log.e("Array Key: ", "Array key wasn't found");
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


    public ArrayList<JSONObject> getAllImages(JSONObject o, String key) {
        ArrayList<JSONObject> urls = new ArrayList<>();
        try {
            JSONArray images = o.getJSONArray(key);
            for(int i = 0; i < images.length(); i++) {
                urls.add(images.getJSONObject(i));
                Log.i("URL retrieved", images.getJSONObject(i).toString());
            }
            return urls;
        } catch (JSONException e) {
            Log.e("JSON Error", "Unknown index");
            return null;
        }
    }


    public long jsonDateParser(JSONObject o, String key) {
        long millis;
        JSONObject date;
        try {
            date = o.getJSONObject(key);
            millis = date.getLong("usec");
            millis += date.getLong("sec") * 1000;
            return millis;

        } catch (JSONException e) {
            Log.e("jsonDate", "Date format as given doesn't exist");
            return 0;
        }
    }

    public void printJSONImageArray(JSONObject o, String key) {
        try {
            JSONArray imageArray = o.getJSONArray(key);
            Log.i("Image Array", imageArray.toString());
        } catch (JSONException e) {
            Log.e("JSON Image Array", "The image array couldn't be found");
        }

    }

    public ArrayList<String> getAllImageTypeURLS(ArrayList<JSONObject> o, String key) {
        ArrayList<String> urls = new ArrayList<>();
        for(JSONObject j : o) {
            try {
                String url = j.getString(key);
                urls.add(url);
                Log.i("Image added", "" + url + ", key: " + key);
            } catch (JSONException e) {
                Log.e("Image key doesn't exist", "JSONArray: " + o.toString() + ", " + "key: " + key);
            }
        }
        return urls;
    }

    public String getFirstImageType(ArrayList<JSONObject> o, String key) {
        String url;
        for(JSONObject j : o) {
            try {
                url = j.getString(key);
                Log.i("Image url returned type", "" + key + ", url: " + url);
                return url;
            } catch (JSONException e) {
                Log.e("Image didn't exist", "JSONArray: " + o.toString() + ", key: " + key);
            }
        }
        return null;
    }

}
