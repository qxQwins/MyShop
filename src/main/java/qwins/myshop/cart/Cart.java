package qwins.myshop.cart;

import jakarta.persistence.*;
import lombok.*;
import qwins.myshop.cart.items.CartItem;
import qwins.myshop.product.Product;
import qwins.myshop.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    public void updateItemQuantity(Long productId, int quantity) {
        if(quantity <= 0) {
            removeItem(productId);
            return;
        }

        this.items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void removeItem(Long productId) {
        this.items.removeIf(cartItem -> cartItem.getId().equals(productId));
    }

    public void clearItems() {
        this.items.clear();
    }

    public int getItemQuantity(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .map(CartItem::getQuantity)
                .orElse(0);
    }
}
