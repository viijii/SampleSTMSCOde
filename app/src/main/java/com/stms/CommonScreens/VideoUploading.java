package com.stms.CommonScreens;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.stms.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

    public class VideoUploading extends AppCompatActivity {
    private static int serverResponseCode;
    private Button btn, uploadtoserver;
    private VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    String upurl;
    String title,location;
    TextView textViewResponse;
    Intent intent;

    private static final int multiplePermissions = 1;
    String[] permissions = new String[]{

            Manifest.permission.CAMERA,


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_upload);

        btn = findViewById( R.id.btn );
        uploadtoserver = findViewById( R.id.upload );
        videoView = findViewById( R.id.vv );
        textViewResponse= findViewById( R.id.textViewRespone );

      //  title1=title.getText().toString();
        Log.d("TAG", "onCreate:"+title);
      //  location1=location.getText().toString();

        intent=getIntent();
        title=intent.getStringExtra("title");
       location= intent.getStringExtra("location");

        checkPermissions();
        ActivityCompat.requestPermissions(VideoUploading.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        },1);

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        } );
        uploadtoserver.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//Toast.makeText(getApplicationContext(), "Please Do Select or Record Your Video First", Toast.LENGTH_SHORT).show();
              /*  if(upurl==null){
                    uploadtoserver.setVisibility( View.VISIBLE );
                    Toast.makeText( VideoUploading.this, "Please Capture Video", Toast.LENGTH_SHORT ).show();
                }else {*/
                    uploadVideo();
                   Intent intent=new Intent(getApplicationContext(),FieldActivityUpload.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);

              //  }
            }
        } );

    }

    private boolean checkPermissions() {
        //Config.addLog(" ", "passngerLogin: entered into checkpermission()");
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), multiplePermissions);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case multiplePermissions: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

// all permissions are granted.
                } else {

//permissions missing

                }
                return;
            }
        }
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder( this );
        pictureDialog.setTitle( "Select Action" );
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera"};
        pictureDialog.setItems( pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();

                                break;
                        }
                    }
                } );
        pictureDialog.show();
    }

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI );

        startActivityForResult( galleryIntent, GALLERY );
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent( MediaStore.ACTION_VIDEO_CAPTURE );
        startActivityForResult( intent, CAMERA );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d( "result", "" + resultCode );
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_CANCELED) {
            Log.d( "what", "cancle" );
            return;
        }
        if (requestCode == GALLERY) {

            if (data != null) {
                Uri contentURI = data.getData();

                String selectedVideoPath = getPath( contentURI );
                Log.d( "TAG", "onActivityResult:5555 "+selectedVideoPath );// /storage/emulated/0/DCIM/Camera/VID_20190228_091519.mp4
                saveVideoToInternalStorage( selectedVideoPath );
                videoView.setVideoURI( contentURI );
                videoView.requestFocus();
                videoView.start();

            }

        } else if (requestCode == CAMERA) {
            Uri contentURI = data.getData();
            String recordedVideoPath = getPath( contentURI );
            Log.d( "TAG", "onActivityResult:6666 "+recordedVideoPath );///storage/emulated/0/DCIM/Camera/VID_20190228_094810.mp4
            saveVideoToInternalStorage( recordedVideoPath );
            videoView.setVideoURI( contentURI );
            videoView.requestFocus();
            videoView.start();
        }
    }

    private void saveVideoToInternalStorage(String filePath) {

        File newfile ;
        upurl=filePath;
        Log.d( "TAG", "saveVideoToInternalStorage:path "+upurl );

        try {

            File currentFile = new File( filePath );
            Log.d( "TAG", "saveVideoToInternalStorage: 8888"+currentFile );
            File wallpaperDirectory = new File( Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY );
            Log.d( "TAG", "saveVideoToInternalStorage:1111" + wallpaperDirectory );
            newfile = new File( wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4" );
            Log.d( "TAG", "saveVideoToInternalStorage:2222" + newfile );



            // saveVideoToInternalStorage=newfile;

            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }


            if (currentFile.exists()) {

                InputStream in = new FileInputStream(currentFile);
                OutputStream out = new FileOutputStream(newfile);

                // Copy the bits from instream to outstream
                byte[] buf = new byte[1024];
                Log.d( "TAG", "saveVideoToInternalStorage: " );
                int len;

                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                Log.v( "vii", "Video file saved successfully." );
            }
            else {
                Log.v( "vii", "Video saving failed. Source file missing." );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Log.d( "TAG", "saveVideoToInternalStorage: 12121212" + filePath );
    }


    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query( uri, projection, null, null, null );
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow( MediaStore.Video.Media.DATA );
            cursor.moveToFirst();
            return cursor.getString( column_index );
        } else
            return null;
    }

        private void uploadVideo() {

        class UploadVideo extends AsyncTask<Void, Void, String> {


            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(VideoUploading.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                textViewResponse.setText( Html.fromHtml("<b><a href='" + s + "'>" + s + "</a></b>"));
                //    textViewResponse.setMovementMethod( LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload(getApplicationContext());
                Log.d( "TAG", "doInBackground:10101 "+u );
                @SuppressLint("WrongThread") String msg = u.uploadVideo(upurl,title,location);
                Log.d( "TAG", "doInBackground:66666 "+upurl );
               // Log.d( "TAG", "doInBackground:6 "+title );
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        Log.d( "TAG", "uploadVideo:33333 "+uv );
        uv.execute();

    }
}

