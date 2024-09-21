package com.androidpostapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext,
    // button, textview and progressbar.
    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;
    private PostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our views
        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        viewModel = new PostViewModel();

        // Observing loading status
        viewModel.isLoading().observe(this, isLoading -> {
            if (isLoading != null) {
                loadingPB.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        // Observing response text
        viewModel.getResponseText().observe(this, responseText -> {
            responseTV.setText(responseText);
            nameEdt.setText("");
            jobEdt.setText("");
        });

        postDataBtn.setOnClickListener(v -> {
            String name = nameEdt.getText().toString();
            String job = jobEdt.getText().toString();
            if (name.isEmpty() || job.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both values", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.postData(name, job);
        });
    }
}