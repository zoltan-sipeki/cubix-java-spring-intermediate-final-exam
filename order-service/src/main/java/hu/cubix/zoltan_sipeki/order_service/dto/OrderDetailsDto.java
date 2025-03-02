package hu.cubix.zoltan_sipeki.order_service.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;

public record OrderDetailsDto(

        @Null Long id,

        @Null String username,

        @NotEmpty String shippingAddress,

        @Null String status,

        @NotEmpty List<@Valid OrderItemDto> items,

        @Null Long shipmentId) {

}
