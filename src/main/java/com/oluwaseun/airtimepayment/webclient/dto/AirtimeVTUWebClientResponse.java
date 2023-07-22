package com.oluwaseun.airtimepayment.webclient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirtimeVTUWebClientResponse {
    public String requestId;
    public String referenceId;
    public String responseCode;
    public String responseMessage;
    public Data data;
}
