package views.screen.admin;

import entity.invoice.Invoice;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static views.screen.home.HomeScreenHandler.LOGGER;

public class AdminHandler extends BaseScreenHandler {

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
    private TableColumn<Media, Integer> invoice_custom;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;


    @FXML
    private ImageView aimsImage;

    public AdminHandler(Stage stage, String screenPath) throws IOException, SQLException {
        super(stage, screenPath);

        dataMedia = new Media().getAllMedia();
        System.out.println(dataMedia.size());
        initializeTableColumns();
        populateTable();
        File file = new File("assets/images/Logo.png");
        Image im = new Image(file.toURI().toString());
        aimsImage.setImage(im);

        aimsImage.setOnMouseClicked(e -> homeScreenHandler.show());
    }

    public void requestToAdmin(BaseScreenHandler prevScreen) throws SQLException {
        setPreviousScreen(prevScreen);
        setScreenTitle("Admin Screen");
        show();
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
        invoice_custom.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        invoice_custom.setCellFactory(col -> new TableCell<Media, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {

                    Button viewButton = createViewButton(getTableRow().getIndex());
                    Button editButton = createEditButton(getTableRow().getIndex());

                    editButton.setOnAction(event -> {
                        Media media = getTableView().getItems().get(getIndex());
                        handleEditButtonAction(media);
                    });

                    viewButton.setOnAction(event -> {
                        Media media = getTableView().getItems().get(getIndex());
                        try {
                            handleViewButtonAction(media);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    HBox buttonContainer = new HBox();
                    buttonContainer.setSpacing(10);
                    buttonContainer.getChildren().addAll(viewButton, editButton);

                    setGraphic(buttonContainer);
                }
            }
        });

        addButton.setOnAction(event -> {
            try {
                AddMediaHandler addMediaHandler = new AddMediaHandler(stage, Configs.ADD_MEDIA_PATH);
                addMediaHandler.requestToAddMedia(this);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        deleteButton.setOnAction(event -> {
            try {
                DeleteMediaHandler deleteMediaHandler = new DeleteMediaHandler(stage, Configs.DELETE_MEDIA_PATH);
                deleteMediaHandler.requestToDeleteMedia(this);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    private Button createEditButton(int index) {
        Button button = new Button("Edit");
        button.setUserData(index);
        return button;
    }

    private Button createViewButton(int index) {
        Button button = new Button("View");
        button.setUserData(index);
        return button;
    }


    private void handleViewButtonAction(Media media) throws SQLException {
        // Tạo một cửa sổ thông báo kiểu Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông tin chi tiết");
        alert.setHeaderText(media.getTitle());

        // Tạo một ImageView để hiển thị hình ảnh
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        String imageURL = media.getImageURL();
        File file = new File(imageURL);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Thông tin media:\n");
        sb.append("Title: ").append(media.getTitle()).append("\n");
        sb.append("Type: ").append(media.getType()).append("\n");
        sb.append("Category: ").append(media.getCategory()).append("\n");
        sb.append("Price: ").append(media.getPrice()).append("\n");
        sb.append("Quantity: ").append(media.getQuantityInit()).append("\n");
        sb.append("Value: ").append(media.getValue()).append("\n");
        if (media.getType().equals("book")) {
            try {
                Book book = Book.getBookById(media.getId());
                System.out.println(book.getAuthor());
                sb.append("Author: ").append(book.getAuthor()).append("\n");
                sb.append("Cover Type: ").append(book.getCoverType()).append("\n");
                sb.append("Publisher: ").append(book.getPublisher()).append("\n");
                sb.append("Publish Date: ").append(book.getPublishDate()).append("\n");
                sb.append("Number of Pages: ").append(book.getNumOfPages()).append("\n");
                sb.append("Language: ").append(book.getLanguage()).append("\n");
                sb.append("Book Category: ").append(book.getBookCategory()).append("\n");
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }
        } else if (media.getType().equals("cd")) {
            try {
                CD cd = CD.getCDById(media.getId());
                sb.append("Artist: ").append(cd.getArtist()).append("\n");
                sb.append("Record Label: ").append(cd.getRecordLabel()).append("\n");
                sb.append("Music Type: ").append(cd.getMusicType()).append("\n");
                sb.append("Released Date: ").append(cd.getReleasedDate()).append("\n");
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }

        } else if (media.getType().equals("dvd")) {
            try {
                DVD dvd = DVD.getDVDById(media.getId());
                sb.append("Disc Type: ").append(dvd.getDiscType()).append("\n");
                sb.append("Director: ").append(dvd.getDirector()).append("\n");
                sb.append("Runtime: ").append(dvd.getRuntime()).append("\n");
                sb.append("Studio: ").append(dvd.getStudio()).append("\n");
                sb.append("Subtitles: ").append(dvd.getSubtitles()).append("\n");
                sb.append("Released Date: ").append(dvd.getReleasedDate()).append("\n");
                sb.append("Film Type: ").append(dvd.getFilmType()).append("\n");
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }
        }

        alert.setContentText(sb.toString());
        alert.getDialogPane().setGraphic(imageView);

        // Hiển thị cửa sổ popup
        alert.showAndWait();
    }

    public void handleEditButtonAction(Media media) {
        Object[] options = { "Confirm", "Cancel" };
        String message = "Title: " + media.getTitle() + "\nPrice: " + media.getPrice();

        String newPriceString = JOptionPane.showInputDialog(null, message, "Edit Media", JOptionPane.INFORMATION_MESSAGE);

        if (newPriceString != null) {
            try {
                int newPrice = Integer.parseInt(newPriceString);
                media.updatePrice(newPrice);
                sleep(1000);
                this.populateTable();
            } catch (NumberFormatException e) {
                // Invalid input for price
                JOptionPane.showMessageDialog(null, "Invalid price input", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                // Handle database error
                JOptionPane.showMessageDialog(null, "Failed to update price", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void populateTable() {
        ObservableList<Media> observableData = FXCollections.observableArrayList(dataMedia);
        tableView.setItems(observableData);
    }


}
