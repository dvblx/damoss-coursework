package ru.melnikov.computershop.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.melnikov.computershop.enumerate.PrinterType;

import java.util.UUID;

@Entity
@Table(name = "computer_shop_printer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Printer {
    @Id
    private UUID id;
    @Column(name = "is_coloured")
    private Boolean coloured;
    @Column(name = "printer_type")
    @Enumerated(EnumType.STRING)
    private PrinterType type;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductData productData;
}
