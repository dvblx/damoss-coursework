package ru.melnikov.computershop.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CdType {
    SSD("Твердотельный накопитель"),
    HDD("Жёсткий диск");

    private String value;
}
