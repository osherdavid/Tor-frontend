package com.eph.tor.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eph.tor.AccountDetails;
import com.eph.tor.CallBackFunction;
import com.eph.tor.MainActivity;
import com.eph.tor.R;

import java.io.IOException;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener, CallBackFunction {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText repeatPasswordEditText;
    ProgressBar loadingProgressBar;
    TextView usernameErrorText;
    TextView passwordErrorText;
    TextView repeatPasswordErrorText;
    ImageButton signinButton;
    TextView signinError;
    AccountDetails accountDetails;
    ImageView signinSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        LinearLayout layoutRoot = findViewById(R.id.signin_root_layout);

        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorAccent)});

        layoutRoot.setBackground(gradientDrawable);

        usernameEditText = findViewById(R.id.username_signin);
        usernameEditText.requestFocus();
        passwordEditText = findViewById(R.id.password_signin);
        repeatPasswordEditText = findViewById(R.id.repeat_password);
        signinButton = findViewById(R.id.signin);
        loadingProgressBar = findViewById(R.id.signin_loading);
        usernameErrorText = findViewById(R.id.username_error_signin);
        passwordErrorText = findViewById(R.id.password_error_signin);
        repeatPasswordErrorText = findViewById(R.id.repeat_password_error);
        signinError = findViewById(R.id.signin_error);
        signinSuccess = findViewById(R.id.signin_success);
        signinButton.setOnClickListener(this);
        accountDetails = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                try {
                    SignIn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public <T> void callback(T... values) {
        String errorMessage = "";
        if (values.length == 1) {
            if (values[0] instanceof IOException) {
                errorMessage = getResources().getString(R.string.connection_error);
            }
        }
        boolean success = false;
        if(accountDetails.operation == AccountDetails.Operation.DOES_USER_EXIST){
            if (values.length == 1) {
                if (values[0].equals(true)) {
                    errorMessage = getResources().getString(R.string.user_exists_error);
                } else {
                    AccountDetails temp_ad = new AccountDetails(this.accountDetails);
                    this.accountDetails.SignIn();
                    return;
                }
            }
        }
        else if(accountDetails.operation == AccountDetails.Operation.SGININ) {
            if (values.length == 1) {
                if (values[0].equals(true)) {
                    success = true;
                } else {
                    errorMessage = getResources().getString(R.string.signin_error);
                }
            }
        }

        if (success) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    signinSuccess.setVisibility(View.VISIBLE);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent switchActivityIntent = new Intent(SigninActivity.this, LoginActivity.class);
            startActivity(switchActivityIntent);
            finish();
        }
        else {
            System.out.println("Not Verified!");
            final String finalErrorMessage = errorMessage;
            if(!errorMessage.equals("")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update the user interface
                        loading(false, finalErrorMessage);
                    }
                });
            }
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Update the user interface
                        loading(false);
                    }
                });
            }
        }
    }

    public void SignIn() throws IOException {
        signinError.setText("");
        usernameErrorText.setText("");
        passwordErrorText.setText("");
        repeatPasswordErrorText.setText("");
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String repeatPassword = repeatPasswordEditText.getText().toString();
        if(username.equals("")) {
            this.usernameErrorText.setText(R.string.enter_username_error);
            return;
        }
        if(password.equals("")) {
            this.passwordErrorText.setText(R.string.enter_password_error);
            return;
        }
        System.out.println("PASSWORD: " + password);
        System.out.println("REPEAT PASSWORD: " + repeatPassword);
        if(!repeatPassword.equals(password)) {
            this.repeatPasswordErrorText.setText(R.string.password_does_not_match);
            return;
        }
        signAccount(username, password);
    }

    private void signAccount(String username, String password) {
        this.accountDetails = new AccountDetails(username, password);
        this.accountDetails.setCallback(this);
        this.accountDetails.setCleanup(this);
        this.accountDetails.doesUserExists();
        loading(true);
    }

    private void loading(boolean isLoading, String errorMessage) {
        this.signinError.setText(errorMessage);
        loading(isLoading);
    }

    private void loading(boolean isLoading) {
        int loading_visibility = isLoading ? View.VISIBLE : View.GONE;
        int not_loading_visibility = isLoading ? View.GONE : View.VISIBLE;
        this.loadingProgressBar.setVisibility(loading_visibility);
        this.signinButton.setVisibility(not_loading_visibility);
        this.signinError.setVisibility(not_loading_visibility);
    }
}