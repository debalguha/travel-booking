package com.rhipe.travel.booking.transformers;

import com.rhipe.travel.booking.dto.BookingInfoDTO;
import com.rhipe.travel.booking.dto.FlightBookingDTO;
import com.rhipe.travel.booking.dto.RoomBookingDTO;
import org.apache.camel.Message;
import org.apache.camel.spi.DataType;
import org.apache.camel.spi.Transformer;

public class HotelBookingTransformer extends Transformer {
    @Override
    public void transform(Message message, DataType from, DataType to) {
        final BookingInfoDTO bookingInfoDTO = message.getBody(BookingInfoDTO.class);
        final RoomBookingDTO roomBookingDTO = new RoomBookingDTO(bookingInfoDTO.customerId(), bookingInfoDTO.hotelRoomIds());

        message.setBody(roomBookingDTO);
    }
}
