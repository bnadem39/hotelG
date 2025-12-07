package com.hotel.service;

import com.hotel.data.StaticData;
import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import com.hotel.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReservationService {

    public static ObservableList<Reservation> getAllReservations() {
        return StaticData.getReservations();
    }

    public static ObservableList<Reservation> getReservationsByClient(User client) {
        return FXCollections.observableArrayList(
                StaticData.getReservations().stream()
                        .filter(r -> r.getClient().getId() == client.getId())
                        .toList()
        );
    }

    public static void createReservation(User client, Room room, LocalDate checkIn, LocalDate checkOut) {
        int id = StaticData.getNextReservationId();
        Reservation reservation = new Reservation(id, client, room, checkIn, checkOut, "EN_ATTENTE");
        StaticData.addReservation(reservation);
    }

    public static void checkIn(Reservation reservation) {
        reservation.setStatus("CONFIRMEE");
        reservation.getRoom().setStatus(RoomStatus.OCCUPEE);
    }

    public static void checkOut(Reservation reservation) {
        reservation.setStatus("TERMINEE");
        reservation.getRoom().setStatus(RoomStatus.MENAGE);
    }

    public static void cancelReservation(Reservation reservation) {
        reservation.setStatus("ANNULEE");
    }

    public static ObservableList<Reservation> getActiveReservations() {
        return FXCollections.observableArrayList(
                StaticData.getReservations().stream()
                        .filter(r -> r.getStatus().equals("CONFIRMEE") || r.getStatus().equals("EN_ATTENTE"))
                        .toList()
        );
    }
}

