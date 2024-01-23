package views.screen.admin;

import controller.AdminController;
import entity.media.Media;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static utils.Utils.showSuccessAlert;

public class DeleteMediaHandler extends BaseScreenHandler {
    private final ArrayList<Media> dataMedia;
    @FXML
    private TableView<Media> tableView;
    @FXML
    private TableColumn<Media, Integer> sttColumn;
    @FXML
    private TableColumn<Media, String> titleColumn;
    @FXML
    private TableColumn<Media, String> typeColumn;
    @FXML
    private TableColumn<Media, String> categoryColumn;
    @FXML
    private TableColumn<Media, Integer> quantityColumn;
    @FXML
    private TableColumn<Media, Integer> priceColumn;
    @FXML
    private TableColumn<Media, Integer> valueColumn;
    @FXML
    private TableColumn<Media, ImageView> imageColumn;
    @FXML
    private TableColumn<Media, Integer> checkboxColumn;
    @FXML
    private Button deleteButton;

    private final ArrayList<Media> selectedMedia = new ArrayList<>();

    public DeleteMediaHandler(Stage stage, String screenPath) throws IOException, SQLException {
        super(stage, screenPath);
        dataMedia = new Media().getAllMedia();
        initializeTableColumns();
        populateTable();
    }

    private void initializeTableColumns() {
        sttColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        priceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrice()).asObject());
        valueColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getValue()).asObject());
        imageColumn.setCellValueFactory(cellData -> {
            String imageURL = cellData.getValue().getImageURL();
            File file = new File(imageURL);
            ImageView imageView = new ImageView(new Image(file.toURI().toString()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            return new SimpleObjectProperty<>(imageView);
        });
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantityInit()).asObject());
        checkboxColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        checkboxColumn.setCellFactory(col -> new TableCell<Media, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setOnAction(event -> {
                        Media media = getTableView().getItems().get(getIndex());
                        boolean isChecked = checkBox.isSelected();
                        handleCheckBoxAction(media, isChecked);
                    });

                    setGraphic(checkBox);
                }
            }
        });

        deleteButton.setOnAction(event -> {
            selectedMedia.forEach(media -> {
                try {
                    media.delete();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            showSuccessAlert("Delete media successfully");
            AdminHandler adminHandler;
            try {
                adminHandler = new AdminHandler(this.stage, Configs.ADMIN_PATH);
                adminHandler.setHomeScreenHandler(new HomeScreenHandler(this.stage, Configs.HOME_PATH));
                adminHandler.setBController(new AdminController());
                adminHandler.requestToAdmin(this);
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });


    }

    private void handleCheckBoxAction(Media media, boolean isChecked) {
        if (isChecked) {
            selectedMedia.add(media);
        } else {
            selectedMedia.remove(media);
        }
    }

    public void requestToDeleteMedia(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        setScreenTitle("Delete Media");
        show();
    }

    private void populateTable() {
        ObservableList<Media> observableData = FXCollections.observableArrayList(dataMedia);
        tableView.setItems(observableData);
    }

}
