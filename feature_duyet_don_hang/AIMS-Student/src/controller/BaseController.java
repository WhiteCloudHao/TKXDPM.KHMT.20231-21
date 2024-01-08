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
    
    /**
     * The method checks whether the Media in Cart, if it were in, we will return the CartMedia else return null
     * @param media
     * @return CartMedia or null
     */
	// functional cohesion
	// Phương thức checkMediaInCart(Media media) làm việc trực tiếp với lớp Cart, kiểm tra xem Media có trong Cart hay không và trả về CartMedia tương ứng 
    public CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart
     * @return List[CartMedia]
     */
    // functional cohesion
    // Phương thức getListCartMedia() làm việc trực tiếp với lớp Cart, lấy danh sách các mục trong Cart.
    public List getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}
