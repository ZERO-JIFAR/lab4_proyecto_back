package com.example.apirest.controllers;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class MercadoPagoController {

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    // Recibe un array de items con cantidad y precio unitario
    @PostMapping("/mp")
    @CrossOrigin("*")
    public ResponseEntity<String> mp(@RequestBody Map<String, Object> body) throws Exception {
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        // Recibe: { items: [{id, title, quantity, unit_price, ...}], orderId? }
        List<Map<String, Object>> itemsFromBody = (List<Map<String, Object>>) body.get("items");
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (Map<String, Object> item : itemsFromBody) {
            String id = String.valueOf(item.get("id"));
            String title = String.valueOf(item.get("title"));
            String description = item.get("description") != null ? String.valueOf(item.get("description")) : "";
            int quantity = Integer.parseInt(String.valueOf(item.get("quantity")));
            BigDecimal unitPrice = new BigDecimal(String.valueOf(item.get("unit_price")));

            PreferenceItemRequest prefItem = PreferenceItemRequest.builder()
                    .id(id)
                    .title(title)
                    .description(description)
                    .quantity(quantity)
                    .currencyId("ARS")
                    .unitPrice(unitPrice)
                    .build();
            items.add(prefItem);
        }

        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("https://localhost:5173/paymentSuccess")
                        .pending("https://localhost:5173/")
                        .failure("https://localhost:5173/paymentFailure")
                        .build();

        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
                .excludedPaymentTypes(excludedPaymentTypes)
                .installments(1)
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .paymentMethods(paymentMethods)
                .autoReturn("approved")
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        String prefId = preference.getId();

        System.out.println("URL de pago: " + preference.getInitPoint());

        return ResponseEntity.status(HttpStatus.OK).body("{\"preferenceId\":\""+prefId+"\"}");
    }
}