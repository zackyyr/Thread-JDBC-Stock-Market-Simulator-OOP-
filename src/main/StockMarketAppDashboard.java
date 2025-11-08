package main;

import db.DatabaseManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Stock;
import thread.StockThread;

public class StockMarketAppDashboard {

    // fungsi clear console
    private static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ignored) {}
    }

    // Hitung lebar kolom berdasarkan header + data
    private static int[] getColumnWidths(List<Stock> stocks) {
        int symbolWidth = "Symbol".length();
        int nameWidth = "Name".length();
        int currentWidth = "Current Price".length();
        int prevWidth = "Prev Price".length();
        int changeWidth = "Change".length();

        for (Stock s : stocks) {
            symbolWidth = Math.max(symbolWidth, s.getSymbol().length());
            nameWidth = Math.max(nameWidth, s.getName().length());
            currentWidth = Math.max(currentWidth, String.format("%.2f", s.getCurrentPrice()).length());
            prevWidth = Math.max(prevWidth, String.format("%.2f", s.getPreviousPrice()).length());
            changeWidth = Math.max(changeWidth, String.format("%.2f", Math.abs(s.getCurrentPrice() - s.getPreviousPrice())).length() + 2);
        }

        return new int[]{symbolWidth, nameWidth, currentWidth, prevWidth, changeWidth};
    }

    private static void printHeader(int[] widths) {
        int totalWidth = widths[0] + widths[1] + widths[2] + widths[3] + widths[4] + 14; // 14 untuk spasi & separator
        String line = "=".repeat(totalWidth);
        System.out.println(line);
        System.out.printf("%" + widths[0] + "s | %" + widths[1] + "s | %" + widths[2] + "s | %" + widths[3] + "s | %" + widths[4] + "s%n",
                "Symbol", "Name", "Current Price", "Prev Price", "Change");
        System.out.println("-".repeat(totalWidth));
    }

    private static void printStocks(List<Stock> stocks, int[] widths) {
        for (Stock s : stocks) {
            double change = s.getCurrentPrice() - s.getPreviousPrice();
            String arrow = change > 0 ? "↑" : (change < 0 ? "↓" : "-");
            System.out.printf("%-" + widths[0] + "s | %-" + widths[1] + "s | %" + widths[2] + ".2f | %" + widths[3] + ".2f | %s %"+(widths[4]-2)+".2f%n",
                    s.getSymbol(), s.getName(), s.getCurrentPrice(), s.getPreviousPrice(), arrow, Math.abs(change));
        }
    }

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();
        DatabaseManager.testConnection();

        List<Stock> stocks = new ArrayList<>();
        try (ResultSet rs = DatabaseManager.getConnection().createStatement()
                .executeQuery("SELECT * FROM stocks")) {
            while (rs.next()) {
                stocks.add(new Stock(
                        rs.getInt("id"),
                        rs.getString("symbol"),
                        rs.getString("name"),
                        rs.getDouble("current_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Jalankan thread tiap saham
        for (Stock stock : stocks) new StockThread(stock).start();

        while (true) {
            clearConsole();
            int[] widths = getColumnWidths(stocks);
            printHeader(widths);
            printStocks(stocks, widths);
            System.out.println("\n[App] Press Ctrl+C to exit.");
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        }
    }
}
