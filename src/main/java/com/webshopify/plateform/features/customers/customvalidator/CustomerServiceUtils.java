package com.webshopify.plateform.features.customers.customvalidator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceUtils {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${validator.email.apikey.key}")
    private String apikey;

    @Value("${validator.email.apikey.value}")
    private String apikeyvalue;

    public boolean isValidEmail(String emailID) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(apikey, apikeyvalue);
        headers.set("x-rapidapi-host", "global-email-v4.p.rapidapi.com");

        String url = "https://global-email-v4.p.rapidapi.com/v4/WEB/GlobalEmail/doGlobalEmail" +
                "?email=" + emailID +
                "&opt=VerifyMailbox%3AExpress%7CVerifyMailbox%3AExpressPremium&format=json";

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String body = response.getBody();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(body);

                JsonNode record = root.path("Records").get(0);

                String scoreStr = record.path("DeliverabilityConfidenceScore").asText();
                String resultCode = record.path("Results").asText();
                String domainAvailability = record.path("DomainAvailability").asText();

                int score = scoreStr.isEmpty() ? 0 : Integer.parseInt(scoreStr);

                // Define your rules here:
                return score >= 60 && !"unavailable".equalsIgnoreCase(domainAvailability)
                        && !resultCode.startsWith("EE");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
