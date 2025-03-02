package hu.cubix.zoltan_sipeki.order_service.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.common_lib.dto.ShippingStatusSOAPDto;
import hu.cubix.zoltan_sipeki.order_service.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ShippingStatusListener {

    private final OrderDetailRepository orderRepository;

    @JmsListener(destination = "shippingStatus")
    @Transactional
    public void listen(ShippingStatusSOAPDto message) {
        System.out.println("Updating status of order " + message.getOrderId() + " to " + message.getStatus());
        orderRepository.updateStatus(message.getOrderId(), message.getStatus());
    }
}
