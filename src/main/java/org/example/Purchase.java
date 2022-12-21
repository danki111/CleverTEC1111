package org.example;

public class Purchase {
    private String product;
    private double price;
    private int amount;

    public Purchase(String product, double price){
        this.product = product;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format(" %-12s  $%-7.2f  %-8d $%-7.2f",
                this.product,this.price,this.amount,this.price*this.amount);
    }
}
