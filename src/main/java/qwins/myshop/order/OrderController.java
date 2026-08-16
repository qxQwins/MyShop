package qwins.myshop.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1. ОФОРМЛЕНИЕ ЗАКАЗА (Кнопка "Купить" на сайте)
    // Запрос в Swagger: POST /api/orders/checkout?userId=5
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestParam long userId) {
        Order completedOrder = orderService.checkout(userId);
        return new ResponseEntity<>(completedOrder, HttpStatus.CREATED); // Статус 201 Created
    }

    @GetMapping
    public ResponseEntity<List<Order>> getUserOrderHistory(@RequestParam long userId) {
        List<Order> history = orderService.findAllByUsersId(userId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        Order order = orderService.findOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(
            @PathVariable long id,
            @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(status, id);
        return ResponseEntity.ok(updatedOrder);
    }


}
