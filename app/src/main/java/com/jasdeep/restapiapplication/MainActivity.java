package com.jasdeep.restapiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private int mUserId;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);
        getUser();
        updateTextView();
    }

    private void getUser() {
        mUserId = getIntent().getIntExtra(Util.USER_KEY, -1);
        mUsername = getIntent().getStringExtra(Util.USER_NAME);
        if (mUserId == -1 | mUsername == null) {
            startActivity(LoginActivity.getIntent(getApplicationContext(), -1, null));
        }
    }

    private void updateTextView() {

        mTextView.append("Hello, " + mUsername + "!" + " Your user id is " + mUserId + "\n\n");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https:jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    mTextView.setText(getString(R.string.code) + response.code());
                    return;
                }
                List<Post> posts = response.body();
                if (posts != null) {
                    for (Post post : posts) {
                        if (post.getUserId() == mUserId) {
                            String content = "";
                            content += "ID: " + post.getId() + "\n"
                                    + "User ID: " + post.getUserId() + "\n"
                                    + "Title: " + post.getTitle() + "\n"
                                    + "Text: " + post.getText() + "\n\n";
                            mTextView.append(content);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                mTextView.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(LoginActivity.getIntent(getApplicationContext(), -1, ""));
    }

    public static Intent getIntent(Context context, int userId, String username) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Util.USER_KEY, userId);
        intent.putExtra(Util.USER_NAME, username);
        return intent;
    }
}