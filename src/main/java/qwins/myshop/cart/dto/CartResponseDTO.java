package qwins.myshop.cart.dto;

import qwins.myshop.cart.Cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        Long Id,
        Long UserId,
        List<CartItemResponseDTO> items,
        BigDecimal totalPrice
) {
    public CartResponseDTO(Cart cart) {
        this(
                cart.getId(),
                cart.getUser().getId(),
                cart.getItems().stream().map(CartItemResponseDTO::new).toList(),
                cart.getItems().stream().
                        map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).
                        reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }

}
