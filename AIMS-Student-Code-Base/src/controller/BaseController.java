package controller;

import java.util.List;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

/**
 * This class is the base controller for our AIMS project
 * @author nguyenlm
 */
// low coupling (data coupling) because only use method of other class
// coincedental cohensive because both of checkMediaInCart and getListCartMedia are not used by all other controller
//  Violate Liskov Substitution Principle because Not every subclass needs to checkMediaInCart and getListCartMedia
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
