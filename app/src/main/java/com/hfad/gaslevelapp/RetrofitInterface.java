package com.hfad.gaslevelapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @GET("display.php")
    Call<List<GasObject>> getWeight();

    @FormUrlEncoded
    @POST("new_user.php")
    Call<GasObject> saveToDatabase(
            @Field("name") String name,
            @Field("username") String username,
            @Field("password") String password
    );
}
