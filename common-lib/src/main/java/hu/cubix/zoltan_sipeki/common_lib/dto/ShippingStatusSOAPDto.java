package hu.cubix.zoltan_sipeki.common_lib.dto;

import hu.cubix.zoltan_sipeki.common_lib.constant.OrderStatus;

public class ShippingStatusSOAPDto {
    
    private long orderId;
    
    private OrderStatus status;
    
    public ShippingStatusSOAPDto() {
    }

    public ShippingStatusSOAPDto(long orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    
}
