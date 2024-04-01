package ru.melnikov.computershop.util;

import ru.melnikov.computershop.dto.LaptopDto;
import ru.melnikov.computershop.dto.ProductDto;
import ru.melnikov.computershop.dto.user.SignInRequest;
import ru.melnikov.computershop.dto.user.SignUpRequest;
import ru.melnikov.computershop.enumerate.CdType;
import ru.melnikov.computershop.enumerate.PrinterType;
import ru.melnikov.computershop.enumerate.ProductType;
import ru.melnikov.computershop.enumerate.Role;
import ru.melnikov.computershop.model.product.Laptop;
import ru.melnikov.computershop.model.product.PersonalComputer;
import ru.melnikov.computershop.model.product.Printer;
import ru.melnikov.computershop.model.product.ProductData;
import ru.melnikov.computershop.model.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public final class TestConstants {

    // Url
    public static final String BASE_API_URL = "/api/v1/%s";
    public static final String SIGN_UP_URL = String.format(BASE_API_URL, "/auth/sign-up");
    public static final String SIGN_IN_URL = String.format(BASE_API_URL, "/auth/sign-in");
    public static final String LAPTOP_URL = String.format(BASE_API_URL, "/product/laptop");
    public static final String BASE_LAPTOP_URL = String.format(BASE_API_URL, "/product/laptop/%s");
    public static final String PRINTER_URL = String.format(BASE_API_URL, "/product/printer");
    public static final String BASE_PRINTER_URL = String.format(BASE_API_URL, "/product/printer/%s");
    public static final String PERSONAL_COMPUTER_URL = String.format(BASE_API_URL, "/product/personal-computer");
    public static final String BASE_PERSONAL_COMPUTER_URL = String.format(BASE_API_URL, "/product/personal-computer/%s");

    // SQL
    public static final String TRUNCATE_CHANGELOG = "truncate databasechangelog cascade";
    public static final String TRUNCATE_CHANGELOGLOCK = "truncate databasechangeloglock cascade";
    public static final String TRUNCATE_PRODUCT_DATA = "truncate computer_shop_product cascade";
    public static final String TRUNCATE_PERSONAL_COMPUTER = "truncate computer_shop_personal_computer cascade";
    public static final String TRUNCATE_LAPTOP = "truncate computer_shop_laptop cascade";
    public static final String TRUNCATE_PRINTER = "truncate computer_shop_printer cascade";
    public static final String TRUNCATE_USER = "truncate computer_shop_user cascade";

    // Other
    public static final String BEARER_BASE = "Bearer %s";

    // Objects
    public static final Laptop TEST_LAPTOP = Laptop.builder()
            .id(UUID.randomUUID())
            .screen(15.6F)
            .speed(2F)
            .hd(256)
            .ram(8)
            .productData(ProductData.builder()
                    .id(UUID.randomUUID())
                    .modelName("some laptop")
                    .price(BigDecimal.valueOf(50000))
                    .productType(ProductType.LAPTOP)
                    .build())
            .build();

    public static final PersonalComputer TEST_PERSONAL_COMPUTER = PersonalComputer.builder()
            .id(UUID.randomUUID())
            .cdType(CdType.SSD)
            .hd(512)
            .ram(16)
            .speed(2.5F)
            .productData(ProductData.builder()
                    .id(UUID.randomUUID())
                    .modelName("some pc")
                    .price(BigDecimal.valueOf(60000))
                    .productType(ProductType.PERSONAL_COMPUTER)
                    .build())
            .build();

    public static final Printer TEST_PRINTER = Printer.builder()
            .id(UUID.randomUUID())
            .coloured(true)
            .type(PrinterType.LASER)
            .productData(ProductData.builder()
                    .id(UUID.randomUUID())
                    .modelName("some printer")
                    .price(BigDecimal.valueOf(5000))
                    .productType(ProductType.PRINTER)
                    .build())
            .build();

    public static final SignUpRequest TEST_SIGN_UP_REQUEST = SignUpRequest.builder()
            .username("testUsername")
            .password("testPassword")
            .email("test@mail.ru")
            .build();

    public static final SignInRequest TEST_SIGN_IN_REQUEST = SignInRequest.builder()
            .username(TEST_SIGN_UP_REQUEST.getUsername())
            .password(TEST_SIGN_UP_REQUEST.getPassword())
            .build();

    public static final User TEST_USER = User.builder()
            .id(UUID.randomUUID())
            .username("some name")
            .password("password")
            .email("foo@bar.com")
            .role(Role.ROLE_USER)
            .build();
}
