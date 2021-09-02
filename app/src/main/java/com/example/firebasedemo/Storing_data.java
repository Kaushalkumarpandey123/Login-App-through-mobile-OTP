package com.example.firebasedemo;

public class Storing_data {
    String  username,address,phonenumber, email,dob,password;
    public static String newusername;

    public static String getNewusername() {
        return newusername;
    }

    public static void setNewusername(String newusername) {
        Storing_data.newusername = newusername;
    }

    public Storing_data() {
    }

    public Storing_data(String username, String address, String phonenumber, String email, String dob, String password) {
        this.username = username;
        this.address = address;
        this.phonenumber = phonenumber;
        this.email = email;
        this.dob = dob;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
