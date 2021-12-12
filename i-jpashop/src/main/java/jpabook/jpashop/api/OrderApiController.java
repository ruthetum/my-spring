package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.response.OrderDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    @GetMapping("/api/v1/simple/orders")
    public List<Order> ordersV1 () {
        return orderService.ordersV1();
    }

    @GetMapping("/api/v2/simple/orders")
    public List<OrderDto> ordersV2() {
        return orderService.ordersV2();
    }
}
