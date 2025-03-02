package hu.cubix.zoltan_sipeki.order_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.cubix.zoltan_sipeki.common_lib.constant.OrderStatus;
import hu.cubix.zoltan_sipeki.common_lib.exception.EntityNotFoundException;
import hu.cubix.zoltan_sipeki.order_service.mapper.OrderMapper;
import hu.cubix.zoltan_sipeki.order_service.model.OrderDetail;
import hu.cubix.zoltan_sipeki.order_service.repository.OrderDetailRepository;
import hu.cubix.zoltan_sipeki.order_service.repository.OrderItemRepository;
import hu.cubix.zoltan_sipeki.order_service.soap.ShippingSOAPServiceService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderDetailService {

    private final OrderDetailRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final OrderMapper orderMapper;

    @Transactional
    public OrderDetail placeOrder(OrderDetail order, String username) {
        order.setUsername(username);
        orderRepository.save(order);
        var items = order.getItems();
        items.forEach(item -> item.setOrderDetail(order));
        orderItemRepository.saveAll(order.getItems());
        return order;
    }

    @Transactional
    public OrderDetail confirmOrder(long orderId) {
        var order = orderRepository.findByIdAndStatus(orderId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException("ORDER", "id", Long.toString(orderId), "status", OrderStatus.PENDING.name()));
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        var shippingService = new ShippingSOAPServiceService().getShippingSOAPServicePort();

        var shippingDetails =  orderMapper.mapToSoapDto(order);
        shippingDetails.setPickupAddress("dark alley");

        var shipmentId = shippingService.shipOrder(shippingDetails);
        System.out.println("Updating shipment ID (" + shipmentId + ") for order (" + orderId + ")");
        orderRepository.updateShipmentId(orderId, shipmentId);
        return order;
    }

    @Transactional
    public OrderDetail declineOrder(long orderId) {
        var order = orderRepository.findByIdAndStatus(orderId, OrderStatus.PENDING)
                .orElseThrow(() -> new EntityNotFoundException("ORDER", "id", Long.toString(orderId), "status", OrderStatus.PENDING.name()));
        order.setStatus(OrderStatus.DECLINED);
        orderRepository.save(order);
        return order;
    }

    public List<OrderDetail> findOrder(String username) {
        return orderRepository.findByUsername(username);
    }
}
