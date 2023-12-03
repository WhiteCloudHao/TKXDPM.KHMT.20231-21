package controller;

import entity.cart.CartMedia;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

// low coupling (data coupling) because only use method of other class
public class PlaceRushOrderController extends PlaceOrderController{

    public boolean checkSupportedPlaceRushOrder(Media media) {
        return media.getIsSupportedPlaceRushOrder();
    }
}
