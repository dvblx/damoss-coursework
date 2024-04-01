package ru.melnikov.computershop.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "computer_shop_laptop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laptop {
    @Id
    private UUID id;
    @Column(name = "processor_frequency")
    private Float speed;
    @Column(name = "ram_volume")
    private Integer ram;
    @Column(name = "hard_disk_memory_volume")
    private Integer hd;
    @Column(name = "monitor_diagonal_size")
    private Float screen;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductData productData;
}
