package com.oluwaseun.airtimepayment.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    public String channel;
    public double amount;
    public String phoneNumber;
}
