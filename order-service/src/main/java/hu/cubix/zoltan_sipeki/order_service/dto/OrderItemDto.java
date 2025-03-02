package hu.cubix.zoltan_sipeki.order_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderItemDto(
        
        @Null
        Long id,
        
        @NotNull
        Long productId,
        
        @NotEmpty
        String productName,
        
        @PositiveOrZero
        double price,
        
        @Positive
        int count) {

}
