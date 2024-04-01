--liquibase formatted sql
--changeset Artem_Melnikov:7

create table if not exists computer_shop_printer(
    is_coloured boolean,
    printer_type varchar(50),
    product_id UUID,
    CONSTRAINT printer_pkey PRIMARY KEY (product_id),
    CONSTRAINT printer_fkey FOREIGN KEY (product_id) REFERENCES computer_shop_product (id)

);

--rollback DROP TABLE IF EXISTS computer_shop_personal_computer CASCADE;

--changeset Artem_Melnikov:8

COMMENT ON TABLE computer_shop_printer IS 'Таблица для хранения данных о ноутбуках';
COMMENT ON COLUMN computer_shop_printer.product_id IS 'ID принтера, по совместительству id товара';
COMMENT ON COLUMN computer_shop_printer.is_coloured IS 'True - цветной, false - чб';
COMMENT ON COLUMN computer_shop_printer.printer_type IS 'Тип печатающего устройства';

-- rollback empty

--changeset Artem_Melnikov:12

INSERT INTO computer_shop_printer (is_coloured, printer_type, product_id)
values (false, 'MULTIFUNCTIONAL_DEVICE', 'b9e62d53-ac6e-4d50-b532-4ee92a08d1c1');

INSERT INTO computer_shop_printer (is_coloured, printer_type, product_id)
values (true, 'LASER', 'eb6cbc4f-50dd-4df4-81d4-bb21985bd7cf');

INSERT INTO computer_shop_printer (is_coloured, printer_type, product_id)
values (true, 'THREE_DIMENSIONAL', '34d03ce2-23ae-4a94-98e3-63d9de0ed007');

-- rollback DELETE FROM computer_shop_laptop CASCADE;
