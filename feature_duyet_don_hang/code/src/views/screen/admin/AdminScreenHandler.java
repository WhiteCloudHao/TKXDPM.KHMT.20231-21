package views.screen.admin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;

public class AdminScreenHandler extends BaseScreenHandler{
	private static Logger LOGGER = Utils.getLogger(AdminScreenHandler.class.getName());

    @FXML private AnchorPane SceneAnchorPane;
    @FXML private AnchorPane contentAdminAnchorPane;

    @FXML private Button overviewBtn;
    @FXML private Button confirmOrderBtn;
    @FXML private Button pendingOrderBtn;
    @FXML private Button logoutBtn;

    @FXML private Label currentAdminLabel;

    @FXML private ImageView orderAlert;
    @FXML private ImageView pendingAlert;
    @FXML private ImageView aimsImage;

    @FXML private HBox overviewBox;
    @FXML private HBox acceptOrderBox;
    @FXML private HBox pendingOrderBox;

    private BaseScreenHandler previousScreen;

    public AdminScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);

		// fix relative image path caused by fxml
		File file = new File("assets/images/Logo.png");
		Image im = new Image(file.toURI().toString());
		aimsImage.setImage(im);

		// on mouse clicked, we back to home
		aimsImage.setOnMouseClicked(e -> {
			homeScreenHandler.setScreenTitle("Home Screen");
			homeScreenHandler.show();
		});

		// on mouse clicked, we back to previous screen
		logoutBtn.setOnMouseClicked(e -> {
			LOGGER.info("Back button clicked");
			if (previousScreen != null) {
				previousScreen.setScreenTitle(previousScreen.getPreviousScreenTitle());
                previousScreen.show(); // Quay lại trang trước đó
            }
		});

		// on mouse clicked, navigate to confirm order
		confirmOrderBtn.setOnMouseClicked(e -> {
			LOGGER.info("Confirm Order button clicked");

            try {
                // Load trang confirm_order.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Configs.CONFIRM_ORDER_SCREEN_PATH));
                Parent confirmOrderParent = loader.load();
                AdminConfirmOrderScreenHandler adminConfirmScreen = loader.getController();
                adminConfirmScreen.initView();

                contentAdminAnchorPane.getChildren().clear();
                contentAdminAnchorPane.getChildren().add(confirmOrderParent);

                // Lấy controller của trang confirm_order.fxml (nếu cần)
//                ConfirmOrderController confirmOrderController = loader.getController();
//                Stage stage1 = new Stage();

//                AdminConfirmOrderScreenHandler adminConfirmOrderScreenHandler = new AdminConfirmOrderScreenHandler(new Stage(), Configs.CONFIRM_ORDER_SCREEN_PATH);
//                adminConfirmOrderScreenHandler.show();

            } catch (IOException | SQLException ex) {
                LOGGER.severe("Error loading confirm_order.fxml");
                ex.printStackTrace();
            }
        });

	}

    public void requestToViewAdminScreen(BaseScreenHandler prevScreen) {
		setPreviousScreen(prevScreen);
		show();
	}

    public void setPreviousScreen(BaseScreenHandler previousScreen) {
        this.previousScreen = previousScreen;
    }

}
