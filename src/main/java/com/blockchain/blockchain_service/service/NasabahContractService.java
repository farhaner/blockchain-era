package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.Nasabah;
import org.apache.http.client.ResponseHandler;
import org.springframework.http.ResponseEntity;

public interface NasabahContractService {

    ResponseEntity<ResponseHandler> addNasabahContract(Nasabah request);
//    ResponseEntity<ResponseHandler> getNasabahContract(GetNasabah request);
//    ResponseEntity<ResponseHandler> getAllNasabahContract(GetAllNasabah request);
//    ResponseEntity<ResponseHandler> updateNasabahContract(UpdateNasabah request);
}
