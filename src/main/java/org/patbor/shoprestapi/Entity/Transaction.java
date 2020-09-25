package org.patbor.shoprestapi.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "value_netto")
    private BigDecimal valueNetto;

    @Column(name = "value_brutto")
    private BigDecimal valueBrutto;

    @OneToMany(mappedBy = "transaction")
    @JsonIgnore
    private List<TransactionDetail> transactionDetails;

    private LocalDate date;

    public Transaction(User user, BigDecimal valueNetto, BigDecimal valueBrutto,LocalDate date ) {
        this.user = user;
        this.valueNetto = valueNetto;
        this.valueBrutto = valueBrutto;
        this.date = date;
    }

    public void addDetails(TransactionDetail transactionDetail) {

        if(transactionDetails == null) {
            transactionDetails = new ArrayList<>();
        }
        if(transactionDetail != null) {
            transactionDetails.add(transactionDetail);
        }
        transactionDetail.setTransaction(this);

    }


}
