package qwins.myshop.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.order.dto.OrderResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@RequestParam long userId) {
        Order completedOrder = orderService.checkout(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponseDTO(completedOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getUserOrderHistory(@RequestParam long userId) {
        List<OrderResponseDTO> orderResponseDTOList = orderService.findAllByUsersId(userId)
                .stream()
                .map(
                        order -> new OrderResponseDTO(order)
                ).
                toList();

        return ResponseEntity.ok(orderResponseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable long id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(new OrderResponseDTO(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable long id,
            @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(status, id);
        return ResponseEntity.ok(new OrderResponseDTO(updatedOrder));
    }


}
