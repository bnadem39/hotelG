package com.hotel.controller;

import com.hotel.data.StaticData;
import com.hotel.model.User;
import com.hotel.model.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void handleSubmit() {
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim().toLowerCase();
        String password = passwordField.getText().trim();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs");
            return;
        }

        // Vérifier unicité du login/email
        if (StaticData.getUserByLogin(email) != null) {
            showAlert("Erreur", "Ce login/email est déjà utilisé");
            return;
        }

        // Créer un utilisateur CLIENT
        int id = StaticData.getNextUserId();
        User user = new User(id, email, password, UserRole.CLIENT, fullName);
        StaticData.addUser(user);

        showAlert("Succès", "Compte créé avec succès. Vous pouvez maintenant vous connecter.");
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
