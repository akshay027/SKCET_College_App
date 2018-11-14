package com.example.exalogic.mobioticsapp;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;





public class ListViewAdapter extends ArrayAdapter<VideoList> {

    //the hero list that will be displayed
    private List<VideoList> videLists;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapter(List<VideoList> videLists, Context mCtx) {
        super(mCtx, R.layout.list_items, videLists);
        this.videLists = videLists;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);

        //getting text views
        TextView textViewTitle = listViewItem.findViewById(R.id.textViewTitle);
        // TextView textViewImageUrl = listViewItem.findViewById(R.id.textViewImageUrl);
        ImageView imageView = listViewItem.findViewById(R.id.imageView);
        //Getting the hero for the specified position
        VideoList videList = videLists.get(position);

        //setting hero values to textviews
        textViewTitle.setText(videList.getTitle());
        //  textViewImageUrl.setText(videList.getDescription());
        Glide.with(mCtx).load(videList.getThumb()).override(600, 200)
                .into(imageView);

        //returning the listitem
        return listViewItem;
    }
}
