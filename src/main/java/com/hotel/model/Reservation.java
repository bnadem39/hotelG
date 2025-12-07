package com.hotel.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Reservation {
    private final IntegerProperty id;
    private final ObjectProperty<User> client;
    private final ObjectProperty<Room> room;
    private final ObjectProperty<LocalDate> checkInDate;
    private final ObjectProperty<LocalDate> checkOutDate;
    private final StringProperty status;

    public Reservation(int id, User client, Room room, LocalDate checkInDate, LocalDate checkOutDate, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleObjectProperty<>(client);
        this.room = new SimpleObjectProperty<>(room);
        this.checkInDate = new SimpleObjectProperty<>(checkInDate);
        this.checkOutDate = new SimpleObjectProperty<>(checkOutDate);
        this.status = new SimpleStringProperty(status);
    }

    // Property getters
    public IntegerProperty idProperty() {
        return id;
    }

    public ObjectProperty<User> clientProperty() {
        return client;
    }

    public ObjectProperty<Room> roomProperty() {
        return room;
    }

    public ObjectProperty<LocalDate> checkInDateProperty() {
        return checkInDate;
    }

    public ObjectProperty<LocalDate> checkOutDateProperty() {
        return checkOutDate;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Value getters and setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public User getClient() {
        return client.get();
    }

    public void setClient(User client) {
        this.client.set(client);
    }

    public Room getRoom() {
        return room.get();
    }

    public void setRoom(Room room) {
        this.room.set(room);
    }

    public LocalDate getCheckInDate() {
        return checkInDate.get();
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate.set(checkInDate);
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate.get();
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate.set(checkOutDate);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}

