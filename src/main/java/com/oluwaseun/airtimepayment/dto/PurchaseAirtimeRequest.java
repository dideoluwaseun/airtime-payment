package com.oluwaseun.airtimepayment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseAirtimeRequest {
    @NotBlank(message = "network provider cannot be empty")
    private String networkProvider;

    @NotNull(message = "amount cannot be null")
    private Integer amount;

    @NotBlank(message = "phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{11}$", message = "phone number must be 11 digits long and not contain alphabets")
    private String phoneNumber;
}
