package com.smalaca.rentalapplication.domain.hotelroom;

import com.smalaca.rentalapplication.domain.clock.Clock;
import com.smalaca.rentalapplication.domain.event.EventIdFactory;
import com.smalaca.rentalapplication.domain.eventchannel.EventChannel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class HotelRoomEventsPublisherTest {
    private final EventChannel eventChannel = mock(EventChannel.class);
    private final HotelRoomEventsPublisher publisher = new HotelRoomEventsPublisher(new EventIdFactory(), new Clock(), eventChannel);

    @Test
    void shouldPublishHotelRoomBookedEvent() {
        ArgumentCaptor<HotelRoomBooked> captor = ArgumentCaptor.forClass(HotelRoomBooked.class);
        String hotelRoomId = "1234";
        String hotelId = "5678";
        String tenantId = "3456";
        LocalDateTime beforeNow = LocalDateTime.now().minusNanos(1);
        List<LocalDate> days = asList(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 2), LocalDate.of(2020, 1, 3));

        publisher.publishHotelRoomBooked(hotelRoomId, hotelId, tenantId, days);

        then(eventChannel).should().publish(captor.capture());
        HotelRoomBooked actual = captor.getValue();
        assertThat(actual.getEventId()).matches(Pattern.compile("[0-9a-z\\-]{36}"));
        assertThat(actual.getEventCreationDateTime())
                .isAfter(beforeNow)
                .isBefore(LocalDateTime.now().plusNanos(1));
        assertThat(actual.getHotelRoomId()).isEqualTo(hotelRoomId);
        assertThat(actual.getHotelId()).isEqualTo(hotelId);
        assertThat(actual.getTenantId()).isEqualTo(tenantId);
        assertThat(actual.getDays()).containsExactlyElementsOf(days);
    }
}