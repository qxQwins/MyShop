package qwins.myshop.order;

import jakarta.persistence.*;
import lombok.*;
import qwins.myshop.product.Product;

import java.math.BigDecimal;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column
    private int quantity;

    @Column
    private BigDecimal priceAtPurchase;
}
