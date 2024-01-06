package views.screen.admin;

import controller.AdminController;
import entity.media.DVD;
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

public class AddDVDHandler extends BaseScreenHandler {
    @FXML
    private TextField discTypeField;
    @FXML
    private TextField directorField;
    @FXML
    private TextField runtimeField;
    @FXML
    private TextField studioField;
    @FXML
    private TextField subtitlesField;
    @FXML
    private DatePicker releasedDateField;
    @FXML
    private TextField filmTypeField;
    private int id;
    @FXML
    private void handleSubmit() throws SQLException {
        LocalDate publishDate = releasedDateField.getValue();
        Date publishDateAsDate = null;
        if (publishDate != null) {
            publishDateAsDate = Date.from(publishDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        DVD dvd = new DVD();
        dvd.setId(id);
        dvd.setDiscType(discTypeField.getText());
        dvd.setDirector(directorField.getText());
        dvd.setRuntime(Integer.parseInt(runtimeField.getText()));
        dvd.setStudio(studioField.getText());
        dvd.setSubtitles(subtitlesField.getText());
        dvd.setReleasedDate(publishDateAsDate);
        dvd.setFilmType(filmTypeField.getText());
        dvd.saveDVD();
        showSuccessAlert("Thêm DVD thành công");
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
    public AddDVDHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void requestToAddMedia(BaseScreenHandler prevScreen,int mediaId) throws SQLException {
        id = mediaId;
        setPreviousScreen(prevScreen);
        setScreenTitle("Thêm sản phẩm");
        show();
    }
}
