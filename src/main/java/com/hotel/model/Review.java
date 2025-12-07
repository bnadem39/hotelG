package com.hotel.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Review {
    private final IntegerProperty id;
    private final ObjectProperty<User> client;
    private final StringProperty type; // "AVIS" ou "RECLAMATION"
    private final StringProperty content;
    private final StringProperty adminResponse;
    private final ObjectProperty<LocalDateTime> createdAt;

    public Review(int id, User client, String type, String content, String adminResponse, LocalDateTime createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleObjectProperty<>(client);
        this.type = new SimpleStringProperty(type);
        this.content = new SimpleStringProperty(content);
        this.adminResponse = new SimpleStringProperty(adminResponse);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    // Property getters
    public IntegerProperty idProperty() {
        return id;
    }

    public ObjectProperty<User> clientProperty() {
        return client;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public StringProperty adminResponseProperty() {
        return adminResponse;
    }

    public ObjectProperty<LocalDateTime> createdAtProperty() {
        return createdAt;
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

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getAdminResponse() {
        return adminResponse.get();
    }

    public void setAdminResponse(String adminResponse) {
        this.adminResponse.set(adminResponse);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.get();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt.set(createdAt);
    }

    public boolean hasResponse() {
        return adminResponse.get() != null && !adminResponse.get().isEmpty();
    }
}

