package qwins.myshop.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeRule {
    private String code;

    private String name;

    private AttributeType type;
}
