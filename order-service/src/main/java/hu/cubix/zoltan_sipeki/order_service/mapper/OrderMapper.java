package hu.cubix.zoltan_sipeki.order_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.cubix.zoltan_sipeki.order_service.soap.ShippingDetailsSOAPDto;
import hu.cubix.zoltan_sipeki.order_service.soap.ShippingItemSOAPDto;
import hu.cubix.zoltan_sipeki.order_service.dto.OrderDetailsDto;
import hu.cubix.zoltan_sipeki.order_service.dto.OrderItemDto;
import hu.cubix.zoltan_sipeki.order_service.model.OrderDetail;
import hu.cubix.zoltan_sipeki.order_service.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    public OrderDetailsDto mapToDto(OrderDetail order);
    
    public OrderDetail mapToModel(OrderDetailsDto order);

    public OrderItemDto mapToDto(OrderItem orderItem);

    public OrderItem mapToModel(OrderItemDto orderItem);

    public List<OrderDetailsDto> mapToDtoList(List<OrderDetail> orders);

    @Mapping(target = "orderId", source = "id")
    public ShippingDetailsSOAPDto mapToSoapDto(OrderDetail order);

    @Mapping(target = "orderItemId", source = "id")
    public ShippingItemSOAPDto mapToSoapDto(OrderItem orderItem);
}
