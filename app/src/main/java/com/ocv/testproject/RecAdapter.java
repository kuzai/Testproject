package com.ocv.testproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * Created by walke on 10/3/2017.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {


    final String idKey = "_id";
    final String creatorKey = "creator";
    final String dateKey = "date";
    final String titleKey = "title";
    final String contentKey = "content";
    final String blogKey = "blogID";
    final String imagesKey = "images";
    final String statusKey = "status";

    public Context context;
    FragmentManager fm;

    boolean useDarkTheme;

    private ArrayList<JSONObject> mDataSet;
    private JSONHelper helper = new JSONHelper();


    public class ViewHolder extends RecyclerView.ViewHolder {

        public View layout;
        public RecyclerView recView;
        public TextView title;
        public TextView date;
        public TextView description;
        public ImageView image;





        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            layout = v;
            recView = (RecyclerView) layout.findViewById(R.id.recyclyer_view_list);
            title = (TextView) layout.findViewById(R.id.json_title);
            date = (TextView) layout.findViewById(R.id.json_date);
            description = (TextView) layout.findViewById(R.id.json_description);
            image = (ImageView) layout.findViewById(R.id.list_image);
        }
    }

    //modify this constructor to match the data
    public RecAdapter(ArrayList<JSONObject> myDataSheet, Context context, boolean useDarkTheme) {
        mDataSet = myDataSheet;
        this.context = context;
        this.useDarkTheme = useDarkTheme;
    }


    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.my_text_view, parent, false);

        //create a view

        //set the parameters of the view
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }


    //this replaces the contents of a view
    @Override
    public void onBindViewHolder(final RecAdapter.ViewHolder holder, final int position) {
        //get element at pos from data set
        //replace contents of view with element
        final JSONObject name = mDataSet.get(position);
        holder.title.setText(helper.jsonParser(name, titleKey));

        String content = helper.jsonParser(name, contentKey);
        Spanned text = Html.fromHtml(content);
        holder.description.setText(text);

        Date date = new Date(helper.jsonDateParser(name, dateKey));
        holder.date.setText(date.toString());
        String url = helper.getJSONImages(name, imagesKey, "small");
        if(url != null) {
            Picasso.with(context).load(url).into(holder.image);
        }
        else holder.image.setVisibility(View.GONE);


        //getJSONImages(name, imagesKey);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("Item No.: ", "" + position);

                //get the data into the right categories.
                //get image urls and convert to string array
                //get title
                //get date
                //get description
                //bundle all of the above
                //start fragment with said bundle and then unpack

                ArrayList<String> images = helper.getAllImages(name, imagesKey);
                String titleB = helper.jsonParser(name, titleKey);
                long millisB = helper.jsonDateParser(name, dateKey);
                String contentB = helper.jsonParser(name, contentKey);
                //Log.e("ContenB", contentB);

                Bundle bundle = new Bundle();
                bundle.putBoolean("useDarkTheme", useDarkTheme);
                bundle.putStringArrayList("images", images);
                bundle.putLong("date", millisB);
                bundle.putString("content", contentB);
                bundle.putString("title", titleB);
                //Log.e("There", "" + bundle.containsKey("content"));

                android.support.v4.app.Fragment fragment = new InfoFragment();
                fragment.setArguments(bundle);

                fm = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.list_container, fragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });


    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }




}
