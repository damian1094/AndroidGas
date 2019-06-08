package com.hfad.gaslevelapp.Database.RemoteDb;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    private static final String BASE_URL = "http://192.168.43.215/sanga/";
    private static Retrofit retrofit = null;

    public static Retrofit getApi(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
