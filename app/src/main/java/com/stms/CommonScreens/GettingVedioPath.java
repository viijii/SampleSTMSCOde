package com.stms.CommonScreens;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.stms.R;

public class GettingVedioPath extends AppCompatActivity {
    VideoView videoView;
    Intent intent;
    String video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_vedio_path);

         videoView  =findViewById(R.id.videoView);

        intent=getIntent();
        Log.d("TAG", "onCreate:kkk "+intent.getStringExtra("imageview"));
        video= intent.getStringExtra("imageview");
        Log.d("TAG", "onCreate:jjjj "+video);
       if(video.endsWith(".mp4")) {
            Uri uri = Uri.parse(video);
            videoView.setVideoURI(uri);
            videoView.start();
        }else{}
        }
    }



