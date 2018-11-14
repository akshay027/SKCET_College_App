package com.example.exalogic.mobioticsapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    //the URL having the json data
    private static final String JSON_URL = "https://interview-e18de.firebaseio.com/media.json?print=pretty";

    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    ArrayList<VideoList> videoLists;
    ListViewAdapter adapter;
    ArrayList<VideoList> newlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        //initializing listview and hero list
        listView = findViewById(R.id.listView);
        videoLists = new ArrayList<>();
        newlist = new ArrayList<>();
        //this method will fetch and parse the data
        loadHeroList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newlist.addAll(videoLists);
                newlist.remove(videoLists.get(position));


                Log.e("videoLists", "---" + videoLists);
                Log.e("newlist", "---" + newlist);
                Intent intent = new Intent(getApplicationContext(), VideoPlayActivity.class);
                intent.putExtra("url", videoLists.get(position).getUrl());
                intent.putExtra("title", videoLists.get(position).getTitle());
                intent.putExtra("description", videoLists.get(position).getDescription());
               // intent.putExtra("list",  newlist.toString());
                intent.putExtra("newlist", newlist);
                intent.putExtra("oldlist", videoLists);
                intent.putExtra("postion", videoLists.get(position));
                /*Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)newlist);
                intent.putExtra("BUNDLE",args);*/

                startActivity(intent);
            }
        });
    }

    private void loadHeroList() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            //JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray jsonArray = new JSONArray(response);

                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject heroObject = jsonArray.getJSONObject(i);

                                //creating a hero object and giving them the values from json object
                                VideoList videoList = new VideoList(heroObject.getString("description"), heroObject.getString("id")
                                        , heroObject.getString("title"), heroObject.getString("url"), heroObject.getString("thumb"));

                                //adding the hero to herolist
                                videoLists.add(videoList);
                            }

                            //creating custom adapter object
                            adapter = new ListViewAdapter(videoLists, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}