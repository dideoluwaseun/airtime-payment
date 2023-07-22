package com.oluwaseun.airtimepayment.webclient.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Details {
    public String phoneNumber;
    public int amount;

}
