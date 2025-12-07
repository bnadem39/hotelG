package com.hotel.data;

import com.hotel.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StaticData {
    // Listes observables pour JavaFX
    private static final ObservableList<User> users = FXCollections.observableArrayList();
    private static final ObservableList<Room> rooms = FXCollections.observableArrayList();
    private static final ObservableList<Reservation> reservations = FXCollections.observableArrayList();
    private static final ObservableList<Review> reviews = FXCollections.observableArrayList();

    // Compteurs pour les IDs
    private static int nextUserId = 5;
    private static int nextReservationId = 6;
    private static int nextReviewId = 7;

    static {
        initializeUsers();
        initializeRooms();
        initializeReservations();
        initializeReviews();
    }

    private static void initializeUsers() {
        users.add(new User(1, "admin", "123", UserRole.ADMIN, "Administrateur Principal"));
        users.add(new User(2, "reception", "123", UserRole.RECEPTION, "Réceptionniste Chef"));
        users.add(new User(3, "ali", "123", UserRole.CLIENT, "Ali Ben Salah"));
        users.add(new User(4, "sarra", "123", UserRole.CLIENT, "Sarra Mansouri"));
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Simple", 80.0, RoomStatus.LIBRE));
        rooms.add(new Room(102, "Simple", 80.0, RoomStatus.OCCUPEE));
        rooms.add(new Room(103, "Double", 120.0, RoomStatus.LIBRE));
        rooms.add(new Room(104, "Double", 120.0, RoomStatus.MENAGE));
        rooms.add(new Room(105, "Suite", 200.0, RoomStatus.LIBRE));
        rooms.add(new Room(106, "Suite", 200.0, RoomStatus.OCCUPEE));
        rooms.add(new Room(107, "Simple", 80.0, RoomStatus.LIBRE));
        rooms.add(new Room(108, "Double", 120.0, RoomStatus.OCCUPEE));
        rooms.add(new Room(109, "Double", 120.0, RoomStatus.LIBRE));
        rooms.add(new Room(110, "Suite", 200.0, RoomStatus.MENAGE));
    }

    private static void initializeReservations() {
        User ali = getUserByLogin("ali");
        User sarra = getUserByLogin("sarra");

        // Réservations pour Ali
        reservations.add(new Reservation(1, ali, getRoomByNumber(102),
            LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), "CONFIRMEE"));
        reservations.add(new Reservation(2, ali, getRoomByNumber(105),
            LocalDate.now().plusDays(10), LocalDate.now().plusDays(15), "EN_ATTENTE"));

        // Réservations pour Sarra
        reservations.add(new Reservation(3, sarra, getRoomByNumber(106),
            LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), "CONFIRMEE"));
        reservations.add(new Reservation(4, sarra, getRoomByNumber(103),
            LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), "CONFIRMEE"));
        reservations.add(new Reservation(5, sarra, getRoomByNumber(108),
            LocalDate.now().minusDays(20), LocalDate.now().minusDays(15), "TERMINEE"));
    }

    private static void initializeReviews() {
        User ali = getUserByLogin("ali");
        User sarra = getUserByLogin("sarra");

        // Avis avec réponse admin
        reviews.add(new Review(1, ali, "AVIS",
            "Excellent séjour ! Chambre très propre et personnel accueillant.",
            "Merci beaucoup pour votre retour positif ! À bientôt.",
            LocalDateTime.now().minusDays(3)));

        reviews.add(new Review(2, sarra, "RECLAMATION",
            "Le wifi ne fonctionne pas correctement dans ma chambre.",
            "Nous avons vérifié et résolu le problème. Toutes nos excuses.",
            LocalDateTime.now().minusDays(2)));

        // Avis sans réponse
        reviews.add(new Review(3, ali, "AVIS",
            "Très bon petit-déjeuner, grande variété de choix.",
            null,
            LocalDateTime.now().minusDays(1)));

        reviews.add(new Review(4, sarra, "RECLAMATION",
            "La climatisation fait trop de bruit la nuit.",
            null,
            LocalDateTime.now().minusHours(12)));

        reviews.add(new Review(5, ali, "AVIS",
            "Belle vue depuis la chambre 105. Je recommande !",
            "Merci ! Nous sommes ravis que vous ayez apprécié.",
            LocalDateTime.now().minusHours(6)));

        reviews.add(new Review(6, sarra, "RECLAMATION",
            "Le check-out à 10h est trop tôt, pourrait-il être à 11h ?",
            null,
            LocalDateTime.now().minusHours(2)));
    }

    // Getters pour les listes observables
    public static ObservableList<User> getUsers() {
        return users;
    }

    public static ObservableList<Room> getRooms() {
        return rooms;
    }

    public static ObservableList<Reservation> getReservations() {
        return reservations;
    }

    public static ObservableList<Review> getReviews() {
        return reviews;
    }

    // Méthodes utilitaires
    public static User getUserByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public static Room getRoomByNumber(int number) {
        return rooms.stream()
                .filter(r -> r.getNumber() == number)
                .findFirst()
                .orElse(null);
    }

    public static int getNextReservationId() {
        return nextReservationId++;
    }

    public static int getNextReviewId() {
        return nextReviewId++;
    }

    public static int getNextUserId() {
        return nextUserId++;
    }

    public static void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public static void addReview(Review review) {
        reviews.add(review);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }
}
