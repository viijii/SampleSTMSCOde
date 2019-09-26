package com.stms.webservices;


import retrofit2.Retrofit;

 public class RestClient
{

    //testing
    public static final String baseUrl = "http://115.98.3.215:90/sRamapptest/";

//production
   // public static final String baseUrl = "http://71.40.116.146/sRamapp_prod/";

     //for with in a network testing
    //public static final String baseUrl = "http://192.168.0.21:90/sRamapptest/";

     public static Retrofit client = new Retrofit.Builder().baseUrl(baseUrl).build();
}




