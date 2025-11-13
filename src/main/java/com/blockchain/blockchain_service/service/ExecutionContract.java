package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface ExecutionContract {

    ResponseEntity<ResponseService> storeData(RequestService request) throws JsonProcessingException;
//    ResponseEntity<ResponseService> getNasabahContract(RequestService request);
//    ResponseEntity<ResponseHandler> getAllNasabahContract(GetAllNasabah request);
//    ResponseEntity<ResponseHandler> updateNasabahContract(UpdateNasabah request);
}
