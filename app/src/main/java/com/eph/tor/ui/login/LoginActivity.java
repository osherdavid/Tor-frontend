package com.eph.tor.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eph.tor.AccountDetails;
import com.eph.tor.CallBackFunction;
import com.eph.tor.MainActivity;
import com.eph.tor.R;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CallBackFunction {

    EditText usernameEditText;
    EditText passwordEditText;
    ProgressBar loadingProgressBar;
    TextView usernameErrorText;
    TextView passwordErrorText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);
        usernameErrorText = findViewById(R.id.username_error);
        passwordErrorText = findViewById(R.id.password_error);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                try {
                    LogIn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void LogIn() throws IOException {
        usernameErrorText.setText("");
        passwordErrorText.setText("");
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.equals("")) {
            this.usernameErrorText.setText(R.string.enter_username_error);
            return;
        }
        if(password.equals("")) {
            this.passwordErrorText.setText(R.string.enter_password_error);
            return;
        }
        verifyAccount(username, password);
    }

    private void verifyAccount(String username, String password) {
        AccountDetails ad = new AccountDetails(username, password);
        ad.setCallback(this);
        ad.start();
    }

    @SafeVarargs
    @Override
    public final <T> void callback(T... values) {
        boolean isVerified = values[0].equals(true);
        if (isVerified) {
            Intent switchActivityIntent = new Intent(this, MainActivity.class);
            startActivity(switchActivityIntent);
            finish();
        }
        else {
            System.out.println("Not Verified!");
        }
    }
}