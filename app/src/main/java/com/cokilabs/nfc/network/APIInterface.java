package com.cokilabs.nfc.network;

/**
 * Created by Jim Geovedi on 3/30/2017.
 */


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    // account
    @POST("login")
    Call<ResponseBody> login(
            @Query("username") String username,
            @Query("password") String password
    );


    @GET("jadwal")
    Call<ResponseBody> getJadwals();


    @FormUrlEncoded
    @POST("presensi")
    Call<ResponseBody> postKehadiran(
            @Field("nim") String nim
    );

    @GET("presensi/akhiri")
    Call<ResponseBody> akhiriKuliah(
            @Field("id-jadwal") String id_jadwal
    );




}