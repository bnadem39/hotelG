package com.hotel.service;

import com.hotel.data.StaticData;
import com.hotel.model.Room;
import com.hotel.model.RoomStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomService {

    public static ObservableList<Room> getAllRooms() {
        return StaticData.getRooms();
    }

    public static ObservableList<Room> getAvailableRooms() {
        return FXCollections.observableArrayList(
                StaticData.getRooms().stream()
                        .filter(r -> r.getStatus() == RoomStatus.LIBRE)
                        .toList()
        );
    }

    public static void updateRoomStatus(Room room, RoomStatus newStatus) {
        room.setStatus(newStatus);
    }

    public static Room getRoomByNumber(int number) {
        return StaticData.getRoomByNumber(number);
    }

    public static long countRoomsByStatus(RoomStatus status) {
        return StaticData.getRooms().stream()
                .filter(r -> r.getStatus() == status)
                .count();
    }
}

