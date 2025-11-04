package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IpfsService {

    ResponseEntity<ResponseService> uploadFile(MultipartFile file);
//    ResponseEntity<ResponseService> getFile(MultipartFile file);
//    ResponseEntity<ResponseService> pinFile(MultipartFile file);
//    ResponseEntity<ResponseService> unpinFile(MultipartFile file);
//    ResponseEntity<ResponseService> listPin(MultipartFile file);

}
