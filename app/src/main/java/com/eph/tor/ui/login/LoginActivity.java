package com.eph.tor.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eph.tor.AccountDetails;
import com.eph.tor.CallBackFunction;
import com.eph.tor.MainActivity;
import com.eph.tor.R;

import org.w3c.dom.Text;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CallBackFunction {

    EditText usernameEditText;
    EditText passwordEditText;
    ProgressBar loadingProgressBar;
    TextView usernameErrorText;
    TextView passwordErrorText;
    ImageButton loginButton;
    TextView singInHere;
    TextView loginError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout layoutRoot = findViewById(R.id.login_root_layout);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorAccent)});

        layoutRoot.setBackground(gradientDrawable);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.login_loading);
        usernameErrorText = findViewById(R.id.username_error);
        passwordErrorText = findViewById(R.id.password_error);
        singInHere = findViewById(R.id.sign_in_here);
        loginError = findViewById(R.id.login_error);
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
        ad.setCleanup(this);
        ad.start();
        loading(true);
    }

    private void loading(boolean isLoading, String errorMessage) {
        this.loginError.setText(errorMessage);
        loading(isLoading);
    }

    private void loading(boolean isLoading) {
        int loading_visibility = isLoading ? View.VISIBLE : View.GONE;
        int not_loading_visibility = isLoading ? View.GONE : View.VISIBLE;
        this.loadingProgressBar.setVisibility(loading_visibility);
        this.loginError.setVisibility(not_loading_visibility);
        this.loginButton.setVisibility(not_loading_visibility);
        this.singInHere.setVisibility(not_loading_visibility);
    }

    @SafeVarargs
    @Override
    public final <T> void callback(T... values) {
        boolean isVerified = false;
        String errorMessage = getResources().getString(R.string.user_invalid);
        if (values.length == 1) {
            if (values[0] instanceof IOException) {
                errorMessage = getResources().getString(R.string.connection_error);
            }
            else {
                isVerified = values[0].equals(true);
            }
        }
        if (isVerified) {
            Intent switchActivityIntent = new Intent(this, MainActivity.class);
            startActivity(switchActivityIntent);
            finish();
        }
        else {
            System.out.println("Not Verified!");
            final String finalErrorMessage = errorMessage;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Update the user interface
                    loading(false, finalErrorMessage);
                }
            });
        }
    }
}