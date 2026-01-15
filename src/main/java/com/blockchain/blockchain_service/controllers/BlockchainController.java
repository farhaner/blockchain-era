package com.blockchain.blockchain_service.controllers;

import com.blockchain.blockchain_service.dto.IpfsResponse;
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
            @RequestPart(value = "identityCopy")  MultipartFile identityCopy,
            @RequestPart(value = "residencePermit") MultipartFile residencePermit,
            @RequestPart(value = "incomeProof") MultipartFile incomeProof,
            @RequestPart(value = "businessDocumentCopy") MultipartFile businessDocumentCopy,
            @RequestPart(value = "professionalLicense") MultipartFile professionalLicense,
            @RequestPart(value = "otherBankCreditCardInfo") MultipartFile otherBankCreditCardInfo,
            @RequestPart(value = "emeraldCustomer") MultipartFile emeraldCustomer,
            @RequestPart(value = "taxIdNumber", required = false) MultipartFile taxIdNumber
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
        return executionContract.getCustomerContract(request.getNik());
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
        return executionContract.getCustomerContract(request.getNik());
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
    public IpfsResponse upload(@RequestParam("file") MultipartFile file) {
        return ipfsService.uploadFile2(file);
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