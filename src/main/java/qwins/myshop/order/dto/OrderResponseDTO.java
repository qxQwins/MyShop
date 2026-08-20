package qwins.myshop.order.dto;

import qwins.myshop.order.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long userId,
        String status,
        BigDecimal price,
        Date createdDate,
        List<OrderItemResponseDTO> items
) {
    public OrderResponseDTO(Order order){
        this(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getPrice(),
                order.getCreatedAt(),
                order.getItems().stream().map(OrderItemResponseDTO::new).toList()
        );
    }
}
