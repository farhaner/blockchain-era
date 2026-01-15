package com.blockchain.blockchain_service.service;

import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MainService {

    ResponseEntity<ResponseService> addCustomer(String payload, MultipartFile file1, MultipartFile file2,
                                                MultipartFile file3, MultipartFile file4, MultipartFile file5,
                                                MultipartFile file6, MultipartFile file7, MultipartFile file8);

    ResponseEntity<ResponseService> updateCustomer(String payload, MultipartFile file1, MultipartFile file2,
                                                   MultipartFile file3, MultipartFile file4, MultipartFile file5,
                                                   MultipartFile file6, MultipartFile file7, MultipartFile file8);
}
