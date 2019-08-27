package com.e.marvelapiproject.pojo;

public class Price {

    public String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Price{" +
                "price='" + price + '\'' +
                '}';
    }
}
