package com.eph.tor;


import java.security.MessageDigest;

public class AccountDetails extends HTTPRequest {
    public static enum Operation {
            VERIFY, DOES_USER_EXIST, SGININ
    }
    private String username;
    private String password;
    private boolean isVerified;
    public Operation operation;

    public AccountDetails(String username, String password) {
        this.username = username;
        this.password = calculateHash(password);
        this.isVerified = false;
    }

    public void setCallback(CallBackFunction callBackFunction) {
        this.callback = callBackFunction;
    }

    public void setCleanup(CallBackFunction cleanupFunction) {
        this.cleanup = cleanupFunction;
    }

    public void Verify() {
        this.optionalUrl = "/users/"+this.username+"/"+password;
        this.operation = Operation.VERIFY;
        this.requestType = "GET";
        this.start();
    }

    public void doesUserExists() {
        this.optionalUrl = "/users/"+this.username;
        this.operation = Operation.DOES_USER_EXIST;
        this.requestType = "GET";
        this.start();
    }

    public void SignIn() {
        this.optionalUrl = "/users/"+this.username+"/"+password;
        this.operation = Operation.SGININ;
        this.postParams = "{\"username\":\""+this.username+"\",\"password\":\""+this.password+"\"}";
        this.requestType = "POST";
        this.start();
    }

    @Override
    public <T> boolean task(String result) {
        this.isVerified = Boolean.parseBoolean(result);
        System.out.println("run: "+ result);
        return this.isVerified;
    }


    private static String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}