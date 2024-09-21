package com.androidpostapi;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {
    private RetrofitInterface retrofitAPI;

    public PostRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitAPI = retrofit.create(RetrofitInterface.class);
    }

    public Call<DataModal> createPost(DataModal modal) {
        return retrofitAPI.createPost(modal);
    }
}