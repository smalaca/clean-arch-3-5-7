package com.smalaca.rentalapplication.query.hotelroom;

import com.google.common.collect.ImmutableMap;
import org.assertj.core.api.Assertions;

import java.util.function.Consumer;

class HotelRoomReadModelAssertion {
    private final HotelRoomReadModel actual;

    private HotelRoomReadModelAssertion(HotelRoomReadModel actual) {
        this.actual = actual;
    }

    static HotelRoomReadModelAssertion assertThat(HotelRoomReadModel actual) {
        return new HotelRoomReadModelAssertion(actual);
    }

    HotelRoomReadModelAssertion hasHotelRoomIdEqualTo(String expected) {
        Assertions.assertThat(actual.getId()).isEqualTo(expected);
        return this;
    }

    HotelRoomReadModelAssertion hasHotelIdEqualTo(String expected) {
        Assertions.assertThat(actual.getHotelId()).isEqualTo(expected);
        return this;
    }

    HotelRoomReadModelAssertion hasNumberEqualTo(int expected) {
        Assertions.assertThat(actual.getNumber()).isEqualTo(expected);
        return this;
    }

    HotelRoomReadModelAssertion hasSpacesDefinitionEqualTo(ImmutableMap<String, Double> expected) {
        Assertions.assertThat(actual.getSpaces()).hasSize(expected.size());

        expected.forEach((name, squareMeter) -> {
            Assertions.assertThat(actual.getSpaces()).anySatisfy(hasSpaceThat(name, squareMeter));
        });

        return this;
    }

    private Consumer<SpaceReadModel> hasSpaceThat(String name, Double squareMeter) {
        return spaceReadModel -> {
            Assertions.assertThat(spaceReadModel.getName()).isEqualTo(name);
            Assertions.assertThat(spaceReadModel.getValue()).isEqualTo(squareMeter);
        };
    }

    HotelRoomReadModelAssertion hasDescriptionEqualTo(String expected) {
        Assertions.assertThat(actual.getDescription()).isEqualTo(expected);
        return this;
    }
}
