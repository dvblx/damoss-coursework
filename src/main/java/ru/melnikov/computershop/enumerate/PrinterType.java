package ru.melnikov.computershop.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum PrinterType {
    LASER("Лазерный"),
    MATRIX("Матричный"),
    JET("Струйный"),
    MULTIFUNCTIONAL_DEVICE("Многофункциональное устройство"),
    THREE_DIMENSIONAL("3Д-Принтер");

    private String value;
}
