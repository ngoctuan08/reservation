package com.ngoctuan.endpoint;

import com.ngoctuan.common.dto.RoomReservation;
import com.ngoctuan.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void getReservations() throws Exception {
        Date date = DATE_FORMAT.parse("2020-07-20");
        List<RoomReservation> mockRoomReservations = new ArrayList<>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setFirstName("JUnit");
        roomReservation.setLastName("Test");
        roomReservation.setDate(date);
        roomReservation.setGuestId(1);
        roomReservation.setRoomId(1);
        roomReservation.setRoomNumber("1");
        roomReservation.setRoomName("Test");
        mockRoomReservations.add(roomReservation);

        given(reservationService.getRoomReservationsForDate(date)).willReturn(mockRoomReservations);

        this.mockMvc.perform(get("/reservations?date=2020-07-20")).andExpect(status().isOk()).andExpect(content().string(containsString("Test")));
    }
}
