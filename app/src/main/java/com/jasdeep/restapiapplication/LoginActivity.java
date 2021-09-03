package com.jasdeep.restapiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static final String USER_KEY = "com.jasdeep.restapiapplication.USER_KEY";
    public static final String USER_NAME = "com.jasdeep.restapiapplication.USERNAME";

    private EditText mUserName;
    private EditText mPassword;
    private Button mSignIn;

    private int mUserIdVal;
    private String mUserNameVal;
    private String mPasswordVal;

    private User mUser;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        getUsers();

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoginInfo();
                if (validateUser()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra(USER_KEY, mUserIdVal);
                    intent.putExtra(USER_NAME, mUserNameVal);
                    startActivity(intent);
                }
            }
        });
    }

    private void getUsers() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https:jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<User>> call = jsonPlaceHolderApi.getUsers();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error Code: "+response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                mUsers = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void findViews() {
        mUserName = findViewById(R.id.username_edittext);
        mPassword = findViewById(R.id.password_edittext);
        mSignIn = findViewById(R.id.sign_in_button);
    }

    private boolean validateUser() {

        for(User user: mUsers) {
            if(user.getUsername().equals(mUserNameVal)) {
                mUser = user;
                break;
            }
        }


        if(mUser == null) {
            Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_SHORT).show();
            return false;
        }

        mUserIdVal = mUser.getUserId();
        if (mUserIdVal <= 0) {
            Toast.makeText(getApplicationContext(), "Enter valid username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mPasswordVal.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter valid password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mPasswordVal.equals(mUser.getUsername()+"password")) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void getLoginInfo() {
        mUserNameVal = mUserName.getText().toString();

        mPasswordVal = mPassword.getText().toString();
    }
}