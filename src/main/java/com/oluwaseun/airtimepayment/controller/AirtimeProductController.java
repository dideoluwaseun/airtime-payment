package com.oluwaseun.airtimepayment.controller;

import com.oluwaseun.airtimepayment.dto.CreateAirtimeProductsRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeResponse;
import com.oluwaseun.airtimepayment.service.AirtimeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/airtime-products")
@RequiredArgsConstructor
public class AirtimeProductController {
    private final AirtimeProductService airtimeProductService;

    @PostMapping("purchase")
    public ResponseEntity<PurchaseAirtimeResponse> purchaseAirtime(@RequestBody PurchaseAirtimeRequest request) {
        PurchaseAirtimeResponse purchaseAirtimeResponse = airtimeProductService.purchaseAirtime(request);
        return new ResponseEntity<>(purchaseAirtimeResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Void> createAirtimeProduct(@Valid @RequestBody CreateAirtimeProductsRequest request) {
        airtimeProductService.createAirtimeProducts(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
