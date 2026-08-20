package qwins.myshop.cart.dto;

import qwins.myshop.cart.CartItem;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        BigDecimal price,
        int quantity
) {
    public CartItemResponseDTO(CartItem cartItem){
        this(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity()
        );
    }
}
