package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.dto.OrderSimpleQueryDto;
import jpabook.jpashop.dto.response.OrderSimpleDto;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<OrderSimpleDto> ordersV2() {
        return orderService.ordersV2();
    }

    @GetMapping("/api/v3/simple/orders")
    public List<OrderSimpleDto> ordersV3() {
        return orderService.ordersV3();
    }

    /**
     * V3.1 엔티티를 조회해서 DTO로 변환 페이징 고려
     * - ToOne 관계만 우선 모두 페치 조인으로 최적화
     * - 컬렉션 관계는 hibernate.default_batch_fetch_size, @BatchSize로 최적화
     */
    @GetMapping("/api/v3.1/simple/orders")
    public List<OrderSimpleDto> ordersV3_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return orderService.ordersV3_page(offset, limit);
    }

    @GetMapping("/api/v4/simple/orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderService.ordersV4();
    }
}
