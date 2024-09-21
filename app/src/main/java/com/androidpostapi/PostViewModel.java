package com.androidpostapi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostViewModel extends ViewModel {
    private MutableLiveData<String> responseText = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private PostRepository postRepository;

    public PostViewModel() {
        postRepository = new PostRepository();
    }

    public LiveData<String> getResponseText() {
        return responseText;
    }

    public LiveData<Boolean> isLoading() {
        return loading;
    }

    public void postData(String name, String job) {
        loading.setValue(true);

        DataModal modal = new DataModal(name, job);
        Call<DataModal> call = postRepository.createPost(modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                loading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccess(response.body());
                } else {
                    handleError(response.message());
                }
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                loading.setValue(false);
                handleError(t.getMessage());
            }
        });
    }

    private void handleSuccess(DataModal responseFromAPI) {
        String responseString = "\nName : " + responseFromAPI.getName() + "\nJob : " + responseFromAPI.getJob();
        responseText.setValue(responseString);
    }

    private void handleError(String errorMessage) {
        responseText.setValue("Error: " + errorMessage);
    }
}