package com.ngoctuan.endpoint;

import com.ngoctuan.common.dto.RoomReservation;
import com.ngoctuan.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservations")
@PreAuthorize("hasRole('ROLE_USER')")
public class ReservationController {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET)
    public List<RoomReservation> getReservations(@RequestParam(value = "date", required = false) String dateString){
        Date date = null;
        if(dateString != null) {
            try {
                date = DATE_FORMAT.parse(dateString);
            } catch (ParseException e){
                date = new Date();
            }
        } else {
            date = new Date();
        }
        List<RoomReservation> reservations = this.reservationService.getRoomReservationsForDate(date);
        return reservations;
    }
}
