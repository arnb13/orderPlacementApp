package com.example.order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("api_create_user/")
    Call<User> createUser(@Body User user);

    @POST("api_login/")
    Call<UserLogin> loginUser(@Body UserLogin userLogin);

    @GET("api_get_product/")
    Call<List<Product>> getProduct();

    @POST("api_order/")
    Call<Order> createOrder(@Body Order order);
}
