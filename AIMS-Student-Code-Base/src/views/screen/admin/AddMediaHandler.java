package views.screen.admin;

import controller.AdminController;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Configs;
import utils.ImageUtils;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import static utils.Utils.showSuccessAlert;

public class AddMediaHandler extends BaseScreenHandler {
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField valueField;
    @FXML
    private Button chooseImage;
    @FXML
    private ImageView selectedImageView;
    @FXML
    private Button submit;

    private File selectedFile;

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        Stage stage = (Stage) chooseImage.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("Selected Image: " + selectedFile.getAbsolutePath());
            Image selectedImage = new Image(selectedFile.toURI().toString());
            selectedImageView.setImage(selectedImage);
            chooseImage.setVisible(false);
            selectedImageView.setVisible(true);
        }
    }

    @FXML
    private void handleSubmit() {
        String path = "";
        try {
            if (Objects.equals(typeComboBox.getValue(), "Book")) {
                path = "assets/images/book";
            } else if (Objects.equals(typeComboBox.getValue(), "CD")) {
                path = "assets/images/cd";
            } else if (Objects.equals(typeComboBox.getValue(), "DVD")) {
                path = "assets/images/dvd";
            }
            String imagePath = ImageUtils.saveImage(selectedFile, path);
            Media media = new Media();
            media.setTitle(titleField.getText());
            media.setType(typeComboBox.getValue());
            media.setCategory(categoryField.getText());
            media.setPrice(Integer.parseInt(priceField.getText()));
            media.setQuantity(Integer.parseInt(quantityField.getText()));
            media.setValue(Integer.parseInt(valueField.getText()));
            media.setMediaURL(imagePath);
            media.saveMedia();
            int id = media.getId();
            showSuccessAlert("Thêm sản phẩm thành công");
            if (Objects.equals(typeComboBox.getValue(), "book")) {
                AddBookHandler addBookHandler = new AddBookHandler(this.stage, Configs.ADD_BOOK_PATH);
                addBookHandler.requestToAddMedia(this, id);
            } else if (Objects.equals(typeComboBox.getValue(), "cd")) {
                AddCDHandler addCDHandler = new AddCDHandler(this.stage, Configs.ADD_CD_PATH);
                addCDHandler.requestToAddMedia(this, id);
            } else if (Objects.equals(typeComboBox.getValue(), "dvd")) {
                AddDVDHandler addDVDHandler = new AddDVDHandler(this.stage, Configs.ADD_DVD_PATH);
                addDVDHandler.requestToAddMedia(this, id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AddMediaHandler(Stage stage, String screenPath) throws IOException {
        super(stage, screenPath);
    }

    public void requestToAddMedia(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        setScreenTitle("Thêm sản phẩm");
        show();
    }

}
