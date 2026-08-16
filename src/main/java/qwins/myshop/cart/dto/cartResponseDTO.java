package qwins.myshop.cart.dto;

import qwins.myshop.cart.items.CartItem;
import qwins.myshop.user.User;

import java.util.List;

public class cartResponseDTO {
    private Long Id;
    private User user;
    private List<CartItem> items;
}
