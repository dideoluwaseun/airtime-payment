package com.oluwaseun.airtimepayment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseAirtimeResponse {
    public String responseMessage;
    public String referenceId;
    public Date timestamp;
    public double amount;
    public String phoneNumber;
}
