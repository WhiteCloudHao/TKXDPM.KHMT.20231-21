package views.screen.admin;

import controller.AdminController;
import entity.media.CD;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static utils.Utils.showSuccessAlert;

public class AddCDHandler extends BaseScreenHandler {
    @FXML
    private TextField artistField;
    @FXML
    private TextField recordLabelField;
    @FXML
    private TextField musicCategoryField;
    @FXML
    private DatePicker releasedDateField;
    private int id;

    @FXML
    private void handleSubmit() throws SQLException {
        LocalDate publishDate = releasedDateField.getValue();
        Date publishDateAsDate = null;
        if (publishDate != null) {
            publishDateAsDate = Date.from(publishDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        CD cd = new CD();
        cd.setId(id);
        cd.setArtist(artistField.getText());
        cd.setRecordLabel(recordLabelField.getText());
        cd.setMusicType(musicCategoryField.getText());
        cd.setReleasedDate(publishDateAsDate);
        cd.saveCD();
        showSuccessAlert("Thêm CD thành công");
        AdminHandler adminHandler;
        try {
            adminHandler = new AdminHandler(this.stage, Configs.ADMIN_PATH);
            adminHandler.setHomeScreenHandler(new HomeScreenHandler(this.stage, Configs.HOME_PATH));
            adminHandler.setBController(new AdminController());
            adminHandler.requestToAdmin(this);
        } catch (IOException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public AddCDHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void requestToAddMedia(BaseScreenHandler prevScreen,int mediaId) throws SQLException {
        id = mediaId;
        setPreviousScreen(prevScreen);
        setScreenTitle("Thêm sản phẩm");
        show();
    }
}
