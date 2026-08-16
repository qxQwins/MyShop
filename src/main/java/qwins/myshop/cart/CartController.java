package qwins.myshop.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.cart.items.CartItem;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCartItems(@RequestParam Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addCartItem(@RequestParam Long userId,
                                            @RequestParam Long  productId,
                                            int quantity) {
        Cart cartWithNewItem = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartWithNewItem);
    }

    @PutMapping("/items")
    public ResponseEntity<Cart> updateCartItem(@RequestParam Long userId,
                                               @RequestParam Long  productId,
                                               int quantity) {
        Cart updatedCart = cartService.updateProductQuantity(userId,productId,quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(@RequestParam Long userId,
                                               @PathVariable Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItems(@RequestBody Cart cart) {
        cart.clearItems();
        return ResponseEntity.noContent().build();
    }

}