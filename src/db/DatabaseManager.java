package db;

import java.sql.*;

public class DatabaseManager {
    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "stock_db";
    private static final String URL = BASE_URL + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private static Connection connection;

    // Dapatkan koneksi
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try { Class.forName("com.mysql.cj.jdbc.Driver"); } 
            catch (ClassNotFoundException e) { e.printStackTrace(); }
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("[DB] Connected to MySQL successfully!");
        }
        return connection;
    }

    // buat database & tabel kalo belum ada
    public static void initializeDatabase() {
        try (Connection baseConn = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
             Statement stmt = baseConn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS stocks (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        symbol VARCHAR(10) NOT NULL,
                        name VARCHAR(50) NOT NULL,
                        current_price DOUBLE NOT NULL
                    )
                """);
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS price_history (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        stock_id INT,
                        price DOUBLE NOT NULL,
                        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (stock_id) REFERENCES stocks(id)
                    )
                """);
            }
            System.out.println("[DB] Database & tables checked/created!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // testing koneksi
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println(conn != null ? "[DB] Connection test: SUCCESS ✅" : "[DB] Connection test: FAILED ❌");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        return getConnection().createStatement().executeQuery(query);
    }

    // Eksekusi query INSERT/UPDATE/DELETE
    public static int executeUpdate(String query) throws SQLException {
        return getConnection().createStatement().executeUpdate(query);
    }

    // close koneksi
    public static void closeConnection() {
        try { if (connection != null && !connection.isClosed()) connection.close(); } 
        catch (SQLException e) { e.printStackTrace(); }
    }
}
