package subsystem.vnpay;

import common.exception.UnrecognizedException;
import entity.payment.PaymentTransaction;
import utils.API;

import java.io.IOException;
import java.util.Map;

public class VNPayBoundary {

	public String generatePayUrl(int amount, String content) throws IOException {
		RequestVNPay payRequestVNPay = new RequestVNPay(amount, content);
		return payRequestVNPay.generateURL();
	}

	public PaymentTransaction getPaymentTransaction(Map<String,String> response) {
		ResponseVNPay payResponseVNPay = new ResponseVNPay(response);
		return payResponseVNPay.getPaymentTransaction();
	}

}
