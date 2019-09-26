package com.stms.Customers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import com.stms.R;

public class ImageViewPurpose extends AppCompatActivity {
    Intent i;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView imageView;
    String s;
    ZoomageView imageZoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_purpose);

       // imageView=findViewById(R.id.image1);
       // mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        imageZoom  = findViewById(R.id.image1);

        i=getIntent();
        s= i.getStringExtra("imagepath");
        Log.d("TAG", "onCreate: "+s+ " "+i.getStringExtra("imagepath"));

        Picasso.with(getApplicationContext()).load(s).
                    resize(1800, 1800).centerInside().into(imageZoom);

    }

    /*public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.max(mScaleFactor, 1.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }*/

}
