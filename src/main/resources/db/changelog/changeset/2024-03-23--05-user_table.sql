--liquibase formatted sql
--changeset Artem_Melnikov:13

create table if not exists computer_shop_user(
    id UUID,
    username varchar(50),
    email varchar(255),
    password varchar(255),
    role varchar(20),
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

--rollback DROP TABLE IF EXISTS computer_shop_product CASCADE;

--changeset Artem_Melnikov:14

COMMENT ON TABLE computer_shop_user IS 'Таблица для хранения данных о товарах';
COMMENT ON COLUMN computer_shop_user.id IS 'ID пользователя';
COMMENT ON COLUMN computer_shop_user.username IS 'Уникальное имя пользователя';
COMMENT ON COLUMN computer_shop_user.email IS 'Адрес электронной почты';
COMMENT ON COLUMN computer_shop_user.password IS 'Пароль';
COMMENT ON COLUMN computer_shop_user.role IS 'Роль пользователя';

-- rollback empty