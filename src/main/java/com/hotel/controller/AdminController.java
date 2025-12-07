package com.hotel.controller;

import com.hotel.data.StaticData;
import com.hotel.model.Reservation;
import com.hotel.model.Review;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.model.User;
import com.hotel.model.UserRole;
import com.hotel.service.AuthService;
import com.hotel.service.ReservationService;
import com.hotel.service.ReviewService;
import com.hotel.service.RoomService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AdminController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label statsLibreLabel;

    @FXML
    private Label statsOccupeeLabel;

    @FXML
    private Label statsMenageLabel;

    @FXML
    private Label statsPendingReviewsLabel;

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

    @FXML
    private ComboBox<RoomStatus> statusComboBox;

    // Table des réservations
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

    // Table des avis/réclamations
    @FXML
    private TableView<Review> reviewsTable;

    @FXML
    private TableColumn<Review, Integer> reviewIdColumn;

    @FXML
    private TableColumn<Review, String> reviewClientColumn;

    @FXML
    private TableColumn<Review, String> reviewTypeColumn;

    @FXML
    private TableColumn<Review, String> reviewContentColumn;

    @FXML
    private TextArea responseTextArea;

    // Table des utilisateurs
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> userLoginColumn;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, UserRole> userRoleColumn;

    @FXML
    private ToggleButton fullscreenToggle;

    @FXML
    private void initialize() {
        // Affichage du nom de l'utilisateur
        welcomeLabel.setText("Bienvenue, " + AuthService.getCurrentUser().getFullName());

        // Initialisation des statistiques
        updateStatistics();

        // Configuration de la table des chambres
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        roomStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        roomsTable.setItems(RoomService.getAllRooms());

        // Configuration du ComboBox des statuts
        statusComboBox.getItems().addAll(RoomStatus.values());

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
        reservationsTable.setItems(ReservationService.getAllReservations());

        // Configuration de la table des avis
        reviewIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        reviewClientColumn.setCellValueFactory(cellData ->
            javafx.beans.binding.Bindings.createStringBinding(
                () -> cellData.getValue().getClient().getFullName()
            ));
        reviewTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        reviewContentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        reviewsTable.setItems(ReviewService.getAllReviews());

        // Listener pour afficher le contenu de l'avis sélectionné
        reviewsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection.hasResponse()) {
                responseTextArea.setText(newSelection.getAdminResponse());
            } else {
                responseTextArea.clear();
            }
        });

        // Configuration de la table des utilisateurs
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userLoginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        userRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        usersTable.setItems(StaticData.getUsers());
    }

    private void updateStatistics() {
        statsLibreLabel.setText(String.valueOf(RoomService.countRoomsByStatus(RoomStatus.LIBRE)));
        statsOccupeeLabel.setText(String.valueOf(RoomService.countRoomsByStatus(RoomStatus.OCCUPEE)));
        statsMenageLabel.setText(String.valueOf(RoomService.countRoomsByStatus(RoomStatus.MENAGE)));
        statsPendingReviewsLabel.setText(String.valueOf(ReviewService.countPendingReviews()));
    }

    @FXML
    private void handleUpdateRoomStatus() {
        Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        RoomStatus newStatus = statusComboBox.getValue();

        if (selectedRoom == null) {
            showAlert("Erreur", "Veuillez sélectionner une chambre");
            return;
        }

        if (newStatus == null) {
            showAlert("Erreur", "Veuillez sélectionner un statut");
            return;
        }

        RoomService.updateRoomStatus(selectedRoom, newStatus);
        roomsTable.refresh();
        updateStatistics();
        showAlert("Succès", "Statut de la chambre " + selectedRoom.getNumber() + " mis à jour");
    }

    @FXML
    private void handleRespondToReview() {
        Review selectedReview = reviewsTable.getSelectionModel().getSelectedItem();
        String response = responseTextArea.getText().trim();

        if (selectedReview == null) {
            showAlert("Erreur", "Veuillez sélectionner un avis ou une réclamation");
            return;
        }

        if (response.isEmpty()) {
            showAlert("Erreur", "Veuillez entrer une réponse");
            return;
        }

        ReviewService.respondToReview(selectedReview, response);
        reviewsTable.refresh();
        updateStatistics();
        showAlert("Succès", "Réponse ajoutée avec succès");
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleAddReceptionist() {
        // Dialog to collect login, password and full name
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Ajouter Réceptionniste");

        // Buttons
        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Fields
        TextField loginField = new TextField();
        loginField.setPromptText("login");
        PasswordField pwdField = new PasswordField();
        pwdField.setPromptText("mot de passe");
        TextField nameField = new TextField();
        nameField.setPromptText("Nom complet");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new Label("Login:"), 0, 0);
        grid.add(loginField, 1, 0);
        grid.add(new Label("Mot de passe:"), 0, 1);
        grid.add(pwdField, 1, 1);
        grid.add(new Label("Nom complet:"), 0, 2);
        grid.add(nameField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable add button depending on input
        javafx.scene.Node addButton = dialog.getDialogPane().lookupButton(addButtonType);
        addButton.setDisable(true);

        loginField.textProperty().addListener((obs, oldVal, newVal) ->
            addButton.setDisable(loginField.getText().trim().isEmpty() || pwdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty())
        );
        pwdField.textProperty().addListener((obs, oldVal, newVal) ->
            addButton.setDisable(loginField.getText().trim().isEmpty() || pwdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty())
        );
        nameField.textProperty().addListener((obs, oldVal, newVal) ->
            addButton.setDisable(loginField.getText().trim().isEmpty() || pwdField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty())
        );

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                int id = StaticData.getNextUserId();
                return new User(id, loginField.getText().trim(), pwdField.getText().trim(), UserRole.RECEPTION, nameField.getText().trim());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(user -> {
            StaticData.addUser(user);
            usersTable.setItems(StaticData.getUsers());
            usersTable.refresh();
            showAlert("Succès", "Réceptionniste ajouté avec succès");
        });
    }

    @FXML
    private void handleRemoveUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur à supprimer");
            return;
        }

        // Prevent deleting the main admin
        if (selectedUser.getRole() == UserRole.ADMIN) {
            showAlert("Erreur", "Impossible de supprimer un administrateur");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmer la suppression");
        confirm.setHeaderText(null);
        confirm.setContentText("Voulez-vous vraiment supprimer l'utilisateur '" + selectedUser.getFullName() + "' ?");

        confirm.showAndWait().ifPresent(button -> {
            if (button == ButtonType.OK) {
                StaticData.removeUser(selectedUser);
                usersTable.setItems(StaticData.getUsers());
                usersTable.refresh();
                showAlert("Succès", "Utilisateur supprimé");
            }
        });
    }

    @FXML
    private void handleToggleFullScreen() {
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        if (fullscreenToggle != null) {
            stage.setFullScreen(fullscreenToggle.isSelected());
        }
    }
}
