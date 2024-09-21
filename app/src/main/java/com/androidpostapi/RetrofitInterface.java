package com.androidpostapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("users")
    Call<DataModal> createPost(@Body DataModal dataModal);
}
