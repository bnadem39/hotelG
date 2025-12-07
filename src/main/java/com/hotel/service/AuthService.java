package com.hotel.service;

import com.hotel.data.StaticData;
import com.hotel.model.User;
import com.hotel.model.UserRole;

public class AuthService {
    private static User currentUser = null;

    public static User authenticate(String login, String password) {
        User user = StaticData.getUsers().stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            currentUser = user;
        }

        return user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }

    public static boolean isReception() {
        return currentUser != null && currentUser.getRole() == UserRole.RECEPTION;
    }

    public static boolean isClient() {
        return currentUser != null && currentUser.getRole() == UserRole.CLIENT;
    }
}

