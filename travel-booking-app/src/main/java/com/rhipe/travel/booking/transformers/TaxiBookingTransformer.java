package com.rhipe.travel.booking.transformers;

import com.rhipe.travel.booking.dto.BookingInfoDTO;
import com.rhipe.travel.booking.dto.RoomBookingDTO;
import com.rhipe.travel.booking.dto.TaxiBookingDTO;
import org.apache.camel.Message;
import org.apache.camel.spi.DataType;
import org.apache.camel.spi.Transformer;

public class TaxiBookingTransformer extends Transformer {
    @Override
    public void transform(Message message, DataType from, DataType to) {
        final BookingInfoDTO bookingInfoDTO = message.getBody(BookingInfoDTO.class);
        final TaxiBookingDTO taxiBookingDTO = new TaxiBookingDTO(bookingInfoDTO.customerId(), bookingInfoDTO.taxiId());

        message.setBody(taxiBookingDTO);
    }
}
