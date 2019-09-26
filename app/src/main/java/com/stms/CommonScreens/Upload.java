package com.stms.CommonScreens;

import android.content.Context;
import android.util.Log;

import com.stms.utils.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import static android.content.ContentValues.TAG;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Upload {
    //testing
    public static final String UPLOAD_URL=  "http://115.98.3.215:90/sRamapptest/video_uploads.php" ;
    //production
    //public static final String UPLOAD_URL=  "http://71.40.116.146/sRamapp_prod/video_uploads.php" ;
    public static final String title="title";
    public static  final String location="location";
    // public static final String UPLOAD_URL=  "http://toobworks.com/hap/video_uploads.php" ;

    // variable to hold context
    private Context context;

//save the context recievied via constructor in a local variable

    public Upload(Context context){
        this.context=context;
    }


    private int serverResponseCode;
    File sourceFile;
    public String uploadVideo(String file,String title,String location) {
        Log.d(TAG, "uploadVideo1: "+file);

        Log.d(TAG, "uploadVideo: kcontext"+Config.getCorp_code(context));

        Log.d(TAG, "uploadVideo1: "+title);
        Log.d(TAG, "uploadVideo1: "+location);
        String fileName = file;
        String titleName=title;
        String locationName=location;
        Log.d( TAG, "uploadVideo: 909090" + fileName +titleName +locationName);
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        try
        {
            sourceFile = new File( file );
            Log.d( TAG, "uploadVideo: source" + sourceFile);// uploadVideo: source/storage/emulated/0/DCIM/Camera/VID_20190228_210331.mp4
            if (!sourceFile.isFile()) {
                Log.e( "Huzza", "Source File Does not exist" );

                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(context, "Please Do Select or Record Your Video First", Toast.LENGTH_SHORT).show();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream( sourceFile );
            Log.d( TAG, "uploadVideo: input" + fileInputStream );   // uploadVideo: inputjava.io.FileInputStream@81b9a
            URL url = new URL( UPLOAD_URL );
            conn = (HttpURLConnection) url.openConnection();
            Log.d( TAG, "uploadVideo: connnnnn" + conn );
            conn.setDoInput( true );
            conn.setDoOutput( true );
            conn.setUseCaches( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Connection", "Keep-Alive" );
            conn.setRequestProperty( "ENCTYPE", "multipart/form-data" );
            conn.setRequestProperty( "Content-Type", "multipart/form-data;boundary=" + boundary );


            conn.setRequestProperty( "myFile", fileName );
            conn.setRequestProperty( "titleName", titleName );
            conn.setRequestProperty( "locationName", locationName );
            conn.setRequestProperty("corp_code", Config.getCorp_code(context));
            Log.d( TAG, "uploadVideo: 88088" + fileName +titleName +locationName);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            Log.d(TAG, "uploadVideo:titke"+titleName);
            Log.d(TAG, "uploadVideo:location"+locationName);

            dos = new DataOutputStream( conn.getOutputStream() );
            Log.d( TAG, "uploadVideo: data1" + dos );

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"titleName\""
                    + lineEnd);
            Log.d(TAG, "uploadVideo: llll"+titleName);
            dos.writeBytes(lineEnd);

            // assign value
            dos.writeBytes(titleName);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"locationName\""
                    + lineEnd);
            Log.d(TAG, "uploadVideo: llll"+titleName);
            dos.writeBytes(lineEnd);

            // assign value
            dos.writeBytes(locationName);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"corp_code\""
                    + lineEnd);
            Log.d(TAG, "uploadVideo: llll"+titleName);
            dos.writeBytes(lineEnd);

            // assign value
            dos.writeBytes(Config.getCorp_code(context));
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);


            dos.writeBytes( twoHyphens + boundary + lineEnd );
            dos.writeBytes( "Content-Disposition: form-data; name=\"myFile\";filename=\"" + fileName + "\"" +lineEnd );
            Log.d(TAG, "uploadVideo: rajiiii"+fileName);
            dos.writeBytes( lineEnd );

            bytesAvailable = fileInputStream.available();
            Log.i( "Huzza", "Initial .available : " + bytesAvailable );
            Log.d( TAG, "uploadVideo: available" + bytesAvailable );

            bufferSize = Math.min( bytesAvailable, maxBufferSize );
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read( buffer, 0, bufferSize );
            Log.d( TAG, "uploadVideo: rachhaa" + bytesRead );

            while (bytesRead > 0) {
                dos.write( buffer, 0, bufferSize );
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min( bytesAvailable, maxBufferSize );
                bytesRead = fileInputStream.read( buffer, 0, bufferSize );
            }

            dos.writeBytes( lineEnd );
            dos.writeBytes( twoHyphens + boundary + twoHyphens + lineEnd );

            serverResponseCode = conn.getResponseCode();
            Log.d( TAG, "uploadVideo: server"+serverResponseCode );

            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            Log.d(TAG, "uploadVideoex: "+ex);
            ex.printStackTrace();
        } catch (Exception e) {

            Log.d(TAG, "uploadVideoe: "+e);
            e.printStackTrace();
        }

        if (serverResponseCode == 200) {
            StringBuilder sb = new StringBuilder();


            try {
                BufferedReader rd = new BufferedReader( new InputStreamReader( conn
                        .getInputStream() ) );
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append( line );
                }
                rd.close();
            } catch (IOException ioex) {
            }
            return sb.toString();
        } else {
            Log.d(TAG, "uploadVideo:11 "+serverResponseCode);
            return "Could not upload,Please try Again";
            //  return "Failed";

        }
    }
}