package views.screen.admin;

import controller.AdminConfirmOrderController;
import entity.order.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Utils;
import views.screen.popup.PopupScreen;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AdminConfirmOrderScreenHandler{
	private static Logger LOGGER = Utils.getLogger(AdminConfirmOrderScreenHandler.class.getName());

    @FXML private TableView<Order> tableview;
    @FXML private TableColumn<Order, Integer> col_num;
    @FXML private TableColumn<Order, String> col_customer;
    @FXML private TableColumn<Order, String> col_instructions;
    @FXML private TableColumn<Order, String> col_province;
    @FXML private TableColumn<Order, String> col_status;
    @FXML private TableColumn<Order, String> col_phone;

    @FXML private Button cancelOrderBtn;
    @FXML private Button acceptOrderBtn;

    private Order selectedOrder;

    private AdminConfirmOrderController adminConfirmOrderController = new AdminConfirmOrderController();

    public void initView() throws IOException, SQLException {
//        this.orderMediaDetails = orderMediaDetails;
        initializeTableView();

        tableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Lấy đơn hàng được chọn
                selectedOrder = newSelection;
            }
        });

        acceptOrderBtn.setOnMouseClicked(mouseEvent -> {
            if (selectedOrder != null) {
                // Lấy id của đơn hàng được chọn và cập nhật trạng thái
                int orderId = selectedOrder.getId();
                try {
                    Order.updateOrderStatus(orderId, "ĐÃ DUYỆT");
                    ObservableList<Order> orderData = FXCollections.observableArrayList(Order.getAllOrders());
                    tableview.setItems(orderData);
                    PopupScreen.success("Đã duyệt đơn hàng thành công");
                    LOGGER.info("Order accepted");
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cancelOrderBtn.setOnMouseClicked(mouseEvent -> {
            if (selectedOrder != null) {
                // Lấy id của đơn hàng được chọn và cập nhật trạng thái
                int orderId = selectedOrder.getId();
                try {
                    Order.updateOrderStatus(orderId, "ĐÃ TỪ CHỐI");
                    ObservableList<Order> orderData = FXCollections.observableArrayList(Order.getAllOrders());
                    tableview.setItems(orderData);
                    PopupScreen.success("Từ chối đơn hàng thành công");
                    LOGGER.info("Order canceled");
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void initializeTableView() throws SQLException {
        // Khởi tạo các cột
        col_num.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_customer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryInfo().getEmail()));
        col_province.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryInfo().getProvince()));
        col_instructions.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryInfo().getShippingInstruction()));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_phone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeliveryInfo().getPhoneNumber()));

        // Lấy dữ liệu từ bảng Order và cập nhật TableView
//        ObservableList<Order> orderData = FXCollections.observableArrayList(Order.getAllOrders());
        ObservableList<Order> orderData = adminConfirmOrderController.getAllOrders();
        tableview.setItems(orderData);
    }

}
