package com.oluwaseun.airtimepayment.controller;

import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeResponse;
import com.oluwaseun.airtimepayment.service.AirtimeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/airtime-products")
@RequiredArgsConstructor
public class AirtimeProductController {
    private final AirtimeProductService airtimeProductService;

    @PostMapping("purchase")
    public ResponseEntity<PurchaseAirtimeResponse> purchaseAirtime(@RequestBody PurchaseAirtimeRequest request) {
        airtimeProductService.purchaseAirtime(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
