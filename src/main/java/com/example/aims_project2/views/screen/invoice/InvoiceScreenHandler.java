package com.example.aims_project2.views.screen.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import com.example.aims_project2.common.exception.ProcessInvoiceException;
import com.example.aims_project2.controller.PaymentController;
import com.example.aims_project2.entity.invoice.Invoice;
import com.example.aims_project2.entity.order.Order;
import com.example.aims_project2.entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.aims_project2.utils.Configs;
import com.example.aims_project2.utils.Utils;
import com.example.aims_project2.views.screen.BaseScreenHandler;
import com.example.aims_project2.views.screen.payment.PaymentScreenHandler;

public class InvoiceScreenHandler extends BaseScreenHandler {

	private static Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

	@FXML
	private Label pageTitle;

	@FXML
	private Label name;

	@FXML
	private Label phone;

	@FXML
	private Label province;

	@FXML
	private Label address;

	@FXML
	private Label instructions;

	@FXML
	private Label subtotal;

	@FXML
	private Label shippingFees;

	@FXML
	private Label total;

	@FXML
	private VBox vboxItems;

	private Invoice invoice;

	public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		setInvoiceInfo();
	}

	private void setInvoiceInfo(){
		HashMap<String, String> deliveryInfo = invoice.getOrder().getDeliveryInfo();
		name.setText(deliveryInfo.get("name"));
		province.setText(deliveryInfo.get("province"));
		instructions.setText(deliveryInfo.get("instructions"));
		address.setText(deliveryInfo.get("address"));
		subtotal.setText(Utils.getCurrencyFormat(invoice.getOrder().getAmount()));
		shippingFees.setText(Utils.getCurrencyFormat(invoice.getOrder().getShippingFees()));
		int amount = invoice.getOrder().getAmount() + invoice.getOrder().getShippingFees();
		total.setText(Utils.getCurrencyFormat(amount));
		invoice.setAmount(amount);
		invoice.getOrder().getlstOrderMedia().forEach(orderMedia -> {
			try {
				MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
				mis.setOrderMedia((OrderMedia) orderMedia);
				vboxItems.getChildren().add(mis.getContent());
			} catch (IOException | SQLException e) {
				System.err.println("errors: " + e.getMessage());
				throw new ProcessInvoiceException(e.getMessage());
			}
			
		});

	}

	@FXML
	void confirmInvoice(MouseEvent event) throws IOException {
		BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
		paymentScreen.setBController(new PaymentController());
		paymentScreen.setPreviousScreen(this);
		paymentScreen.setHomeScreenHandler(homeScreenHandler);
		paymentScreen.setScreenTitle("Payment Screen");
		paymentScreen.show();
		LOGGER.info("Confirmed invoice");
	}

}
