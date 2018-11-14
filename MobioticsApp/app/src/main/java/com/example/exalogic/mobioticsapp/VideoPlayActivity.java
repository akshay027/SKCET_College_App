package com.example.exalogic.mobioticsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class VideoPlayActivity extends AppCompatActivity {
    String url, title, description;
    String pos;
    private boolean video = true;
    Button button;
    VideoView videoView;
    private TextView textViewtitle, textViewDescription;
    MediaController mediaController;
    ArrayList <String> simplelist;
    ArrayList<VideoList> newlist,oldlist;
    ArrayList<String> arrayList=new ArrayList();
    NewListViewAdapter newListViewAdapter;
    ListView listView;
    int i=0;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView = findViewById(R.id.videoView);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewtitle = findViewById(R.id.textViewtitle);
        listView = findViewById(R.id.listView);

        mediaController = new MediaController(this);
        button = findViewById(R.id.button);

        oldlist= new ArrayList<>();
        newlist=new ArrayList<>();

        Intent intent = getIntent();

        url = intent.getStringExtra("url");
        //Bundle args = intent.getBundleExtra("BUNDLE");
        newlist = (ArrayList<VideoList>)getIntent().getSerializableExtra("newlist");
        oldlist = (ArrayList<VideoList>)getIntent().getSerializableExtra("oldlist");
       //ArrayList<VideoList> newlist = (ArrayList<VideoList>) args.getSerializable("ARRAYLIST");
        //newlist.add(intent.getStringExtra("list"));
        title = intent.getStringExtra("title");
        pos=intent.getStringExtra("postion");
        description = intent.getStringExtra("description");
        textViewDescription.setText(description);
        textViewtitle.setText(title);
        //videoView.start();

        Log.e("newlistaaa", "---" + newlist);
        Log.e("oldlisttttt", "---" + oldlist);
        arrayList.add(url);
        for (int j = 0; j < newlist.size(); j++) {
            arrayList.add(newlist.get(i).getUrl());
        }

        newListViewAdapter = new NewListViewAdapter(newlist, getApplicationContext());

        //adding the adapter to listview
        listView.setAdapter(newListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //newlist.addAll(oldlist);
                newlist.remove(newlist.get(position));
                newListViewAdapter = new NewListViewAdapter(newlist, getApplicationContext());

                //adding the adapter to listview
                listView.setAdapter(newListViewAdapter);
                url="";
                url=newlist.get(position).getUrl();
                videoView.setVideoPath(url);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();
                button.setVisibility(View.GONE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                videoView.setVideoPath(arrayList.get(i));
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();
                button.setVisibility(View.GONE);

                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    public void onCompletion(final MediaPlayer mp) {

                        i = (i + 1) % arrayList.size();

                        videoView.setVideoPath(arrayList.get(i));
                        videoView.start();
                    }
                });

            }

        });
    }


}
