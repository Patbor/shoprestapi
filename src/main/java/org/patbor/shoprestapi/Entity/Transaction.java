package org.patbor.shoprestapi.Entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionID;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @Column(name = "user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "value_netto")
    private BigDecimal valueNetto;

    @Column(name = "value_brutto")
    private BigDecimal valueBrutto;

    @OneToMany(mappedBy = "transactions")
    private List<TransactionDetail> transactionDetails;

    private LocalDate date;

    public void addDetails(TransactionDetail transactionDetail) {

        if(transactionDetails == null) {
            transactionDetails = new ArrayList<>();
        }
        if(transactionDetail != null) {
            transactionDetails.add(transactionDetail);
        }
        transactionDetail.setTransactions(this);
    }


}
