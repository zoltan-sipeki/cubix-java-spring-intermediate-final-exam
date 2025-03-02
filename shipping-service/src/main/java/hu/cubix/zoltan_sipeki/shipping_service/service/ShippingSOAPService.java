package hu.cubix.zoltan_sipeki.shipping_service.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import hu.cubix.zoltan_sipeki.common_lib.constant.OrderStatus;
import hu.cubix.zoltan_sipeki.common_lib.dto.ShippingDetailsSOAPDto;
import hu.cubix.zoltan_sipeki.common_lib.dto.ShippingStatusSOAPDto;
import io.netty.util.internal.ThreadLocalRandom;

public class ShippingSOAPService implements IShippingSOAPService {

    private JmsTemplate jmsTemplate;

    private TaskScheduler taskScheduler;

    public ShippingSOAPService(JmsTemplate jmsTemplate, TaskScheduler taskScheduler) {
        this.jmsTemplate = jmsTemplate;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public long ShipOrder(ShippingDetailsSOAPDto dto) {
        taskScheduler.schedule(() -> {
            var status = ThreadLocalRandom.current().nextFloat() < 0.5 ? OrderStatus.DELIVERED
                    : OrderStatus.SHIPMENT_FAILED;
            jmsTemplate.convertAndSend("shippingStatus", new ShippingStatusSOAPDto(dto.getOrderId(), status));
        }, Instant.now().plusSeconds(10));

        return ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
    }
}
