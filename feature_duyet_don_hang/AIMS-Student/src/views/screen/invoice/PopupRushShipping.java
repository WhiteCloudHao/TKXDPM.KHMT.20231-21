package views.screen.invoice;

import common.exception.ProcessInvoiceException;
import controller.PlaceOrderController;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Configs;
import views.screen.BaseScreenHandler;

import java.io.IOException;
import java.sql.SQLException;

public class PopupRushShipping extends BaseScreenHandler {

    @FXML
    private VBox vboxItems;

    @FXML
    private Button btnConfirm;

    @FXML
    private Button btnclose;

    @FXML
    private Label oldpr;

    @FXML
    private Label newpr;

    private Order order;
    private PlaceOrderController placeOrderController;
    private OnClickConfirm onClickConfirm;

    public PopupRushShipping(Stage stage, Order order, PlaceOrderController controller) throws IOException {
        super(stage, Configs.POPUP_RUSH_SHIPPING_PATH);
        this.placeOrderController = controller;
        this.order = order;
        this.stage.initStyle(StageStyle.UNDECORATED);
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
        oldpr.setText(this.order.calculateShippingFees() + "");
        this.order.getDeliveryInfo().setRushShipping(true);
        newpr.setText(this.order.calculateShippingFees() + "");
        this.order.getDeliveryInfo().setRushShipping(false);

        btnclose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                close();
            }
        });

        btnConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    handleConfirm();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void close(){
        stage.close();
    }

    void handleConfirm() throws IOException, InterruptedException {
        this.order.getDeliveryInfo().setRushShipping(true);
        this.order = placeOrderController.processDeliveryInfo(order);
        onClickConfirm.onClick();
        stage.close();
    }
    public void setOnClickConfirm(OnClickConfirm onClickConfirm){
        this.onClickConfirm = onClickConfirm;
    }
}
