package hu.cubix.zoltan_sipeki.shipping_service.service;

import hu.cubix.zoltan_sipeki.common_lib.dto.ShippingDetailsSOAPDto;
import jakarta.jws.WebService;

@WebService
public interface IShippingSOAPService {

    public long ShipOrder(ShippingDetailsSOAPDto dto);
}
