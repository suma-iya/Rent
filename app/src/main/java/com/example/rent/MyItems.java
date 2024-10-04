package com.example.rent;

public class MyItems {
    private String fullName;
    private String mobile;
    private String email;
    private String electricityBill;
    private String rent;
    private String total;
    private  String password;


    public MyItems(String fullName, String mobile, String email, String electricityBill, String rent, String total, String password){
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.electricityBill = electricityBill;
        this.rent = rent;
        this.total = total;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setFullName(String updatedName) {
        // Update the name
        fullName = updatedName;
    }

    public void setEmail(String updatedEmail) {
        email = updatedEmail;
    }

    public void setElectricityBill(String updatedElectricityBill) {
        // Update the electricity bill
        electricityBill = updatedElectricityBill;
    }

    public void setRent(String updatedRent) {
        // Update the rent
        rent = updatedRent;
    }

    public void setTotal(String updatedTotal) {
        // Update the total
        total = updatedTotal;
    }
    public void setPassword(String updatedPassword) {
        // Update the password
        password = updatedPassword;
    }
    public void setMobile(String updatedMobile) {
        // Update the mobile
        mobile = updatedMobile;
    }
}
