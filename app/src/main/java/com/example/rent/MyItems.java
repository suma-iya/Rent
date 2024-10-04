package com.example.rent;

public class MyItems {
    private String fullName;
    private final String mobile;
    private String email;
    private String electricityBill;
    private String rent;
    private String total;


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
}
