package com.oluwaseun.airtimepayment.webclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oluwaseun.airtimepayment.Exception.ValidationException;
import com.oluwaseun.airtimepayment.config.ApplicationConfig;
import com.oluwaseun.airtimepayment.util.PaymentHashGenerator;
import com.oluwaseun.airtimepayment.webclient.dto.AirtimeVTUWebClientRequest;
import com.oluwaseun.airtimepayment.webclient.dto.AirtimeVTUWebClientResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@RequiredArgsConstructor
@Service
@Data
@Slf4j
public class AirtimeVTUWebClient {
    private WebClient webClient;

    private final ApplicationConfig applicationConfig;

    @Value("${AIRTIME-VTU-API-URL-VALUE}")
    private String apiUrl;

    @Value("${AIRTIME-VTU-API-PUBLIC-KEY-VALUE}")
    private String publicKey;
    @Value("${AIRTIME-VTU-API-PRIVATE-KEY-VALUE}")
    private String privateKey ;

    private static final ObjectMapper mapper = new ObjectMapper();


    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public AirtimeVTUWebClientResponse sendAirtimeVTURequest(AirtimeVTUWebClientRequest airtimeVTUWebClientRequest) throws JsonProcessingException {
        try {

            //serialize request body to json
            String requestBody = null;
            try {
                requestBody = mapper.writeValueAsString(airtimeVTUWebClientRequest);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            //calculate payment hash
            String hmac512 = PaymentHashGenerator.calculateHMAC512(requestBody, privateKey);

            //build headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", String.format("Bearer %s", publicKey));
            headers.set("PaymentHash", hmac512);


            //make API call
            AirtimeVTUWebClientResponse response = webClient.post()
                    .uri(apiUrl)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .bodyValue(airtimeVTUWebClientRequest)
                    .retrieve()
                    .bodyToMono(AirtimeVTUWebClientResponse.class)
                    .block();

            if (response != null && response.getResponseCode().equals("00")) {
                return response;
            }
        } catch (WebClientResponseException ex) {
            log.error("Request failed with status code: {}", ex.getRawStatusCode());
            log.error("Response Body: {}", ex.getResponseBodyAsString());
            AirtimeVTUWebClientResponse response = mapper.readValue(ex.getResponseBodyAsString(), AirtimeVTUWebClientResponse.class);
                throw new ValidationException(response.getResponseMessage());
        } catch (WebClientRequestException ex) {
            log.error("An error occurred {}", ex.getMessage());
            throw new RuntimeException("An error occurred", ex.getCause());
        }
        return null;
    }
}
