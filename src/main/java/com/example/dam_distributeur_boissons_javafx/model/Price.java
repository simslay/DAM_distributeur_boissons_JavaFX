package com.example.dam_distributeur_boissons_javafx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Price {
    private final StringProperty price;

    public Price(String price) {
        this.price = new SimpleStringProperty(price);
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
