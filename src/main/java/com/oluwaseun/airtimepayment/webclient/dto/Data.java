package com.oluwaseun.airtimepayment.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Data {
    public String channel;
    public double amount;
    public String phoneNumber;
}
