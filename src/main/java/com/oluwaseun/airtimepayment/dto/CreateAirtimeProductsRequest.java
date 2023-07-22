package com.oluwaseun.airtimepayment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateAirtimeProductsRequest {
    @NotBlank(message = "network provider cannot be blank")
    private String networkProvider;
    @NotBlank(message = "productCode cannot be blank")
    private String productCode;
    @NotNull(message = "min amount cannot be null")
    private Integer minAmount;
    @NotNull(message = "max amount cannot be null")
    private Integer maxAmount;
}
