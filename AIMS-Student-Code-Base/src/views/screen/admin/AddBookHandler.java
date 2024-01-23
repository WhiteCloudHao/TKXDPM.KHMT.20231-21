package views.screen.admin;

import controller.AdminController;
import entity.media.Book;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

public class AddBookHandler extends BaseScreenHandler {
    @FXML
    private TextField authorField;
    @FXML
    private ComboBox<String> coverTypeComboBox;
    @FXML
    private TextField publisherField;
    @FXML
    private DatePicker publishDatePicker;
    @FXML
    private TextField numOfPagesField;
    @FXML
    private TextField languageField;
    @FXML
    private TextField bookCategoryField;

    private int id;

    @FXML
    private void handleSubmit() throws SQLException {
        LocalDate publishDate = publishDatePicker.getValue();
        Date publishDateAsDate = null;
        if (publishDate != null) {
            publishDateAsDate = Date.from(publishDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        Book book = new Book();
        book.setId(id);
        book.setAuthor(authorField.getText());
        book.setCoverType(coverTypeComboBox.getValue());
        book.setPublisher(publisherField.getText());
        book.setPublishDate(publishDateAsDate);
        book.setNumOfPages(Integer.parseInt(numOfPagesField.getText()));
        book.setLanguage(languageField.getText());
        book.setBookCategory(bookCategoryField.getText());
        book.saveBook();
        showSuccessAlert("Thêm sách thành công");
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
    public AddBookHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void requestToAddMedia(BaseScreenHandler prevScreen,int mediaId) throws SQLException {
        id = mediaId;
        setPreviousScreen(prevScreen);
        setScreenTitle("Thêm sản phẩm");
        show();
    }
}
