package controller;

import entity.order.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AdminConfirmOrderController extends BaseController {
	private static Logger LOGGER = utils.Utils.getLogger(AdminConfirmOrderController.class.getName());

	public ObservableList<Order> getAllOrders() throws SQLException {
		return FXCollections.observableArrayList(Order.getAllOrders());
	}
}

