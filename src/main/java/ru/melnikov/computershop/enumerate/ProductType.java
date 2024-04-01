package ru.melnikov.computershop.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProductType {
    PERSONAL_COMPUTER("Персональный комтьютер"),
    LAPTOP("Ноутбук"),
    PRINTER("Принтер");

    private String value;

    public String getUrl(){
        switch (this){
            case PRINTER -> {
                return "/product/printer";
            }
            case LAPTOP -> {
                return "/product/laptop";
            }
            default -> {
                return "/product/personal-computer";
            }
        }
    }

}
