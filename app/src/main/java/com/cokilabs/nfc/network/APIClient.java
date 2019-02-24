package com.cokilabs.nfc.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    public static APIInterface getClient() {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder;

//                if (auth.equals("")){
                    requestBuilder = original.newBuilder()
                            .method(original.method(), original.body());
//                }else{
//                    requestBuilder = original.newBuilder()
//                            .method(original.method(), original.body())
//                            .removeHeader("Authorization")
//                            .addHeader("Authorization", auth)
//                            .removeHeader("Accept")
//                            .addHeader("Accept", "application/json");
//                }

                //.removeHeader("User-Agent")
//                 .addHeader("User-Agent", ua)

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiURL.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        //.addConverterFactory(GsonConverterFactory.create())

        return retrofit.create(APIInterface.class);
    }

}