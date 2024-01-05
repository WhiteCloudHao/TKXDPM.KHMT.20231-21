package controller;

//high coupling (control coupling) 
//BaseController trực tiếp gọi các phương thức của lớp Cart và sử dụng lớp CartMedia

//Violate Liskov Substitution Principle because not every subclass needs to checkMediaCart and getListCartMed

import java.util.List;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

/**
 * This class is the base controller for our AIMS project
 * @author nguyenlm
 */
public class BaseController {
    

    public CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }



    public List getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}
