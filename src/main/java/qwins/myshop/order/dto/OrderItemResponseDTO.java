package qwins.myshop.order.dto;

import qwins.myshop.order.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        BigDecimal price,
        int quantity
) {
    public OrderItemResponseDTO(OrderItem orderItem){
        this(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getPriceAtPurchase(),
                orderItem.getQuantity()
        );
    }
}
