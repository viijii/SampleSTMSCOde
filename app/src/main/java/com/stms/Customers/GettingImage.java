package com.stms.Customers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stms.R;

public class GettingImage extends AppCompatActivity {
   ImageView imageView;
   Intent intent;
   String image1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getting_image);

        imageView=findViewById(R.id.image);

        intent=getIntent();
        Log.d("TAG", "onCreate:mmm "+intent.getStringExtra("imageview"));
       image1= intent.getStringExtra("imageview");
        Log.d("TAG", "onCreate:ffff "+image1);

        try {
           /* if(image1.endsWith(".png")) {*/
                Picasso.with(getApplicationContext()).load(image1).into(imageView);
           /* }else{}*/
        }catch(Exception e){

        }
    }
}
