--liquibase formatted sql
--changeset Artem_Melnikov:1

create table if not exists computer_shop_product(
    id UUID,
    model varchar(100),
    price decimal,
    product_type varchar(30),
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

--rollback DROP TABLE IF EXISTS computer_shop_product CASCADE;

--changeset Artem_Melnikov:2

COMMENT ON TABLE computer_shop_product IS 'Таблица для хранения данных о товарах';
COMMENT ON COLUMN computer_shop_product.id IS 'ID товара';
COMMENT ON COLUMN computer_shop_product.model IS 'Название товара';
COMMENT ON COLUMN computer_shop_product.price IS 'Стоимость товара';
COMMENT ON COLUMN computer_shop_product.product_type IS 'Тип товара';

-- rollback empty

--changeset Artem_Melnikov:9

INSERT INTO computer_shop_product (id, model, price, product_type)
values ('a517604e-d4b4-4fc8-a144-522002d34886', 'Personal Computer 1', 100999.00, 'PERSONAL_COMPUTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('2b64b5d2-1124-4368-9b41-2502ce6a4ebb', 'Personal Computer 2', 39999.00, 'PERSONAL_COMPUTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('6bc4a745-eae9-49d5-98f7-d7562d7282b7', 'Personal Computer 3', 115999.00, 'PERSONAL_COMPUTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('7ab7fa6f-bf6f-4b71-9897-8e29adf0ffcb', 'Personal Computer 4', 30999.00, 'PERSONAL_COMPUTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('b88668d8-55da-460b-a716-0228502b4edb', 'Personal Computer 5', 25999.00, 'PERSONAL_COMPUTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('8baa8614-8ffc-4924-85b5-8b9e47d5ae08', 'Laptop 1', 29000.00, 'LAPTOP');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('5acf0a2c-eade-46fd-a732-eef7271a2cda', 'Laptop 2', 49999.00, 'LAPTOP');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('c0f4d115-c853-47de-88eb-0566accc6914', 'Laptop 3', 65999.00, 'LAPTOP');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('c43303fd-3758-4f65-8623-50806eb9cdcd', 'Laptop 4', 85999.00, 'LAPTOP');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('b9e62d53-ac6e-4d50-b532-4ee92a08d1c1', 'Printer 1', 5999.00, 'PRINTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('eb6cbc4f-50dd-4df4-81d4-bb21985bd7cf', 'Printer 2', 15999.00, 'PRINTER');
INSERT INTO computer_shop_product (id, model, price, product_type)
values ('34d03ce2-23ae-4a94-98e3-63d9de0ed007', 'Printer 3', 29999.00, 'PRINTER');

--rollback DELETE FROM computer_shop_product CASCADE;
