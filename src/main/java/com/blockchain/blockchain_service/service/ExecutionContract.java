package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ExecutionContract {

    ResponseEntity<ResponseService> storeCustomerContract(RequestService request) throws JsonProcessingException;

    ResponseEntity<ResponseService> getCustomerContract(String request);

    ResponseEntity<ResponseService> getAllCustomerContract();

    ResponseEntity<ResponseService> updateCustomerContract(RequestService request) throws JsonProcessingException;
}
