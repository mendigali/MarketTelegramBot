package Database.Entities;

import Database.Database;
import Database.Interfaces.CRUD;
import Database.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Advertisement<T> implements CRUD {

    private long id;
    private long author_id;
    private String author_username;
    private String title;
    private String description;
    private long price;
    private String payment_type;
    private long date;
    private boolean is_visible;

    public Advertisement() {
    }

    public Advertisement(long id, long author_id, String author_username, String title, String description,
                         long price, String payment_type, long date, boolean is_visible) {
        this.id = id;
        this.author_id = author_id;
        this.author_username = author_username;
        this.title = title;
        this.description = description;
        this.price = price;
        this.payment_type = payment_type;
        this.date = date;
        this.is_visible = is_visible;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_username() {
        return author_username;
    }

    public void setAuthor_username(String author_username) {
        this.author_username = author_username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isIs_visible() {
        return is_visible;
    }

    public void setIs_visible(boolean is_visible) {
        this.is_visible = is_visible;
    }

    @Override
    public void insert() {
        try {
            Database database = Database.getInstance();
            PreparedStatement statement = database.getConnection().prepareStatement(SQL.INSERT_ADVERTISEMENT);
            statement.setLong(1, author_id);
            statement.setString(2, author_username);
            statement.setString(3, title);
            statement.setString(4, description);
            statement.setLong(5, price);
            statement.setString(6, payment_type);
            statement.setLong(7, date);
            statement.setBoolean(8, is_visible);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Advertisement> read() {
        ArrayList<Advertisement> advertisements = new ArrayList<Advertisement>();
        try {
            Database database = Database.getInstance();
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SQL.READ_ADVERTISEMENTS);
            while (resultSet.next()) {
                Advertisement advertisement = new Advertisement();
                advertisement.setId(resultSet.getLong("id"));
                advertisement.setAuthor_id(resultSet.getLong("author_id"));
                advertisement.setAuthor_username(resultSet.getString("author_username"));
                advertisement.setTitle(resultSet.getString("title"));
                advertisement.setDescription(resultSet.getString("description"));
               advertisement.setPrice(resultSet.getLong("price"));
                advertisement.setPayment_type(resultSet.getString("payment_type"));
                advertisement.setDate(resultSet.getLong("date"));
                advertisement.setIs_visible(resultSet.getBoolean("is_visible"));
                advertisements.add(advertisement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return advertisements;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
