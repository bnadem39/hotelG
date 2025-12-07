package com.hotel.model;

import javafx.beans.property.*;

public class Room {
    private final IntegerProperty number;
    private final StringProperty type;
    private final DoubleProperty price;
    private final ObjectProperty<RoomStatus> status;

    public Room(int number, String type, double price, RoomStatus status) {
        this.number = new SimpleIntegerProperty(number);
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleDoubleProperty(price);
        this.status = new SimpleObjectProperty<>(status);
    }

    // Property getters
    public IntegerProperty numberProperty() {
        return number;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public ObjectProperty<RoomStatus> statusProperty() {
        return status;
    }

    // Value getters and setters
    public int getNumber() {
        return number.get();
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public RoomStatus getStatus() {
        return status.get();
    }

    public void setStatus(RoomStatus status) {
        this.status.set(status);
    }

    @Override
    public String toString() {
        return "Chambre " + getNumber() + " - " + getType();
    }
}

