package com.oluwaseun.airtimepayment.service;

import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeResponse;

public interface AirtimeProductService {
    PurchaseAirtimeResponse purchaseAirtime(PurchaseAirtimeRequest purchaseAirtimeRequest);
}
