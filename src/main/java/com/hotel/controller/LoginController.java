package com.hotel.controller;

import com.hotel.model.User;
import com.hotel.model.UserRole;
import com.hotel.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private ToggleButton fullscreenToggle;

    @FXML
    private void handleLogin() {
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        User user = AuthService.authenticate(login, password);

        if (user == null) {
            showError("Login ou mot de passe incorrect");
            return;
        }

        // Redirection selon le rôle
        try {
            redirectToDashboard(user.getRole());
        } catch (Exception e) {
            showError("Erreur lors du chargement du tableau de bord");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hotel/view/fxml/register.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Créer un compte");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Impossible d'ouvrir le formulaire d'inscription");
        }
    }

    @FXML
    private void handleToggleFullScreen() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        boolean newState = fullscreenToggle.isSelected();
        stage.setFullScreen(newState);
    }

    private void redirectToDashboard(UserRole role) throws Exception {
        String fxmlFile = switch (role) {
            case ADMIN -> "/com/hotel/view/fxml/admin-dashboard.fxml";
            case RECEPTION -> "/com/hotel/view/fxml/reception-dashboard.fxml";
            case CLIENT -> "/com/hotel/view/fxml/client-dashboard.fxml";
        };

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion Hôtel - " + role.name());
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    @FXML
    private void initialize() {
        errorLabel.setVisible(false);
    }
}
