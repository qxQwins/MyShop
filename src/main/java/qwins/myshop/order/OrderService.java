package qwins.myshop.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qwins.myshop.cart.Cart;
import qwins.myshop.cart.CartService;
import qwins.myshop.cart.CartItem;
import qwins.myshop.product.Product;
import qwins.myshop.product.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, CartService cartService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    public Order checkout(long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }

        Order order = Order.builder()
                .user(cart.getUser())
                .status("NEW")
                .createdAt(new Date())
                .price(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            Product freshProduct = productService.getProductById(cartItem.getProduct().getId());
            BigDecimal priceAtPurchase = freshProduct.getPrice();

            BigDecimal itemCost = priceAtPurchase.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemCost);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(freshProduct)
                    .quantity(cartItem.getQuantity())
                    .priceAtPurchase(priceAtPurchase)
                    .build();

            orderItems.add(orderItem);
        }

        order.setPrice(totalPrice);
        order.getItems().addAll(orderItems);

        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getUser().getId());

        return savedOrder;
    }

    @Transactional
    public Order createOrder(Order order) {
        order.setId(null);
        return orderRepository.save(order);
    }

    public Order findOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NoSuchElementException("Order not found"));
    }

    public List<Order> findAllByUsersId(long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public Order updateOrderStatus(String status, long orderId) {
        Order order = findOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(long orderId) {
        if(orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        }
    }

}
