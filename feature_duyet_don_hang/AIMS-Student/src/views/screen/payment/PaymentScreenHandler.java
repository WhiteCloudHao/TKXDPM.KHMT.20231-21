package views.screen.payment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import controller.PaymentController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import subsystem.vnpay.ConfigVNPay;
import utils.Configs;
import views.screen.BaseScreenHandler;

public class PaymentScreenHandler extends BaseScreenHandler {

	private Invoice invoice;
	@FXML
	private Label pageTitle;
	@FXML
	private VBox vBox;

	private Order order;

	public PaymentScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.invoice = order.getInvoice();
		this.order = order;
		this.setBController(new PaymentController());
		WebView paymentView = new WebView();
		WebEngine webEngine = paymentView.getEngine();
		webEngine.load(((PaymentController) getBController()).generateURL(invoice.calculateTotalPrice(), "Thanh toán hóa đơn"));
		webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
			handleUrlChanged(newValue);
		});
		vBox.getChildren().clear();
		vBox.getChildren().add(paymentView);
	}

	// Hàm chuyển đổi query string thành Map
	private static Map<String, String> parseQueryString(String query) {
		Map<String, String> params = new HashMap<>();
		if (query != null && !query.isEmpty()) {
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				if (keyValue.length == 2) {
					params.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return params;
	}

	private void handleUrlChanged(String newValue) {
		if (newValue.contains(ConfigVNPay.vnp_ReturnUrl)) {
			try {
				URI uri = new URI(newValue);
				String query = uri.getQuery();
				System.out.println(query);

				Map<String, String> params = parseQueryString(query);

				confirmToPayOrder(params);
				Order.saveNewOrder(order);

			} catch (URISyntaxException | IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
	}

	/**
	 * @throws IOException
	 */
	void confirmToPayOrder(Map<String, String> res) throws IOException {

		PaymentController controller = (PaymentController) getBController();
		Map<String, String> response = controller.makePayment(res);

		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH,
				response.get("RESULT"), response.get("MESSAGE"));
		controller.emptyCart();
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();

	}

}