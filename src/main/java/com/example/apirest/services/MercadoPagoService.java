package com.example.apirest.services;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.example.apirest.dto.PaymentRequestDTO;
import com.example.apirest.entities.OrdenDeCompra;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MercadoPagoService {

    private final OrdenDeCompraService ordenDeCompraService;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    public MercadoPagoService(OrdenDeCompraService ordenDeCompraService) {
        this.ordenDeCompraService = ordenDeCompraService;
    }

    public Preference crearPreferencia(PaymentRequestDTO paymentRequest) throws MPException, MPApiException {
        try {
            // Configurar SDK con tu access token
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            // Verificar que la orden existe
            OrdenDeCompra orden = ordenDeCompraService.obtenerPorId(paymentRequest.getOrdenId())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

            // Crear item para la preferencia
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(paymentRequest.getDescripcion())
                    .quantity(1)
                    .unitPrice(paymentRequest.getMonto())
                    .build();

            // Configurar preferencia
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(Collections.singletonList(item))
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("https://localhost:5173/paymentSuccess")
                            .pending("https://localhost:5173/")
                            .failure("https://localhost:5173/paymentFailure")
                            .build())
                    .externalReference(orden.getId().toString())
                    .build();

            // Crear preferencia en Mercado Pago
            PreferenceClient client = new PreferenceClient();
            return client.create(preferenceRequest);
        } catch (MPApiException e) {
            // Loguear detalles específicos del error de API
            System.err.println("Error de API de Mercado Pago: " + e.getApiResponse().getContent());
            System.err.println("Status: " + e.getApiResponse().getStatusCode());
            throw e; // Re-lanzar para que el controlador pueda manejarlo
        } catch (MPException e) {
            System.err.println("Error general de Mercado Pago: " + e.getMessage());
            throw e;
        }
    }
}
