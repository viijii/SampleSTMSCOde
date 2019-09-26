package com.stms.CommonScreens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Customers.Customer_dashboard;
import com.stms.Customers.ImageUpload;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;

public class FieldImageUpload extends AppCompatActivity {
    Uri picUri;
    Button uploadtoserver,Back;
    TextView textView;
    ProgressDialog progressDialog ;
    ConnectionDetector cd;
    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;
    Bitmap selectedImage;
    String filepath,action;

    Intent intent;
    String title,location,imagePath;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_image_upload);
        byteArrayOutputStream = new ByteArrayOutputStream();
        Back=findViewById( R.id.back );
        uploadtoserver= findViewById( R.id.upload );
        textView= findViewById( R.id. text);
        ImageView fab = findViewById(R.id.fab);
        cd = new ConnectionDetector(getApplicationContext());

        intent=getIntent();
        title=intent.getStringExtra("title");
        location=intent.getStringExtra("location");

        Log.d(TAG, "onCreate: title"+title);
        Log.d(TAG, "onCreate: des"+location);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: fabbbbbb");
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });
        uploadtoserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addService();

                UploadImageToServer();

               /* Intent intent=new Intent(getApplicationContext(),FieldActivityUpload.class);
                startActivity(intent);*/

            }
        });
        /*recentposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FieldActivity.class);
                startActivity(intent);
            }
        });*/
        Back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        } );


        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

    }

    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }


    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
            Log.d( "TAG", "getCaptureImageOutputUri: "+getImage.getPath() );
        }
        return outputFileUri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {

            ImageView imageView = findViewById(R.id.imageView);

            if (requestCode == IMAGE_RESULT) {

                String filePath = getImageFilePath(data);

                imagePath = filePath;
                Log.d(TAG, "onActivityResult: imagedata"+imagePath);

                Log.d(TAG, "onActivityResult: path"+filePath);
                if (filePath != null) {
                    selectedImage = BitmapFactory.decodeFile(filePath);
                    imageView.setImageBitmap(selectedImage);
                }
            }

        }

    }


    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

 /*   public void addService()
    {

        Log.d(TAG, "addService: jxshxjx");
        selectedImage.compress( Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream );
        byteArray = byteArrayOutputStream.toByteArray();
        imagePath     = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d(TAG, "onResponse:third"+imagePath);
        try {

            if (cd.isConnectingToInternet()) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );

                Log.d( TAG, "onResponse:fourth" );
                Log.d( TAG, "onResponse:third23" + imagePath );

                Call<AdminRequestedResponse> call = api.addservice( "complaintRequest", Config.getCorp_code(getApplicationContext()), Config.getUserId(getApplicationContext()), title, description, vehicleId, imagePath );
                Log.d( TAG, "addService: "+action+ Config.getCorp_code( getApplicationContext() ) + " " + title + " " + description + " " + vehicleId + " " + imagePath );
                call.enqueue( new Callback<AdminRequestedResponse>() {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response) {

                        adminRequestedResponse = response.body();


                        //  Log.d(TAG, "onResponse:addServiceMessage"+adminRequestedResponse.getMessage());
                        if (adminRequestedResponse==null) {

                            Toast.makeText( FieldImageUpload.this, "something went wrong", Toast.LENGTH_SHORT ).show();
                        } else {
                            if (adminRequestedResponse.getStatus().equalsIgnoreCase( "true" )) {
                                progressDialog.dismiss();
                                Intent intent = new Intent( getApplicationContext(), Customer_dashboard.class );
                                startActivity( intent );
                            } else {
                                Log.d( TAG, "onResponse:ADDservice" + adminRequestedResponse.getStatus() );
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d( TAG, "onFailure: " + t.getMessage() );
                        Toast.makeText( getApplicationContext(),
                                "Try Again!",
                                Toast.LENGTH_LONG ).show();
                    }
                } );
            } else {
                Toast.makeText( getApplicationContext(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG ).show();
            }

        }

        catch(Exception e)
        {
            Log.d(TAG, "addService: exception"+e);
            System.out.println("Exception e"+e);
        }
    }*/

    public void UploadImageToServer(){


        selectedImage.compress( Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream );
        //   Log.d( "TAG", "UploadImageToServer: " +byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        filepath     = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d( "TAG", "UploadImageToServer: "+filepath );

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(FieldImageUpload.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(FieldImageUpload.this,string1,Toast.LENGTH_LONG).show();

                Intent i=new Intent(FieldImageUpload.this,FieldActivityUpload.class);
                startActivity( i );
            }

            @Override
            protected String doInBackground(Void... params) {


                HttpClient httpClient = new DefaultHttpClient();
                //testing
                HttpPost httppost = new HttpPost("http://192.168.0.21:90/sRamapptest/field_image_upload.php");
                //production
               // HttpPost httppost = new HttpPost("http://71.40.116.146/sRamapp_prod/field_image_upload.php");

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
                nameValuePairs.add(new BasicNameValuePair("action","fieldActivity") );
                nameValuePairs.add(new BasicNameValuePair("imagePath", filepath));
                Log.d(TAG, "doInBackground: "+filepath+" "+title+" "+location);
                nameValuePairs.add(new BasicNameValuePair("title", title));
                nameValuePairs.add(new BasicNameValuePair("location", location));
                nameValuePairs.add(new BasicNameValuePair("corp_code", Config.getCorp_code( getApplicationContext() )));


                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = null;
                try {
                    response = httpClient.execute(httppost, responseHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d( "TAG", "doInBackground: 12345567643"+response );


                //      return FinalData;
                return response;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }


}