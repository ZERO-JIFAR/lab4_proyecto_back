package com.example.apirest.controllers;

import com.example.apirest.entities.DetalleOrden;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.entities.Producto;
import com.example.apirest.services.OrdenDeCompraService;
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
    private final OrdenDeCompraService ordenDeCompraService;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    public MercadoPagoController(OrdenDeCompraService ordenDeCompraService) {
        this.ordenDeCompraService = ordenDeCompraService;
    }

    @PostMapping("/mp")
    @CrossOrigin("*")
    public ResponseEntity<String> mp(@RequestBody Map<String, List<Long>> body) throws Exception {
        List<Long> productIds = body.get("id");
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
        List<PreferenceItemRequest> items = new ArrayList<>();

        OrdenDeCompra ordenDeCompra = ordenDeCompraService.generarOrdenCompra(productIds);

        for (DetalleOrden detalle : ordenDeCompra.getDetalle()) {
            Producto producto = detalle.getProducto();
            BigDecimal precioFinal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .id(producto.getId().toString())
                    .title(producto.getNombre())
                    .description(producto.getDescripcion())
                    .quantity(detalle.getCantidad())
                    .currencyId("ARS")
                    .unitPrice(precioFinal.divide(BigDecimal.valueOf(detalle.getCantidad())))
                    .build();
            items.add(item);
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