package com.smalaca.rentalapplication.application.hotelroom;

import com.smalaca.rentalapplication.domain.apartment.Booking;
import com.smalaca.rentalapplication.domain.apartment.BookingRepository;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoom;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomEventsPublisher;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomFactory;
import com.smalaca.rentalapplication.domain.hotelroom.HotelRoomRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HotelRoomApplicationService {
    private final HotelRoomRepository hotelRoomRepository;
    private final BookingRepository bookingRepository;
    private final HotelRoomEventsPublisher hotelRoomEventsPublisher;

    HotelRoomApplicationService(
            HotelRoomRepository hotelRoomRepository, BookingRepository bookingRepository, HotelRoomEventsPublisher hotelRoomEventsPublisher) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.bookingRepository = bookingRepository;
        this.hotelRoomEventsPublisher = hotelRoomEventsPublisher;
    }

    public String add(String hotelId, int number, Map<String, Double> spacesDefinition, String description) {
        HotelRoom hotelRoom = new HotelRoomFactory().create(hotelId, number, spacesDefinition, description);

        return hotelRoomRepository.save(hotelRoom);
    }

    public String book(String id, String tenantId, List<LocalDate> days) {
        HotelRoom hotelRoom = hotelRoomRepository.findById(id);

        Booking booking = hotelRoom.book(tenantId, days, hotelRoomEventsPublisher);

        return bookingRepository.save(booking);
    }
}
