package com.blockchain.blockchain_service.controllers;

import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.ExecutionContract;
import com.blockchain.blockchain_service.service.IpfsService;
import com.blockchain.blockchain_service.service.MainService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor
public class BlockchainController {

    private final IpfsService ipfsService;
    private final ExecutionContract executionContract;
    private final MainService mainService;

    @PostMapping(value = "/addCustomer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseService> addCustomer(
            @RequestPart("payload") String payloadJson,
            @RequestPart("identityCopy") MultipartFile identityCopy,
            @RequestPart("residencePermit") MultipartFile residencePermit,
            @RequestPart("incomeProof") MultipartFile incomeProof,
            @RequestPart("businessDocumentCopy") MultipartFile businessDocumentCopy,
            @RequestPart("professionalLicense") MultipartFile professionalLicense,
            @RequestPart("otherBankCreditCardInfo") MultipartFile otherBankCreditCardInfo,
            @RequestPart("emeraldCustomer") MultipartFile emeraldCustomer,
            @RequestPart("taxIdNumber") MultipartFile taxIdNumber
    ) throws JsonProcessingException {
        return mainService.addCustomer(
                payloadJson,
                identityCopy,
                residencePermit,
                incomeProof,
                businessDocumentCopy,
                professionalLicense,
                otherBankCreditCardInfo,
                emeraldCustomer,
                taxIdNumber);
    }

    @PostMapping("/getCustomer")
    public ResponseEntity<ResponseService> getCustomer(@RequestBody RequestService request) throws JsonProcessingException {
        return executionContract.getCustomerContract(request);
    }

    @PostMapping("/getAllCustomer")
    public ResponseEntity<ResponseService> getAllCustomer() {
        return executionContract.getAllCustomerContract();
    }

    @PostMapping("/updateCustomer")
    public ResponseEntity<ResponseService> updateCustomer(@RequestBody RequestService request) throws JsonProcessingException {
        return executionContract.updateCustomerContract(request);
    }


    @PostMapping("/create")
    public ResponseEntity<ResponseService> create(@RequestBody RequestService request) throws JsonProcessingException {
        return executionContract.storeCustomerContract(request);
    }

    @PostMapping("/view")
    public ResponseEntity<ResponseService> view(@RequestBody RequestService request) throws JsonProcessingException {
        return executionContract.getCustomerContract(request);
    }

    @PostMapping("/getAll")
    public ResponseEntity<ResponseService> getAll() throws JsonProcessingException {
        return executionContract.getAllCustomerContract();
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseService> update(@RequestBody RequestService request) throws JsonProcessingException {
        return executionContract.updateCustomerContract(request);
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