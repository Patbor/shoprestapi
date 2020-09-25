package org.patbor.shoprestapi.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TransactionDetails")
public class TransactionDetail {

    @Id
    @Column(name = "t_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transactions;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product products;

    private int amount;

    @Column(name = "value_netto")
    private BigDecimal valueNetto;

    @Column(name = "value_brutto")
    private BigDecimal valueBrutto;



}
