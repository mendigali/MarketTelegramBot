package Database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Database {

    private static Database database;
    private Connection connection;

    private Database() {
        Dotenv dotenv = Dotenv.load();
        try {
            Class.forName(dotenv.get("DATABASE_DRIVER"));
            String url = dotenv.get("DATABASE_URL") + dotenv.get("DATABASE_NAME");
            connection = DriverManager.getConnection(url, dotenv.get("DATABASE_USERNAME"), dotenv.get("DATABASE_PASSWORD"));

            String table_name = "advertisements";
            boolean table_exists = false;
            if (connection != null) {
                ResultSet rs = connection.getMetaData().getCatalogs();
                while (rs.next()) {
                    String catalog = rs.getString(1);
                    if (table_name.equals(catalog)) {
                        table_exists = true;
                    }
                }
            }
            if (!table_exists) {
                createTable();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() throws SQLException {
        if (database == null || database.getConnection().isClosed()) {
            database = new Database();
        }
        return database;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL.CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}