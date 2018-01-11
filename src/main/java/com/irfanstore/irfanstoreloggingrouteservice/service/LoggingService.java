package com.irfanstore.irfanstoreloggingrouteservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    public void logRequest(RequestEntity<?> incomingRequest) {
        this.logger.info("INCOMING REQUEST --> : {} " + System.lineSeparator(), incomingRequest);
        this.logger.info("REQUEST METHOD --> : {} " + System.lineSeparator(), incomingRequest.getMethod());
        this.logger.info("REQUEST HEADERS --> : {} " + System.lineSeparator(), incomingRequest.getHeaders());
        this.logger.info("REQUEST BODY --> : {} " + System.lineSeparator(), incomingRequest.getBody());
    }
}
