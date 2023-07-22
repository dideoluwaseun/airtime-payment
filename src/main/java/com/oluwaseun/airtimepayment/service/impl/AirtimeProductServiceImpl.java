package com.oluwaseun.airtimepayment.service.impl;

import com.oluwaseun.airtimepayment.domain.AirtimeProduct;
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
        Optional<AirtimeProduct> optionalAirtimeProduct = airtimeProductRepository.findByNetworkProvider(request.getNetworkProvider());

        AirtimeProduct airtimeProduct = optionalAirtimeProduct.orElseThrow(() -> new RuntimeException("Value is not present!"));

        Integer amount = request.getAmount();
        if (amount < airtimeProduct.getMinAmount() || amount > airtimeProduct.getMaxAmount()) {
            throw new RuntimeException("Value must be greater than " + airtimeProduct.getMinAmount() + " and less than " + airtimeProduct.getMaxAmount());
        }

        try {
            UUID uuid = UUID.randomUUID();
            AirtimeVTUWebClientResponse airtimeVTUWebClientResponse = airtimeVTUWebClient.sendAirtimeVTURequest(AirtimeVTUWebClientRequest.builder()
                    .requestId(uuid.toString())
                    .uniqueCode(airtimeProduct.getProductCode())
                    .details(Details.builder()
                            .amount(amount)
                            .phoneNumber(request.getPhoneNumber())
                            .build())
                    .build());

            return PurchaseAirtimeResponse.builder()
                    .responseMessage(airtimeVTUWebClientResponse.responseMessage)
                    .referenceId(airtimeVTUWebClientResponse.referenceId)
                    .phoneNumber(airtimeVTUWebClientResponse.data.phoneNumber)
                    .timestamp(Calendar.getInstance().getTime())
                    .amount(airtimeVTUWebClientResponse.data.amount)
                    .build();

        } catch (Exception e) {
            log.error("");
        }
        return null;
    }
}
