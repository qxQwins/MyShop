package qwins.myshop.cart;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qwins.myshop.product.Product;
import qwins.myshop.product.ProductService;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .build();
                    return cartRepository.save(newCart);
                });
    }

    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productService.getProductById(productId);

        cart.updateItemQuantity(product.getId(), cart.getItemQuantity(product.getId()) + quantity);

        return cartRepository.save(cart);
    }

    public Cart updateProductQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cart.updateItemQuantity(productId, quantity);
        return cartRepository.save(cart);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.removeItem(productId);
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.clearItems();
        cartRepository.save(cart);
    }

}
