--liquibase formatted sql
--changeset Artem_Melnikov:5

create table if not exists computer_shop_laptop(
    processor_frequency float,
    ram_volume decimal,
    hard_disk_memory_volume integer,
    monitor_diagonal_size float,
    product_id UUID,
    CONSTRAINT laptop_pkey PRIMARY KEY (product_id),
    CONSTRAINT laptop_fkey FOREIGN KEY (product_id) REFERENCES computer_shop_product (id)

);

--rollback DROP TABLE IF EXISTS computer_shop_personal_computer CASCADE;

--changeset Artem_Melnikov:6

COMMENT ON TABLE computer_shop_laptop IS 'Таблица для хранения данных о ноутбуках';
COMMENT ON COLUMN computer_shop_laptop.product_id IS 'ID ноутбука, по совместительству id товара';
COMMENT ON COLUMN computer_shop_laptop.processor_frequency IS 'Быстродействие процессора';
COMMENT ON COLUMN computer_shop_laptop.ram_volume IS 'Объем оперативной памяти';
COMMENT ON COLUMN computer_shop_laptop.hard_disk_memory_volume IS 'Объем памяти жесткого диска';
COMMENT ON COLUMN computer_shop_laptop.monitor_diagonal_size IS 'Размер диагонали монитора';

-- rollback empty

--changeset Artem_Melnikov:11

INSERT INTO computer_shop_laptop (processor_frequency, ram_volume, hard_disk_memory_volume, monitor_diagonal_size, product_id)
VALUES (1.1, 8, 256, 14.1, '8baa8614-8ffc-4924-85b5-8b9e47d5ae08');
INSERT INTO computer_shop_laptop (processor_frequency, ram_volume, hard_disk_memory_volume, monitor_diagonal_size, product_id)
VALUES (2, 8, 256, 15.6, '5acf0a2c-eade-46fd-a732-eef7271a2cda');
INSERT INTO computer_shop_laptop (processor_frequency, ram_volume, hard_disk_memory_volume, monitor_diagonal_size, product_id)
VALUES (1.5, 6, 128, 15.6, 'c0f4d115-c853-47de-88eb-0566accc6914');
INSERT INTO computer_shop_laptop (processor_frequency, ram_volume, hard_disk_memory_volume, monitor_diagonal_size, product_id)
VALUES (2.7, 16, 512, 17.6, 'c43303fd-3758-4f65-8623-50806eb9cdcd');

-- rollback DELETE FROM computer_shop_laptop CASCADE;