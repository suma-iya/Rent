package com.example.rent;

public class MyItems {
    private final String fullName, mobile, email, electricityBill, rent, total;


    public MyItems(String fullName, String mobile, String email, String electricityBill, String rent, String total){
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.electricityBill = electricityBill;
        this.rent = rent;
        this.total = total;
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

    public String getElectricityBill() {
        return electricityBill;
    }
    public String getRent() {
        return rent;
    }
    public String getTotal() {
        return total;
    }
}
