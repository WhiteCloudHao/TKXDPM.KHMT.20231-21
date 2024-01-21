package views.screen.invoice;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import controller.PlaceOrderController;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.shipping.DeliveryInfo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.payment.PaymentScreenHandler;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class InvoiceScreenHandler extends BaseScreenHandler implements OnClickConfirm {

	private static final Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

	@FXML private Label pageTitle;

	@FXML private TextField name;

	@FXML private TextField phone;

	@FXML private ComboBox<String> province;

	@FXML private TextField address;

	@FXML private TextField instructions;

	@FXML private RadioButton chooseRushShip;
	
	@FXML private RadioButton chooseNormalShip;

	@FXML private TextField time;

	@FXML private TextField rushInstruction;

	@FXML private TextField email;

	@FXML private Label VAT;

	@FXML private Label noVAT;

	@FXML private Label shippingFees;

	@FXML private Label total;

	@FXML private Label lbtime;

	@FXML private Label lbrushInstruction;

	@FXML private VBox vboxItems;
	
	@FXML private Button cfInvoice;

	private Order order;
	private PopupRushShipping popupRushShipping;

	public PlaceOrderController getBController() {
		return (PlaceOrderController) super.getBController();
	}

	public InvoiceScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
		this.setBController(new PlaceOrderController());
		this.popupRushShipping = new PopupRushShipping(new Stage(), order, getBController());
		this.popupRushShipping.setOnClickConfirm(this);
		this.province.getItems().addAll(Configs.PROVINCES);

		setInvoiceInfo();
		lbtime.setVisible(false);
		lbrushInstruction.setVisible(false);
		time.setVisible(false);
		rushInstruction.setVisible(false);
		chooseNormalShip.setSelected(false);
		
		chooseNormalShip.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) {
					chooseRushShip.setSelected(false);
				} else {
					lbtime.setVisible(false);
					lbrushInstruction.setVisible(false);
					time.setVisible(false);
					rushInstruction.setVisible(false);
				}
			}
		});
		
		chooseRushShip.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) {
					String status = getBController().validateRushShipping(order);
					chooseNormalShip.setSelected(false);
					if(status.equals("EMPTY")){
						try {
							PopupScreen.error("Empty province");
							chooseRushShip.setSelected(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else if(status.equals("ADDRESS_NOT_SUPPORT")){
						try {
							PopupScreen.error("Address not support");
							chooseRushShip.setSelected(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else if(status.equals("PRODUCT_NOT_SUPPORT")){
						try {
							PopupScreen.error("Product not support");
							chooseRushShip.setSelected(false);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else{
						lbtime.setVisible(true);
						lbrushInstruction.setVisible(true);
						time.setVisible(true);
						rushInstruction.setVisible(true);
						popupRushShipping.show();
					}
				} else {
					lbtime.setVisible(false);
					lbrushInstruction.setVisible(false);
					time.setVisible(false);
					rushInstruction.setVisible(false);
				}
			}
		});

		province.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			try {
				updateShippingFees(newValue);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});


	}

	private void updateShippingFees(String province) throws IOException, InterruptedException {
		this.order.getDeliveryInfo().setProvince(province);
		this.order = getBController().processDeliveryInfo(order);
		shippingFees.setText(Utils.getCurrencyFormat(order.getInvoice().getShippingFees()));
		total.setText(Utils.getCurrencyFormat(order.getInvoice().calculateTotalPrice()));
	}

	private void setInvoiceInfo(){
		VAT.setText(Utils.getCurrencyFormat(order.getInvoice().getTotalProductIncludeVAT()));
		noVAT.setText(Utils.getCurrencyFormat(order.getInvoice().getTotalProductNoVAT()));
		shippingFees.setText(Utils.getCurrencyFormat(order.getInvoice().getShippingFees()));
		total.setText(Utils.getCurrencyFormat(order.getInvoice().getTotalPrice()));
		order.setInvoice(order.getInvoice());
		order.getInvoice().getListOrderMedia().forEach(orderMedia -> {
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

	private void setDeliveryInfo() throws IOException, InterruptedException {
		DeliveryInfo deliveryInfo = new DeliveryInfo();

		deliveryInfo.setEmail(email.getText());
		deliveryInfo.setName(name.getText());
		deliveryInfo.setProvince(province.getValue());
		deliveryInfo.setPhoneNumber(phone.getText());
		deliveryInfo.setShippingAddress(address.getText());
		deliveryInfo.setShippingInstruction(instructions.getText());
		deliveryInfo.setRushShippingInstruction(rushInstruction.getText());
		deliveryInfo.setRushShippingTime(time.getText());

		String status = getBController().validateDeliveryInfo(deliveryInfo);

		if(status.equals("INVALID_EMAIL")) {
			try {
				PopupScreen.error("Email không hợp lệ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(status.equals("INVALID_NAME")) {
			try {
				PopupScreen.error("Tên không hợp lệ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(status.equals("INVALID_PHONENUMBER")) {
			try {
				PopupScreen.error("Số điện thoại không hợp lệ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			order.setDeliveryInfo(deliveryInfo);

			showPaymentScreen();
		}


	}
	@FXML
	void confirmInvoice(MouseEvent event) throws IOException, InterruptedException {
		setDeliveryInfo();
		setInvoiceInfo();

//		BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, order);
//		paymentScreen.setBController(new PaymentController());
//		paymentScreen.setPreviousScreen(this);
//		paymentScreen.setHomeScreenHandler(homeScreenHandler);
//		paymentScreen.setScreenTitle("Payment Screen");
//		paymentScreen.show();
//		LOGGER.info("Confirmed invoice");
	}

	private void showPaymentScreen() throws IOException {
		BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, order);
		paymentScreen.setBController(new PaymentController());
		paymentScreen.setPreviousScreen(this);
		paymentScreen.setHomeScreenHandler(homeScreenHandler);
		paymentScreen.setScreenTitle("Payment Screen");
		paymentScreen.show();
		LOGGER.info("Confirmed invoice");
	}

	@Override
	public void onClick() {
		try{
			updateShippingFees(province.getValue());
		} catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
