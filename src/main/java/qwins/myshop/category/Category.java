package qwins.myshop.category;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import qwins.myshop.attribute.AttributeRule;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column
    private List<AttributeRule> allowedAttributes = new ArrayList<>();

}
