package com.irfanstore.irfanstoreloggingrouteservice.controller;

import com.irfanstore.irfanstoreloggingrouteservice.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@RestController
public class RouteController {
    static final String FORWARDED_URL = "X-CF-Forwarded-Url";
    static final String PROXY_METADATA = "X-CF-Proxy-Metadata";
    static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";

    @Autowired
    RestOperations restOperations;

    @Autowired
    LoggingService loggingService;

    @RequestMapping(headers = {FORWARDED_URL, PROXY_METADATA, PROXY_SIGNATURE})
    ResponseEntity<?> service(RequestEntity<byte[]> incomingRequest) {
        loggingService.logRequest(incomingRequest);
        RequestEntity<?> outgoingRequest = getOutgoingRequest(incomingRequest);
        return this.restOperations.exchange(outgoingRequest, byte[].class);
    }

    private static RequestEntity<?> getOutgoingRequest(RequestEntity<?> incomingRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(incomingRequest.getHeaders());

        URI uri = headers.remove(FORWARDED_URL).stream()
                .findFirst()
                .map(URI::create)
                .orElseThrow(() -> new IllegalStateException(String.format("No %s header present", FORWARDED_URL)));

        return new RequestEntity<>(incomingRequest.getBody(), headers, incomingRequest.getMethod(), uri);
    }
}
