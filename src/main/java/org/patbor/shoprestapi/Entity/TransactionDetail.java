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
@Table(name = "Transactiondetails")
public class TransactionDetail {

    @Id
    @Column(name = "t_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;

    private int amount;

    @Column(name = "value_netto")
    private BigDecimal valueNetto;

    @Column(name = "value_brutto")
    private BigDecimal valueBrutto;

    public TransactionDetail(Product product, int amount,  BigDecimal valueNetto, BigDecimal valueBrutto) {
        this.product = product;
        this.amount = amount;
        this.valueBrutto = valueBrutto;
        this.valueNetto = valueNetto;
    }

}
