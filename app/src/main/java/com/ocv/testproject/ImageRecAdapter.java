package com.ocv.testproject;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;

import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;
import java.util.ArrayList;

/**
 * Created by walke on 10/4/2017.
 */



public class ImageRecAdapter extends RecyclerView.Adapter<ImageRecAdapter.ViewHolder> {


    ArrayList<String> images;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        View layout;
        RecyclerView imageList;
        ImageView image;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageList = (RecyclerView) layout.findViewById(R.id.recycle_info_image);
            image = (ImageView) layout.findViewById(R.id.image_recycle);
        }
    }

    //can pass my images into here via URL or something
    public ImageRecAdapter(ArrayList<String> images, Context context) {
        this.images = images;
        if(this.images == null) {
            this.images = new ArrayList<String>();
        }
        this.context = context;

    }

    @Override
    public ImageRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.all_image_recycler, parent, false);

        ImageRecAdapter.ViewHolder vh = new ImageRecAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ImageRecAdapter.ViewHolder holder, final int position) {
        Picasso.with(context).load(images.get(position)).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageViewer.Builder<>(context, images.subList(position, position + 1)).setStartPosition(0).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void openImage(String image) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(image).build();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


}
