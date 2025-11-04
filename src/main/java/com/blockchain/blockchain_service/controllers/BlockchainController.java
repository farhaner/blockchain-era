package com.blockchain.blockchain_service.controllers;

import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.IpfsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
public class BlockchainController {

    private final IpfsService ipfsService;

    public BlockchainController(IpfsService ipfsService) {
        this.ipfsService = ipfsService;
    }

    @PostMapping(value = "/add")
    public String addData() {
        return "null";
    }

    @GetMapping(value = "/get")
    public String getData() {
        return "null";
    }

    @GetMapping(value = "/getAll")
    public List<String> getAlldata() {
        return null;
    }

    @PostMapping(value = "/update")
    public String updateData() {
        return "null";
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseService> uploadToIpfs(@RequestParam("file") MultipartFile file) {
        return ipfsService.uploadFile(file);
    }

}
