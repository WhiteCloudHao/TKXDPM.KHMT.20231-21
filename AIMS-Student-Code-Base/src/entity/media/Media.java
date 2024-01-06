package entity.media;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import utils.Utils;

/**
 * The general media class, for another media it can be done by inheriting this class
 *
 * @author nguyenlm
 */
public class Media {

    private static final Logger LOGGER = Utils.getLogger(Media.class.getName());

    protected static Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;

    private static Media mediaInstance;

    public int allowedUpdate = 30;
    public int allowedDelete = 30;

    public static Media getMedia() throws SQLException {
        if (mediaInstance == null) mediaInstance = new Media();
        return mediaInstance;
    }

    public void decreaseUpdate() {
        this.allowedUpdate--;
    }

    public void decreaseDelete(int quantity) {
        this.allowedDelete = this.allowedDelete - quantity;
    }


    protected boolean isSupportedPlaceRushOrder = new Random().nextBoolean();

    protected double weigh = (new Random().nextDouble() * 0.9) + 0.1;

    public Media() throws SQLException {
        stm = AIMSDB.getConnection().createStatement();
    }

    public Media(int id, String title, String category, int price, int quantity, String type) throws SQLException {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public void saveMedia() throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO Media (title, category, price, quantity, type, imageUrl, value) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, this.title);
            preparedStatement.setString(2, this.category);
            preparedStatement.setInt(3, this.price);
            preparedStatement.setInt(4, this.quantity);
            preparedStatement.setString(5, this.type);
            preparedStatement.setString(6, this.imageURL);
            preparedStatement.setInt(7, this.value);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public int getQuantity() throws SQLException {
        int updated_quantity = getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }

    public void delete() throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;

        // Kiểm tra xem có join bảng "Book" hay không
        boolean hasJoin = checkJoinWithBook();

        if (hasJoin) {
            String sql = "DELETE FROM Media " +
                    "WHERE id IN (SELECT m.id FROM Media m JOIN Book b ON m.id = b.id)";
            preparedStatement = connection.prepareStatement(sql);
        } else {
            String sql = "DELETE FROM Media WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
        }

        preparedStatement.executeUpdate();
    }

    public void updatePrice(int price) throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "UPDATE Media SET price = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, price);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    private boolean checkJoinWithBook() throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean hasJoin = false;

        try {
            String sql = "SELECT 1 FROM Media m JOIN Book b ON m.id = b.id LIMIT 1";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            hasJoin = resultSet.next();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return hasJoin;
    }

    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM Media ;";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {

            return new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
        }
        return null;
    }

    public ArrayList<Media> getAllMedia() throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"))
                    .setValue(res.getInt("value"));
            medium.add(media);
        }
        return medium;
    }


    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        if (value instanceof String) {
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " "
                + field + "=" + value + " "
                + "where id=" + id + ";");
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    private Media setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public int getValue() {
        return this.value;
    }

    public int getQuantityInit() {
        return this.quantity;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Media setMediaURL(String url) {
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setValue(int value) {
        this.value = value;
        return this;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    public boolean getIsSupportedPlaceRushOrder() {
        return this.isSupportedPlaceRushOrder;
    }

    public void setIsSupportedPlaceRushOrder(boolean b) {
        this.isSupportedPlaceRushOrder = b;
    }

    public double getWeigh() {
        return this.weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", category='" + category + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + type + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }

}