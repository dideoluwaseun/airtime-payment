package com.oluwaseun.airtimepayment.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirtimeVTUWebClientRequest {
    public String requestId;
    public String uniqueCode;
    public Details details;
}
