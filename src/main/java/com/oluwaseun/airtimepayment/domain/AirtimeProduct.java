package com.oluwaseun.airtimepayment.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "airtime_products")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirtimeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String networkProvider;
    private String productCode;
    private Integer minAmount;
    private Integer maxAmount;
}
