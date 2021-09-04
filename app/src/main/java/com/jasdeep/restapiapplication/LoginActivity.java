package com.jasdeep.restapiapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
                setPasswords();
                getLoginInfo();
                if (validateUser()) {
                    startActivity(MainActivity.getIntent(getApplicationContext(), mUserIdVal, mUserNameVal));
                }
            }
        });
    }

    private void setPasswords() {
        if(mUsers == null) {
            return;
        }
        for(User user: mUsers) {
            user.setPassword("Password " + user.getUsername());
        }
    }

    public static Intent getIntent(Context context, int userId, String username) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Util.USER_KEY, userId);
        intent.putExtra(Util.USER_NAME, username);
        return intent;
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
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error Code: " + response.code(), Toast.LENGTH_LONG).show();
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

        for (User user : mUsers) {
            if (user.getUsername().equals(mUserNameVal)) {
                mUser = user;
                break;
            }
        }

        if (mUser == null) {
            mUserName.setError("No user found");
            return false;
        }

        mUserIdVal = mUser.getUserId();
        if (mUserIdVal <= 0) {
            mUserName.setError("Invalid username");
            return false;
        }
        if (mPasswordVal.length() == 0) {
            mPassword.setError("Invalid password");
            return false;
        }
        if (mPasswordVal.equals(mUser.getPassword())) {
            return true;
        } else {
            mPassword.setError("Incorrect Password");
            return false;
        }

    }

    private void getLoginInfo() {
        mUserNameVal = getUsername();

        mPasswordVal = getPassword();
    }

    private String getUsername() {
        return mUserName.getText().toString();
    }

    private String getPassword() {
        return mPassword.getText().toString();
    }
}