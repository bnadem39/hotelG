package com.hotel.controller;

import com.hotel.model.Reservation;
import com.hotel.model.Review;
import com.hotel.service.AuthService;
import com.hotel.service.ReservationService;
import com.hotel.service.ReviewService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientController {

    @FXML
    private Label welcomeLabel;

    // Table des réservations du client
    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private TableColumn<Reservation, Integer> resIdColumn;

    @FXML
    private TableColumn<Reservation, Integer> resRoomColumn;

    @FXML
    private TableColumn<Reservation, String> resRoomTypeColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> resCheckInColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> resCheckOutColumn;

    @FXML
    private TableColumn<Reservation, String> resStatusColumn;

    // Table des avis/réclamations du client
    @FXML
    private TableView<Review> reviewsTable;

    @FXML
    private TableColumn<Review, Integer> reviewIdColumn;

    @FXML
    private TableColumn<Review, String> reviewTypeColumn;

    @FXML
    private TableColumn<Review, String> reviewContentColumn;

    @FXML
    private TableColumn<Review, LocalDateTime> reviewDateColumn;

    @FXML
    private TableColumn<Review, String> reviewResponseColumn;

    // Formulaire d'ajout d'avis/réclamation
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private ToggleButton fullscreenToggle;

    @FXML
    private void initialize() {
        welcomeLabel.setText("Bienvenue, " + AuthService.getCurrentUser().getFullName());

        // Configuration de la table des réservations
        resIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        resRoomColumn.setCellValueFactory(cellData ->
            cellData.getValue().getRoom().numberProperty().asObject());
        resRoomTypeColumn.setCellValueFactory(cellData ->
            cellData.getValue().getRoom().typeProperty());
        resCheckInColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        resCheckOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        resStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reservationsTable.setItems(ReservationService.getReservationsByClient(AuthService.getCurrentUser()));

        // Configuration de la table des avis
        reviewIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        reviewTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        reviewContentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        reviewDateColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        reviewResponseColumn.setCellValueFactory(new PropertyValueFactory<>("adminResponse"));
        reviewsTable.setItems(ReviewService.getReviewsByClient(AuthService.getCurrentUser()));

        // Configuration du ComboBox de type
        typeComboBox.getItems().addAll("AVIS", "RECLAMATION");
        typeComboBox.setValue("AVIS");
    }

    @FXML
    private void handleSubmitReview() {
        String type = typeComboBox.getValue();
        String content = contentTextArea.getText().trim();

        if (content.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer un contenu");
            return;
        }

        ReviewService.createReview(AuthService.getCurrentUser(), type, content);
        reviewsTable.setItems(ReviewService.getReviewsByClient(AuthService.getCurrentUser()));
        contentTextArea.clear();
        showAlert("Succès", "Votre " + type.toLowerCase() + " a été soumis avec succès");
    }

    @FXML
    private void handleViewResponse() {
        Review selectedReview = reviewsTable.getSelectionModel().getSelectedItem();

        if (selectedReview == null) {
            showAlert("Erreur", "Veuillez sélectionner un avis ou une réclamation");
            return;
        }

        if (!selectedReview.hasResponse()) {
            showAlert("Information", "Aucune réponse pour le moment");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réponse de l'administration");
        alert.setHeaderText("Concernant : " + selectedReview.getType());
        alert.setContentText(selectedReview.getAdminResponse());
        alert.showAndWait();
    }

    @FXML
    private void handleLogout() {
        try {
            AuthService.logout();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotel/view/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion - Gestion Hôtel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleToggleFullScreen() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setFullScreen(fullscreenToggle.isSelected());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
