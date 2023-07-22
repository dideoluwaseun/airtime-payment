package com.oluwaseun.airtimepayment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PurchaseAirtimeRequest {
    @NotBlank(message = "network provider cannot be empty")
    private String networkProvider;

    @NotNull(message = "amount cannot be null")
    private Integer amount;

    @NotBlank(message = "phone number cannot be empty")
    private String phoneNumber;
}
