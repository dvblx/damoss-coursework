package ru.melnikov.computershop.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.melnikov.computershop.enumerate.CdType;

import java.util.UUID;

@Entity
@Table(name = "computer_shop_personal_computer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalComputer {
    @Id
    private UUID id;
    @Column(name = "processor_frequency")
    private Float speed;
    @Column(name = "ram_volume")
    private Integer ram;
    @Column(name = "hard_disk_memory_volume")
    private Integer hd;
    @Enumerated(EnumType.STRING)
    private CdType cdType;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "product_id")
    private ProductData productData;
}
