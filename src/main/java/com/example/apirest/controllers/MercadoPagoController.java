package com.example.apirest.controllers;

import com.example.apirest.dto.PaymentRequestDTO;
import com.example.apirest.entities.OrdenDeCompra;
import com.example.apirest.services.MercadoPagoService;
import com.example.apirest.services.OrdenDeCompraService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferencePaymentTypeRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class MercadoPagoController {
    private final OrdenDeCompraService ordenDeCompraService;
    private final MercadoPagoService mercadoPagoService;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @PostMapping("/mp")
    public ResponseEntity<Map<String, Object>> mp(@RequestBody Map<String, List<Long>> body) throws Exception {
        try {
            List<Long> ids = body.get("id");
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
            List<PreferenceItemRequest> items = new ArrayList<>();

            // Obtener la orden de compra
            OrdenDeCompra ordenDeCompra = ordenDeCompraService.obtenerPorId(ids.get(0))
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

            // Crear items para cada detalle de la orden
            for (var detalle : ordenDeCompra.getDetalle()) {
                Double precioFinal = detalle.getPrecioUnitario().doubleValue();
                PreferenceItemRequest item = PreferenceItemRequest.builder()
                        .id(detalle.getId().toString())
                        .title(detalle.getProducto().getNombre())
                        .description(detalle.getProducto().getDescripcion())
                        .quantity(detalle.getCantidad())
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(precioFinal))
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

            Map<String, Object> response = new HashMap<>();
            response.put("preferenceId", prefId);
            response.put("urlMP", preference.getInitPoint());
            response.put("orden", ordenDeCompra);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/update-status/{ordenId}")
    @CrossOrigin("*")
    public ResponseEntity<?> actualizarEstadoOrden(
            @PathVariable Long ordenId,
            @RequestParam String nuevoEstado) {

        try {
            OrdenDeCompra orden = ordenDeCompraService.obtenerPorId(ordenId)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

            // Convertir el string a enum
            OrdenDeCompra.EstadoPago estadoPago = OrdenDeCompra.EstadoPago.valueOf(nuevoEstado.toUpperCase());
            orden.setEstadoPago(estadoPago);
            ordenDeCompraService.save(orden);

            return ResponseEntity.ok("Estado actualizado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Estado inválido. Valores permitidos: PENDIENTE, PAGADO, CANCELADO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error actualizando el estado: " + e.getMessage());
        }
    }
}