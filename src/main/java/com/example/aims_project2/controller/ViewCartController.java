package com.example.aims_project2.controller;

import java.sql.SQLException;
import java.util.List;

import com.example.aims_project2.entity.media.Media;
import com.example.aims_project2.entity.cart.Cart;
import com.example.aims_project2.entity.cart.CartMedia;

/**
 * This class controls the flow of events when users view the Cart
 * @author nguyenlm
 */
public class ViewCartController extends BaseController{
    
    /**
     * This method checks the available products in Cart
     * @throws SQLException
     */
    public void checkAvailabilityOfProduct() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method calculates the cart subtotal
     * @return subtotal
     */
    public int getCartSubtotal(){
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }

}
