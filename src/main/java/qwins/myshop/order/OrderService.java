package qwins.myshop.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qwins.myshop.cart.Cart;
import qwins.myshop.cart.CartService;
import qwins.myshop.cart.items.CartItem;
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
        // 1. Достаем актуальную корзину пользователя
        Cart cart = cartService.getCartByUserId(userId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty cart");
        }

        // 2. Создаем "шапку" заказа
        Order order = Order.builder()
                .user(cart.getUser())
                .status("NEW") // Начальный статус заказа
                .createdAt(new Date())
                .price(BigDecimal.ZERO) // Посчитаем в цикле ниже
                .items(new ArrayList<>())
                .build();

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // 3. Конвертируем CartItem в OrderItem
        for (CartItem cartItem : cart.getItems()) {
            // Запрашиваем свежую цену из каталога продуктов (ЗАМОРАЖИВАЕМ цену на момент покупки)
            Product freshProduct = productService.getProductById(cartItem.getProduct().getId());
            BigDecimal priceAtPurchase = freshProduct.getPrice();

            // Вычисляем сумму по этой позиции
            BigDecimal itemCost = priceAtPurchase.multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            totalPrice = totalPrice.add(itemCost);

            // Собираем строку заказа
            OrderItem orderItem = OrderItem.builder()
                    .order(order) // Привязываем к родителю (двунаправленная связь)
                    .product(freshProduct)
                    .quantity(cartItem.getQuantity())
                    .priceAtPurchase(priceAtPurchase)
                    .build();

            orderItems.add(orderItem);
        }

        // 4. Накатываем посчитанные данные на объект заказа
        order.setPrice(totalPrice);
        order.getItems().addAll(orderItems);

        // 5. Сохраняем в БД. Каскад сохранит и сам Order, и все его OrderItems
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
