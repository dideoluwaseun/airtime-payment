package com.oluwaseun.airtimepayment.webclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AirtimeVTUWebClientRequest {
    public String requestId;
    public String uniqueCode;
    public Details details;
}
