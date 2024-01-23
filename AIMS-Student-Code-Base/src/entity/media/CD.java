package entity.media;

import entity.db.AIMSDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//low coupling - data coupling due to getMediaById querying sql data directly
public class CD extends Media {

    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD() throws SQLException {

    }

    public CD(int id, String title, String category, int price, int quantity, String type, String artist,
              String recordLabel, String musicType, Date releasedDate) throws SQLException {
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public CD setId(int id) {
        this.id = id;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    public CD setReleasedDate(String releasedDate) {
        this.releasedDate = new Date(releasedDate);
        return this;
    }

    public static CD getCDById(int id) throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM CD WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        String title = "";
        String category = "";
        int price = 0;
        int quantity = 0;
        String type = "";
        String artist = resultSet.getString("artist");
        String recordLabel = resultSet.getString("recordLabel");
        String musicType = resultSet.getString("musicType");
        Date releasedDate = resultSet.getDate("releasedDate");
        return new CD(id, title, category, price, quantity, type, artist, recordLabel, musicType, releasedDate);

    }

    public void saveCD() throws SQLException {
        Connection connection = AIMSDB.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO CD (id, artist, recordLabel, musicType, releasedDate) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.getId());
            preparedStatement.setString(2, this.getArtist());
            preparedStatement.setString(3, this.getRecordLabel());
            preparedStatement.setString(4, this.getMusicType());
            preparedStatement.setDate(5, new java.sql.Date(this.getReleasedDate().getTime()));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'"
                + "'" + ", musicType='" + musicType + "'" + ", releasedDate='"
                + releasedDate + "'" + "}";
    }

    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM " +
                "aims.CD " +
                "INNER JOIN aims.Media " +
                "ON Media.id = CD.id " +
                "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
        if (res.next()) {

            // from media table
            String title = "";
            String type = res.getString("type");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");

            // from CD table
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");

            return new CD(id, title, category, price, quantity, type,
                    artist, recordLabel, musicType, releasedDate);

        } else {
            throw new SQLException();
        }
    }

    @Override
    public ArrayList getAllMedia() {
        return null;
    }

}
