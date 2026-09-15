
INSERT INTO category (id, name, allowed_attributes)
VALUES
    (1,'Электроника', '[
  {"name": "brand", "type": "STRING", "required": true},
  {"name": "ram", "type": "NUMBER", "required": false},
  {"name": "storage", "type": "NUMBER", "required": false}
]'),
    (2,'Одежда', '[
  {"name": "brand", "type": "STRING", "required": true},
  {"name": "size", "type": "STRING", "required": true},
  {"name": "material", "type": "STRING", "required": false}
]');

INSERT INTO product (name, description, price, category_id, attributes)
VALUES
    ('Смартфон iPhone 15', 'Флагманский телефон от Apple', 95000.00, 1, '{
  "brand": "Apple",
  "ram": 8,
  "storage": 128
}'),
    ('Ноутбук ThinkPad T14', 'Рабочая станция для программистов', 120000.00, 1, '{
  "brand": "Lenovo",
  "ram": 16,
  "storage": 512
}'),
    ('Худи Оверсайз', 'Уютная теплая кофта', 4500.00, 2, '{
  "brand": "MyShop-Brand",
  "size": "XL",
  "material": "Хлопок"
}');

--passwords hashed with BCrypt initially
INSERT INTO shop_user (username, password, role)
VALUES
    ('ivan_test', '$2a$10$fXG0w8g3XmK1N5v6W1P6O.pG6U6E7W6Z6Y6X6W6V6U6T6S6R6Q6P.', 'USER'),
    ('admin_test', '$2a$10$fXG0w8g3XmK1N5v6W1P6O.pG6U6E7W6Z6Y6X6W6V6U6T6S6R6Q6P.', 'ADMIN');
