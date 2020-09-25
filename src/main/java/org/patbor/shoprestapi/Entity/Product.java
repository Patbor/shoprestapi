package org.patbor.shoprestapi.Entity;

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

    @OneToMany(mappedBy = "products")
    private List<TransactionDetail> transactionDetails;

    public void addDetails(TransactionDetail transactionDetail) {

        if(transactionDetails == null) {
            transactionDetails = new ArrayList<>();
        }
        if(transactionDetail != null) {
            transactionDetails.add(transactionDetail);
        }
        transactionDetail.setProducts(this);
    }
}