package com.hotel.controller;

import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.service.AuthService;
import com.hotel.service.ReservationService;
import com.hotel.service.RoomService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReceptionController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label statsLibreLabel;

    @FXML
    private Label statsOccupeeLabel;

    @FXML
    private Label statsMenageLabel;

    @FXML
    private ToggleButton fullscreenToggle;

    // Table des chambres
    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;

    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableColumn<Room, Double> roomPriceColumn;

    @FXML
    private TableColumn<Room, RoomStatus> roomStatusColumn;

    // Table des réservations actives
    @FXML
    private TableView<Reservation> reservationsTable;

    @FXML
    private TableColumn<Reservation, Integer> resIdColumn;

    @FXML
    private TableColumn<Reservation, String> resClientColumn;

    @FXML
    private TableColumn<Reservation, Integer> resRoomColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> resCheckInColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> resCheckOutColumn;

    @FXML
    private TableColumn<Reservation, String> resStatusColumn;

    @FXML
    private void initialize() {
        welcomeLabel.setText("Bienvenue, " + AuthService.getCurrentUser().getFullName());
        updateStatistics();

        // Configuration de la table des chambres
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        roomsTable.setItems(RoomService.getAllRooms());

        // Configuration de la table des réservations
        resIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        resClientColumn.setCellValueFactory(cellData ->
            javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getClient().getFullName()
            ));
        resRoomColumn.setCellValueFactory(cellData ->
            cellData.getValue().getRoom().numberProperty().asObject());
        resCheckInColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        resCheckOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        resStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        reservationsTable.setItems(ReservationService.getActiveReservations());
    }

    private void updateStatistics() {
        statsLibreLabel.setText(String.valueOf(RoomService.countRoomsByStatus(RoomStatus.LIBRE)));
        statsOccupeeLabel.setText(String.valueOf(RoomService.countRoomsByStatus(RoomStatus.OCCUPEE)));
        statsMenageLabel.setText(String.valueOf(RoomService.countRoomsByStatus(RoomStatus.MENAGE)));
    }

    @FXML
    private void handleCheckIn() {
        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            showAlert("Erreur", "Veuillez sélectionner une réservation");
            return;
        }

        if (selectedReservation.getStatus().equals("CONFIRMEE")) {
            showAlert("Information", "Cette réservation est déjà confirmée");
            return;
        }

        ReservationService.checkIn(selectedReservation);
        roomsTable.refresh();
        reservationsTable.refresh();
        updateStatistics();
        showAlert("Succès", "Check-in effectué pour la chambre " + selectedReservation.getRoom().getNumber());
    }

    @FXML
    private void handleCheckOut() {
        Reservation selectedReservation = reservationsTable.getSelectionModel().getSelectedItem();

        if (selectedReservation == null) {
            showAlert("Erreur", "Veuillez sélectionner une réservation");
            return;
        }

        if (!selectedReservation.getStatus().equals("CONFIRMEE")) {
            showAlert("Erreur", "Seules les réservations confirmées peuvent faire un check-out");
            return;
        }

        ReservationService.checkOut(selectedReservation);
        roomsTable.refresh();
        reservationsTable.refresh();
        updateStatistics();
        showAlert("Succès", "Check-out effectué pour la chambre " + selectedReservation.getRoom().getNumber());
    }

    @FXML
    private void handleMarkRoomCleaned() {
        Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();

        if (selectedRoom == null) {
            showAlert("Erreur", "Veuillez sélectionner une chambre");
            return;
        }

        if (selectedRoom.getStatus() != RoomStatus.MENAGE) {
            showAlert("Erreur", "Cette chambre n'est pas en ménage");
            return;
        }

        RoomService.updateRoomStatus(selectedRoom, RoomStatus.LIBRE);
        roomsTable.refresh();
        updateStatistics();
        showAlert("Succès", "La chambre " + selectedRoom.getNumber() + " est maintenant disponible");
    }

    @FXML
    private void handleRefresh() {
        roomsTable.refresh();
        reservationsTable.setItems(ReservationService.getActiveReservations());
        updateStatistics();
        showAlert("Succès", "Données actualisées");
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
        if (fullscreenToggle.isSelected()) {
            stage.setFullScreen(true);
        } else {
            stage.setFullScreen(false);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
