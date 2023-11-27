package com.example.aims_project2.entity.delivery;

import com.example.aims_project2.entity.cart.Cart;
import com.example.aims_project2.entity.cart.CartMedia;
import com.example.aims_project2.entity.media.Media;

public class Delivery {
    
    private int id;
    private String name;
    private String email;
    private String address;
    private String phone;

    public Delivery(int id, String name, String email, String address, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
    
    // override toString method
    @Override
    public String toString() {
        return "{" +
            "  username='" + name + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            ", phone='" + phone + "'" +
            "}";
    }

    // getter and setter
    public String getName() {
        return this.name;
    }

    public void setusername(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
