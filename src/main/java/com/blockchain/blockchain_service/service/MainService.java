package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MainService {

    ResponseEntity<ResponseService> addNasabah(RequestService request, MultipartFile file);
//    ResponseEntity<ResponseService> addNasabah(RequestService request);
//    ResponseEntity<ResponseService> addNasabah(RequestService request);
//    ResponseEntity<ResponseService> addNasabah(RequestService request);
}
