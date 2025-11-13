package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IpfsService {

    ResponseEntity<ResponseService> uploadFile(MultipartFile file);

    ResponseEntity<ResponseService> getFile(String cid);

    ResponseEntity<ResponseService> getAllCid();

//    ResponseEntity<ResponseService> unpinFile(MultipartFile file);

}
