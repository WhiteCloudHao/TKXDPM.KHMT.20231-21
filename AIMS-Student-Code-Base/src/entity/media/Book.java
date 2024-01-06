package entity.media;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import entity.db.AIMSDB;

//low coupling - data coupling due to getMediaById querying sql data directly
public class Book extends Media {

    String author;
    String coverType;
    String publisher;
    Date publishDate;
    int numOfPages;
    String language;
    String bookCategory;

    public Book() throws SQLException {

    }

    public Book(int id, String title, String category, int price, int quantity, String type, String author,
                String coverType, String publisher, Date publishDate, int numOfPages, String language,
                String bookCategory) throws SQLException {
        super(id, title, category, price, quantity, type);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }


    // getter and setter
    public int getId() {
        return this.id;
    }

    public Book setId(int id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCoverType() {
        return this.coverType;
    }

    public Book setCoverType(String coverType) {
        this.coverType = coverType;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public Book setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public int getNumOfPages() {
        return this.numOfPages;
    }

    public Book setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getBookCategory() {
        return this.bookCategory;
    }

    public Book setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }

    public void saveBook() throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO Book (id,author, coverType, publisher, publishDate, numOfPages, language, bookCategory) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.setString(2, this.author);
            preparedStatement.setString(3, this.coverType);
            preparedStatement.setString(4, this.publisher);
            preparedStatement.setDate(5, new java.sql.Date(this.publishDate.getTime()));
            preparedStatement.setInt(6, this.numOfPages);
            preparedStatement.setString(7, this.language);
            preparedStatement.setString(8, this.bookCategory);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public static Book getBookById(int id) throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Book WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        String title = "";
        String category = "";
        int price = 0;
        int quantity = 0;
        String type = "";
        String author = resultSet.getString("author");
        String coverType = resultSet.getString("coverType");
        String publisher = resultSet.getString("publisher");
        Date publishDate = resultSet.getDate("publishDate");
        int numOfPages = resultSet.getInt("numOfPages");
        String language = resultSet.getString("language");
        String bookCategory = resultSet.getString("bookCategory");
        return new Book(id, title, category, price, quantity, type, author, coverType, publisher, publishDate, numOfPages, language, bookCategory);
    }

    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM " +
                "aims.Book " +
                "INNER JOIN aims.Media " +
                "ON Media.id = Book.id " +
                "where Media.id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {

            // from Media table
            String title = "";
            String type = res.getString("type");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");

            // from Book table
            String author = res.getString("author");
            String coverType = res.getString("coverType");
            String publisher = res.getString("publisher");
            Date publishDate = res.getDate("publishDate");
            int numOfPages = res.getInt("numOfPages");
            String language = res.getString("language");
            String bookCategory = res.getString("bookCategory");

            return new Book(id, title, category, price, quantity, type,
                    author, coverType, publisher, publishDate, numOfPages, language, bookCategory);

        } else {
            throw new SQLException();
        }
    }

    @Override
    public ArrayList getAllMedia() {
        return null;
    }


    @Override
    public String toString() {
        return "{" +
                super.toString() +
                " author='" + author + "'" +
                ", coverType='" + coverType + "'" +
                ", publisher='" + publisher + "'" +
                ", publishDate='" + publishDate + "'" +
                ", numOfPages='" + numOfPages + "'" +
                ", language='" + language + "'" +
                ", bookCategory='" + bookCategory + "'" +
                "}";
    }
}
