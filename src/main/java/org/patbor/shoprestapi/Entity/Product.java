package org.patbor.shoprestapi.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    @Column(name = "value_netto")
    private BigDecimal valueNetto;

    @Column(name = "value_brutto")
    private BigDecimal valueBrutto;

    @Column(name = "available_count")
    private int availableCount;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JsonIgnore
    private List<TransactionDetail> transactionDetails;
}
