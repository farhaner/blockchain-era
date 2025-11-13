package com.blockchain.blockchain_service.controllers;

import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.ExecutionContract;
import com.blockchain.blockchain_service.service.IpfsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class BlockchainController {

    private final IpfsService ipfsService;
    private final ExecutionContract executionContract;

    @PostMapping("/create")
    public ResponseEntity<ResponseService> create(@Valid @RequestBody RequestService request) throws JsonProcessingException {
        return executionContract.storeData(request);
    }

    @PostMapping("/view")
    public String view() throws JsonProcessingException {

        return "berhasil";
    }


    //    IPFS Service
    @PostMapping("/upload")
    public ResponseEntity<ResponseService> upload(@RequestParam("file") MultipartFile file) {
        return ipfsService.uploadFile(file);
    }

    @PostMapping(value = "/getCid")
    public ResponseEntity<ResponseService> get(@RequestParam String cid) {
        return ipfsService.getFile(cid);
    }

    @PostMapping(value = "/getAllCid")
    public ResponseEntity<ResponseService> pin() {
        return ipfsService.getAllCid();
    }


}