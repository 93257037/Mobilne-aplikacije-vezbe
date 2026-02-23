package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    // Task 7: GET method to retrieve all products
    @GET("products")
    Call<List<Proizvod>> getAllProducts();

    // Task 7: POST method to add a product
    @POST("products")
    Call<Proizvod> addProduct(@Body Proizvod proizvod);

    // Task 10: Beeceptor dummy-json endpoint
    @GET("dummy-json")
    Call<List<Proizvod>> getDummyJson();
}
