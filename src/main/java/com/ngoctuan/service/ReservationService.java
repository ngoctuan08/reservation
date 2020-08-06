package com.ngoctuan.service;

import com.ngoctuan.common.dto.RoomReservation;
import com.ngoctuan.domain.entity.Guest;
import com.ngoctuan.domain.entity.Reservation;
import com.ngoctuan.domain.entity.Room;
import com.ngoctuan.domain.repository.GuestRepository;
import com.ngoctuan.domain.repository.ReservationRepository;
import com.ngoctuan.domain.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReservationService {
    private RoomRepository roomRepository;
    private GuestRepository guestRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate (Date date){
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findByDate(new java.sql.Date(date.getTime()));
        if(reservations != null) {
            reservations.forEach(reservation -> {
                Guest guest = this.guestRepository.findById(reservation.getGuestId());
                if(guest != null){
                    RoomReservation roomReservation = roomReservationMap.get(reservation.getReservationId());
                    roomReservation.setDate(date);
                    roomReservation.setGuestId(guest.getId());
                    roomReservation.setFirstName(guest.getFirstName());
                    roomReservation.setLastName(guest.getLastName());
                }
            });
        }
        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long roomId: roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(roomId));
        }
        return roomReservations;
    }
}
