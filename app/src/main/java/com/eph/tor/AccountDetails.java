package com.eph.tor;


public class AccountDetails extends HTTPRequest {
    private String username;
    private String password;
    private boolean isVerified;

    public AccountDetails(String username, String password) {
        this.username = username;
        this.password = password;
        this.optionalUrl = "/users/"+this.username+"/"+password;
        this.isVerified = false;
    }

    public void setCallback(CallBackFunction callBackFunction) {
        this.callback = callBackFunction;
    }

    public void setCleanup(CallBackFunction cleanupFunction) {
        this.cleanup = cleanupFunction;
    }

    @Override
    public <T> boolean task(String result) {
        this.isVerified = Boolean.parseBoolean(result);
        System.out.println("run: "+ result);
        return this.isVerified;
    }
}