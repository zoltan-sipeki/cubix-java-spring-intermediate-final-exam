package hu.cubix.zoltan_sipeki.order_service.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.cubix.zoltan_sipeki.order_service.dto.OrderDetailsDto;
import hu.cubix.zoltan_sipeki.order_service.mapper.OrderMapper;
import hu.cubix.zoltan_sipeki.order_service.service.OrderDetailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderDetailService orderService;

    private final OrderMapper orderMapper;

    @PostMapping
    public OrderDetailsDto placeOrder(@RequestBody @Valid OrderDetailsDto orderDto, @AuthenticationPrincipal User user) {
        var order = orderMapper.mapToModel(orderDto);
        order = orderService.placeOrder(order, user.getUsername());
        return orderMapper.mapToDto(order);
    }

    @GetMapping(params = "username")
    @PreAuthorize("authentication.name == #username || hasRole('ADMIN')")
    public List<OrderDetailsDto> findOrder(@RequestParam @NotEmpty String username) {
        return orderMapper.mapToDtoList(orderService.findOrder(username));
    }

    @PostMapping("/confirmed")
    public OrderDetailsDto confirmOrder(@RequestBody @Positive long orderId) {
        return orderMapper.mapToDto(orderService.confirmOrder(orderId));
    }

    @PostMapping("/declined")
    public OrderDetailsDto declineOrder(@RequestBody @Positive long orderId) {
        return orderMapper.mapToDto(orderService.declineOrder(orderId));
    }
}
