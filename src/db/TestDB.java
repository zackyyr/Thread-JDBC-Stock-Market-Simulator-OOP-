package db;

public class TestDB {
    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        DatabaseManager.testConnection();
        DatabaseManager.closeConnection();
    }
}
