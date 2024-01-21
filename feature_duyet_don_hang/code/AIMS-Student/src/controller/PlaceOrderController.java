package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.shipping.DeliveryInfo;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder(Invoice invoice, DeliveryInfo deliveryInfor) throws SQLException{
        Order order = new Order(deliveryInfor, invoice);
        return order;
    }

//    public Invoice createInvoice() {
//        return new Invoice(Cart.getCart().getListMedia());
//    }

    public Order processDeliveryInfo(Order order) throws InterruptedException, IOException{
        int shippingFees = order.calculateShippingFees();
        order.getInvoice().setShippingFees(shippingFees);
        return order;
    }
    public String validateRushShipping(Order order){
        if(order.getDeliveryInfo().getProvince().isEmpty()) return "EMPTY";
        if(!order.getDeliveryInfo().validateAddressPlaceRushOrder()) return "ADDRESS_NOT_SUPPORT";
        if(order.getInvoice().getNumberOfRushShippingProduct() == 0) return "PRODUCT_NOT_SUPPORT";
        return "VALID";
    }
    
    /**
   * The method validates the info
   * @param deliveryInfo
   * @throws InterruptedException
   * @throws IOException
   */
    public String validateDeliveryInfo(DeliveryInfo deliveryInfo) throws InterruptedException, IOException{
    	return deliveryInfo.validateDeliveryInfo();
    }

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
//    public int calculateShippingFee(Order order){
//        return order.calculateShippingFees();
//    }

}
