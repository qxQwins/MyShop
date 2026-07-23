package qwins.myshop.product;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import qwins.myshop.attribute.AttributeRule;
import qwins.myshop.category.Category;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Category category;

    @Column
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column
    private Map<String, Object> attributes = new HashMap<>();

    public void validateAttributes() {
        if (category == null) {
            throw new IllegalStateException("Cannot validate attributes: category is null.");
        }

        Map<String, AttributeRule> rulesMap = category.getAllowedAttributes().stream()
                .collect(Collectors.toMap(AttributeRule::getCode, rule -> rule));

        // Проверяем каждый атрибут, который передал пользователь/фронтенд
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String attributeCode = entry.getKey();
            Object attributeValue = entry.getValue();

            // 1. Проверка: Разрешено ли вообще это свойство в данной категории?
            if (!rulesMap.containsKey(attributeCode)) {
                throw new IllegalArgumentException(
                        String.format("Attribute '%s' cannot be used for category '%s'", attributeCode, category.getName())
                );
            }

            AttributeRule rule = rulesMap.get(attributeCode);

            // 2. Проверка типов данных
            switch (rule.getType()) {
                case NUMBER:
                    if (!(attributeValue instanceof Number)) {
                        throw new IllegalArgumentException(String.format("Attribute '%s' has to be a number", attributeCode));
                    }
                    break;
                case BOOLEAN:
                    if (!(attributeValue instanceof Boolean)) {
                        throw new IllegalArgumentException(String.format("Attribute '%s' has to be true or false", attributeCode));
                    }
                    break;
                case STRING:
                    if (!(attributeValue instanceof String)) {
                        throw new IllegalArgumentException(String.format("Attribute '%s' has to be a string", attributeCode));
                    }
                    break;
            }
        }
    }
}
