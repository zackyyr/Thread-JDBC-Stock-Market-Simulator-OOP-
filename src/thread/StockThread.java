package thread;

import db.DatabaseManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Stock;

public class StockThread extends Thread {
    private Stock stock;

    public StockThread(Stock stock) {
        super("Thread-" + stock.getSymbol());
        this.stock = stock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                double change = (Math.random() - 0.5) * 50;
                double newPrice = Math.max(1, stock.getCurrentPrice() + change);
                stock.setCurrentPrice(newPrice);

                try (PreparedStatement stmt = DatabaseManager.getConnection()
                        .prepareStatement("UPDATE stocks SET current_price=? WHERE id=?")) {
                    stmt.setDouble(1, stock.getCurrentPrice());
                    stmt.setInt(2, stock.getId());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
