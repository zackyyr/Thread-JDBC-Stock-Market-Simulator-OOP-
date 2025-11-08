package model;

public class Stock {
    private int id;
    private String symbol, name;
    private double currentPrice, previousPrice;

    public Stock(int id, String symbol, String name, double currentPrice) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.previousPrice = currentPrice;
    }

    public int getId() { return id; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getCurrentPrice() { return currentPrice; }
    public double getPreviousPrice() { return previousPrice; }

    public void setCurrentPrice(double currentPrice) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return symbol + " - " + name + ": " + currentPrice;
    }
}
