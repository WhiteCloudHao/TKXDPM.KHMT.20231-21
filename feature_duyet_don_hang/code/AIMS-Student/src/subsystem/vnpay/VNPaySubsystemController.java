package subsystem.vnpay;

import java.io.IOException;
import java.util.*;

import entity.payment.PaymentTransaction;

public class VNPaySubsystemController {


	private static VNPayBoundary vnPayBoundary = new VNPayBoundary();


	public String generatePayUrl(int amount, String content) throws IOException {
		return vnPayBoundary.generatePayUrl(amount, content);
	}

	public PaymentTransaction getPaymentTransaction(Map<String,String> response) {
		return vnPayBoundary.getPaymentTransaction(response);
	}

}
