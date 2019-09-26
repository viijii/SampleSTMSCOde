package com.stms.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Config
    {
        //public static final String IMAGE_DIRECTORY_NAME="LinksCourier";


        public static void  showToast(Activity context,String mess)
        {
            Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
        }

        public static final String IMAGE_DIRECTORY_NAME = "veggie";

        public static ProgressDialog progressDialogObj;

        public static void showLoader(Context context)
            {
                progressDialogObj = new ProgressDialog(context);
                progressDialogObj.setMessage("Please wait...");
                progressDialogObj.setCancelable(false);
                progressDialogObj.show();
            }

        public static void closeLoader()
            {
                if (progressDialogObj != null)
                    {
                        progressDialogObj.cancel();
                    }
            }

        public static void saveDeviceToken(Context c,String deviceToken)
            {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("deviceToken", deviceToken);
                editor.commit();
            }


        public static String getDeviceToken(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("deviceToken","");
        }


        public static void saveLoginUserType(Context c,String userType)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userType", userType);
            editor.commit();
        }

        public static String getLoginUserType(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("userType","");
        }

        public static void saveRole(Context c,String userType)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userRole", userType);
            editor.commit();
        }

        public static String getRole(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("userRole","");
        }
        //GCM Device Token

        public static void saveLoginStatus(Context c,String userType)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_status", userType);
            editor.commit();
        }

        public static String getLoginStatus(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("login_status","");
        }




        public static void saveUserId(Context c,String userType)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_id", userType);
            editor.commit();
        }

        public static String getUserId(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("user_id","");
        }
        public static void saveCorp_code(Context c,String userType)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("corp_code", userType);
            editor.commit();
        }

        public static String getCorp_code(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("corp_code","");
        }

        public static String getMobile(Context c){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("mobile","");
        }
        public static void saveUser_name(Context c,String userType)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_name", userType);
            editor.commit();
        }

        public static String getUser_name(Context c)
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
            return sharedPreferences.getString("user_name","");
        }






    }


