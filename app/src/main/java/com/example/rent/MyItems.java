package com.example.rent;

public class MyItems {
    private final String fullName, mobile, email;


    public MyItems(String fullName, String mobile, String email){
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public String getMobile(){
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
