package com.oluwaseun.airtimepayment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.oluwaseun.airtimepayment.Exception.DuplicateEntityException;
import com.oluwaseun.airtimepayment.domain.AirtimeProduct;
import com.oluwaseun.airtimepayment.dto.CreateAirtimeProductsRequest;
import com.oluwaseun.airtimepayment.dto.PurchaseAirtimeRequest;
import com.oluwaseun.airtimepayment.repository.AirtimeProductRepository;
import com.oluwaseun.airtimepayment.webclient.AirtimeVTUWebClient;
import com.oluwaseun.airtimepayment.webclient.dto.AirtimeVTUWebClientResponse;
import com.oluwaseun.airtimepayment.webclient.dto.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AirtimeProductServiceImplTest {

    @Mock
    private AirtimeProductRepository airtimeProductRepository;
    @Mock
    private AirtimeVTUWebClient airtimeVTUWebClient;

    @InjectMocks
    private AirtimeProductServiceImpl airtimeProductService;

    @Nested
    class PurchaseAirtime {

        PurchaseAirtimeRequest request;

        @BeforeEach
        void initObject() {
            //given
            request = PurchaseAirtimeRequest.builder().amount(100).networkProvider("MTN")
                    .phoneNumber("09012345678").build();

        }
        @Test
        void purchaseAirtime() throws JsonProcessingException {
            //when
            when(airtimeProductRepository.findByNetworkProvider(anyString())).thenReturn(Optional.
                    of(AirtimeProduct.builder().networkProvider("MTN").productCode("MTN_123").minAmount(100).maxAmount(100).build()));
            when(airtimeVTUWebClient.sendAirtimeVTURequest(any())).thenReturn(AirtimeVTUWebClientResponse.builder()
                    .responseMessage("successful")
                    .referenceId("12345678QERTY")
                    .data(Data.builder().phoneNumber("09012345678").amount(100).build())
                    .build());

            //then
            assertThatCode(() -> airtimeProductService.purchaseAirtime(request)).doesNotThrowAnyException();
        }

        @Test
        void catchException() throws JsonProcessingException {
            //when
            when(airtimeProductRepository.findByNetworkProvider(anyString())).thenReturn(Optional.
                    of(AirtimeProduct.builder().networkProvider("MTN").productCode("MTN_123").minAmount(100).maxAmount(100).build()));
            when(airtimeVTUWebClient.sendAirtimeVTURequest(any())).thenThrow(WebClientResponseException.class);

            //then
            assertThrows(RuntimeException.class, () -> airtimeProductService.purchaseAirtime(request));
        }

    }


    @Nested
    class CreateAirtimeProducts {

        CreateAirtimeProductsRequest request;
        @BeforeEach
        void initObject() {
            //given
            request = CreateAirtimeProductsRequest.builder().productCode("ACG1234").networkProvider("GLO")
                    .maxAmount(10000).minAmount(100).build();

        }
        @Test
        void createAirtimeProducts() {
            //when
            when(airtimeProductRepository.findByNetworkProvider(anyString())).thenReturn(Optional.empty());

            //then
            assertThatCode(() -> airtimeProductService.createAirtimeProducts(request)).doesNotThrowAnyException();

        }

        @Test
        void catchException() {
            //when
            when(airtimeProductRepository.findByNetworkProvider(anyString())).thenReturn(Optional.of(AirtimeProduct.builder().build()));

            //then
            assertThrows(DuplicateEntityException.class, () -> airtimeProductService.createAirtimeProducts(request));

        }
    }
}