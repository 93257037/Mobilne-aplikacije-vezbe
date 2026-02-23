package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    
    // Base URL for your backend API (Task 7)
    private static final String BASE_URL = "https://your-backend-api.com/api/";
    
    // Base URL for beeceptor (Task 10)
    private static final String BEEceptor_BASE_URL = "https://my-android-retrofit.free.beeceptor.com/";
    
    private static Retrofit retrofit = null;
    private static Retrofit beeceptorRetrofit = null;

    private static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }
    
    // Get Retrofit instance for main API (Task 7)
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        return retrofit;
    }
    
    // Get Retrofit instance for beeceptor API (Task 10)
    public static Retrofit getBeeceptorRetrofitInstance() {
        if (beeceptorRetrofit == null) {
            beeceptorRetrofit = new Retrofit.Builder()
                    .baseUrl(BEEceptor_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
        }
        return beeceptorRetrofit;
    }
    
    // Get API service for main backend
    public static ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
    
    // Get API service for beeceptor
    public static ApiService getBeeceptorApiService() {
        return getBeeceptorRetrofitInstance().create(ApiService.class);
    }
}
