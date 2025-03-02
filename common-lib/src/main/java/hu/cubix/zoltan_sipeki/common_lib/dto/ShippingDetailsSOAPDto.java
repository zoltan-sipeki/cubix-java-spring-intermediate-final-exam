package hu.cubix.zoltan_sipeki.common_lib.dto;

import java.util.List;

public class ShippingDetailsSOAPDto {
    
    private Long orderId;
    
    private String pickupAddress;
    
    private String shippingAddress;
    
    private List<ShippingItemSOAPDto> items;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<ShippingItemSOAPDto> getItems() {
        return items;
    }

    public void setItems(List<ShippingItemSOAPDto> items) {
        this.items = items;
    }
}
