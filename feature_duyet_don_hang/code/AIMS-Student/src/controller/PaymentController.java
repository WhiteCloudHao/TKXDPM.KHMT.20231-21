package controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import entity.payment.PaymentTransaction;
import subsystem.VNPaySubsystemInterface;
import subsystem.VNPaySubsystemService;


/**
 * This {@code PaymentController} class control the flow of the payment process
 * in our AIMS Software.
 * 
 * @author hieud
 *
 */
public class PaymentController extends BaseController {

	/**
	 * Represent the Interbank subsystem
	 */
	private VNPaySubsystemInterface vnPay;


	public Map<String, String> makePayment(Map<String, String> res) {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {

			this.vnPay = new VNPaySubsystemService();
			PaymentTransaction transaction = vnPay.getPaymentTransaction(res);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid the order!");
		} catch (PaymentException | UnrecognizedException ex) {
			result.put("MESSAGE", ex.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String generateURL(int amount, String content) throws IOException {
		this.vnPay = new VNPaySubsystemService();
		return vnPay.generateURL(amount, content);
	}

	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}