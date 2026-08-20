package qwins.myshop.cart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qwins.myshop.cart.dto.CartResponseDTO;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCartItems(@RequestParam Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(new CartResponseDTO(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDTO> addCartItem(@RequestParam Long userId,
                                            @RequestParam Long productId,
                                            int quantity) {
        Cart cartWithNewItem = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(new CartResponseDTO(cartWithNewItem));
    }

    @PutMapping("/items")
    public ResponseEntity<CartResponseDTO> updateCartItem(@RequestParam Long userId,
                                                              @RequestParam Long productId,
                                                              int quantity) {
        Cart updatedCart = cartService.updateProductQuantity(userId,productId,quantity);
        return ResponseEntity.ok(new CartResponseDTO(updatedCart));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(@RequestParam Long userId,
                                               @PathVariable Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCartItems(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

}