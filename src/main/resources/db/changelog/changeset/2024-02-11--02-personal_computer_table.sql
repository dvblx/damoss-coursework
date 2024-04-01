--liquibase formatted sql
--changeset Artem_Melnikov:3

create table if not exists computer_shop_personal_computer(
    processor_frequency float,
    ram_volume decimal,
    hard_disk_memory_volume integer,
    cd_type varchar(50),
    product_id UUID,
    CONSTRAINT personal_computer_pkey PRIMARY KEY (product_id),
    CONSTRAINT personal_computer_fkey FOREIGN KEY (product_id) REFERENCES computer_shop_product (id)

);

--rollback DROP TABLE IF EXISTS computer_shop_personal_computer CASCADE;

--changeset Artem_Melnikov:4

COMMENT ON TABLE computer_shop_personal_computer IS 'Таблица для хранения данных о персональных компьютерах';
COMMENT ON COLUMN computer_shop_personal_computer.product_id IS 'ID компьютера, по совместительству id товара';
COMMENT ON COLUMN computer_shop_personal_computer.processor_frequency IS 'Быстродействие процессора';
COMMENT ON COLUMN computer_shop_personal_computer.ram_volume IS 'Объем оперативной памяти';
COMMENT ON COLUMN computer_shop_personal_computer.hard_disk_memory_volume IS 'Объем памяти жесткого диска';
COMMENT ON COLUMN computer_shop_personal_computer.cd_type IS 'Тип cd-диска';

-- rollback empty

--changeset Artem_Melnikov:10

INSERT INTO computer_shop_personal_computer (processor_frequency, ram_volume, hard_disk_memory_volume, cd_type, product_id)
 values (3.3, 32, 1024, 'SSD', 'a517604e-d4b4-4fc8-a144-522002d34886');
INSERT INTO computer_shop_personal_computer (processor_frequency, ram_volume, hard_disk_memory_volume, cd_type, product_id)
 values (2, 8, 256, 'SSD', '2b64b5d2-1124-4368-9b41-2502ce6a4ebb');
INSERT INTO computer_shop_personal_computer (processor_frequency, ram_volume, hard_disk_memory_volume, cd_type, product_id)
 values (3.8, 32, 1024, 'SSD', '6bc4a745-eae9-49d5-98f7-d7562d7282b7');
INSERT INTO computer_shop_personal_computer (processor_frequency, ram_volume, hard_disk_memory_volume, cd_type, product_id)
 values (2, 8, 128, 'SSD', '7ab7fa6f-bf6f-4b71-9897-8e29adf0ffcb');
INSERT INTO computer_shop_personal_computer (processor_frequency, ram_volume, hard_disk_memory_volume, cd_type, product_id)
 values (1.1, 8, 128, 'HDD', 'b88668d8-55da-460b-a716-0228502b4edb');

--rollback DELETE FROM computer_shop_personal_computer CASCADE;