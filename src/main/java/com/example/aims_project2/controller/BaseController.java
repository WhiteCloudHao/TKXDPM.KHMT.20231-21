package com.example.aims_project2.controller;

import java.util.List;

import com.example.aims_project2.entity.cart.Cart;
import com.example.aims_project2.entity.cart.CartMedia;
import com.example.aims_project2.entity.media.Media;

/**
 * This class is the base controller for our AIMS project
 * @author nguyenlm
 */
public class BaseController {
    
    /**
     * The method checks whether the Media in Cart, if it were in, we will return the CartMedia else return null
     * @param media
     * @return CartMedia or null
     */
    public CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart
     * @return List[CartMedia]
     */
    public List getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}
