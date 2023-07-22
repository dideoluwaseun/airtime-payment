package com.oluwaseun.airtimepayment.service.impl;

import com.oluwaseun.airtimepayment.Exception.DuplicateEntityException;
import com.oluwaseun.airtimepayment.Exception.EntityNotFoundException;
import com.oluwaseun.airtimepayment.Exception.ValidationException;
import com.oluwaseun.airtimepayment.domain.AirtimeProduct;
import com.oluwaseun.airtimepayment.dto.CreateAirtimeProductsRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeResponse;
import com.oluwaseun.airtimepayment.repository.AirtimeProductRepository;
import com.oluwaseun.airtimepayment.service.AirtimeProductService;
import com.oluwaseun.airtimepayment.webclient.AirtimeVTUWebClient;
import com.oluwaseun.airtimepayment.webclient.dto.AirtimeVTUWebClientRequest;
import com.oluwaseun.airtimepayment.webclient.dto.AirtimeVTUWebClientResponse;
import com.oluwaseun.airtimepayment.webclient.dto.Details;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirtimeProductServiceImpl implements AirtimeProductService {

    private final AirtimeProductRepository airtimeProductRepository;
    private final AirtimeVTUWebClient airtimeVTUWebClient;

    @Override
    public PurchaseAirtimeResponse purchaseAirtime(PurchaseAirtimeRequest request) {
        log.info("processing purchase airtime request");
        //check if network provider and product exists
        Optional<AirtimeProduct> optionalAirtimeProduct = airtimeProductRepository.findByNetworkProvider(request.getNetworkProvider());

        AirtimeProduct airtimeProduct = optionalAirtimeProduct.orElseThrow(() -> new EntityNotFoundException("Network provider does not exist"));

        //check if amount is greater than min amount and less than max amount
        Integer amount = request.getAmount();
        if (amount < airtimeProduct.getMinAmount() || amount > airtimeProduct.getMaxAmount()) {
            throw new ValidationException("Value must be greater than " + airtimeProduct.getMinAmount() + " and less than " + airtimeProduct.getMaxAmount());
        }

        try {
            // call airtime VTU API
            UUID uuid = UUID.randomUUID();
            AirtimeVTUWebClientResponse airtimeVTUWebClientResponse = airtimeVTUWebClient.sendAirtimeVTURequest(AirtimeVTUWebClientRequest.builder()
                    .requestId(uuid.toString())
                    .uniqueCode(airtimeProduct.getProductCode())
                    .details(Details.builder()
                            .amount(amount)
                            .phoneNumber(request.getPhoneNumber())
                            .build())
                    .build());

            log.info("done processing purchase airtime request");

            //map API response
            return PurchaseAirtimeResponse.builder()
                        .responseMessage(airtimeVTUWebClientResponse.responseMessage)
                        .referenceId(airtimeVTUWebClientResponse.referenceId)
                        .phoneNumber(airtimeVTUWebClientResponse.data.phoneNumber)
                        .timestamp(Calendar.getInstance().getTime())
                        .amount(airtimeVTUWebClientResponse.data.amount)
                        .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createAirtimeProducts(CreateAirtimeProductsRequest request) {
        Optional<AirtimeProduct> optionalAirtimeProduct = airtimeProductRepository.findByNetworkProvider(request.getNetworkProvider());

        if(optionalAirtimeProduct.isPresent())
            throw new DuplicateEntityException("duplicate airtime product");

        airtimeProductRepository.save(AirtimeProduct.builder()
                .networkProvider(request.getNetworkProvider())
                .productCode(request.getProductCode())
                .minAmount(request.getMinAmount())
                .maxAmount(request.getMaxAmount())
                .build());
    }

}
