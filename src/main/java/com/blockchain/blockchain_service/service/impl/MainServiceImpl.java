package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.dto.CidRequest;
import com.blockchain.blockchain_service.dto.IpfsResponse;
import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.IpfsService;
import com.blockchain.blockchain_service.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final ExecutionContractImpl executionContract;
    private final IpfsService ipfsService;

    ObjectMapper objectMapper = new ObjectMapper();
    ResponseService responseService = new ResponseService();
    Map<String, MultipartFile> allFiles = new LinkedHashMap<>();
    CidRequest cidRequest = new CidRequest();

    @Override
    public ResponseEntity<ResponseService> addCustomer(String payload, MultipartFile identityCopy,
                                                       MultipartFile residencePermit, MultipartFile incomeProof,
                                                       MultipartFile businessDocumentCopy, MultipartFile professionalLicense,
                                                       MultipartFile otherBankCreditCardInfo, MultipartFile emeraldCustomer,
                                                       MultipartFile taxIdNumber) {

        try {
            RequestService payloadJsonRequest = objectMapper.readValue(payload, RequestService.class);
            ResponseEntity<ResponseService> findCustomer = executionContract.getCustomerContract(payloadJsonRequest.getNik());
            log.info("Find Customer : {}", findCustomer.getBody().getStatusCode());
            if (!findCustomer.getBody().getStatusCode().equals("000")) {

                allFiles.put("IdentityCopy", identityCopy);
                allFiles.put("ResidencePermit", residencePermit);
                allFiles.put("IncomeProof", incomeProof);
                allFiles.put("BusinessDocumentCopy", businessDocumentCopy);
                allFiles.put("ProfessionalLicense", professionalLicense);
                allFiles.put("OtherBankCreditCardInfo", otherBankCreditCardInfo);
                allFiles.put("EmeraldCustomer", emeraldCustomer);
                allFiles.put("TaxIdNumber", taxIdNumber);
                log.info("CID List : {}", allFiles);

                List<Object> results = new ArrayList<>();

                log.info("========Request IPFS========");
                allFiles.forEach((key, value) -> {
                    log.info("Key : {}, Value: {}", key, value);
                    if (value != null) {
                        IpfsResponse responseServiceResponseEntity = ipfsService.uploadFile2(value);
                        Object data = responseServiceResponseEntity.getHash();
                        log.info("CID from {}: {}", key, data.toString());
                        results.add(data);
                    } else {
                        results.add(null);
                    }
                });

                cidRequest.setIdentityCopy(String.valueOf(results.get(0)));
                cidRequest.setResidencePermit(String.valueOf(results.get(1)));
                cidRequest.setIncomeProof(String.valueOf(results.get(2)));
                cidRequest.setBusinessDocumentCopy(String.valueOf(results.get(3)));
                cidRequest.setProfessionalLicense(String.valueOf(results.get(4)));
                cidRequest.setOtherBankCreditCardInfo(String.valueOf(results.get(5)));
                cidRequest.setEmeraldCustomer(String.valueOf(results.get(6)));
                cidRequest.setTaxIdNumber(String.valueOf(results.get(7)));
                log.info("All CID: {}", cidRequest);

                RequestService requestService = new RequestService();
                requestService.setNik(payloadJsonRequest.getNik());
                requestService.setFullName(payloadJsonRequest.getFullName());
                requestService.setBirthDate(payloadJsonRequest.getBirthDate());
                requestService.setGender(payloadJsonRequest.getGender());
                requestService.setNationality(payloadJsonRequest.getNationality());
                requestService.setFullAddress(payloadJsonRequest.getFullAddress());
                requestService.setPhoneNumber(payloadJsonRequest.getPhoneNumber());
                requestService.setCid(cidRequest);

                log.info("test3{}", requestService.getCid());

                log.info("========Request Blockchain========");
                ResponseEntity<ResponseService> responseContract = executionContract.storeCustomerContract(requestService);
                log.info("Blockchain response: {}", responseContract.getBody());
                if (responseContract.getBody().getStatusCode().equals("000")) {
                    responseService.setStatusCode("000");
                    responseService.setStatus(true);
                    responseService.setMessage("Success");
                    responseService.setData(requestService);
                } else {
                    responseService.setStatusCode(findCustomer.getBody().getStatusCode());
                    responseService.setStatus(false);
                    responseService.setMessage(findCustomer.getBody().getMessage());
                    responseService.setData(null);
                }
            } else {
                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("Customer (NIK) already exists");
                responseService.setData(findCustomer.getBody().getData());
            }
            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage("General Error");
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }

    @Override
    public ResponseEntity<ResponseService> updateCustomer(String payload, MultipartFile identityCopy,
                                                          MultipartFile residencePermit, MultipartFile incomeProof,
                                                          MultipartFile businessDocumentCopy, MultipartFile professionalLicense,
                                                          MultipartFile otherBankCreditCardInfo, MultipartFile emeraldCustomer,
                                                          MultipartFile taxIdNumber) {
        try {
            RequestService payloadJsonRequest = objectMapper.readValue(payload, RequestService.class);
            ResponseEntity<ResponseService> findCustomer = executionContract.getCustomerContract(payloadJsonRequest.getNik());
            log.info("Find Customer : {}", findCustomer.getBody().getStatusCode());
            if (!findCustomer.getBody().getStatusCode().equals("000")) {

                allFiles.put("IdentityCopy", identityCopy);
                allFiles.put("ResidencePermit", residencePermit);
                allFiles.put("IncomeProof", incomeProof);
                allFiles.put("BusinessDocumentCopy", businessDocumentCopy);
                allFiles.put("ProfessionalLicense", professionalLicense);
                allFiles.put("OtherBankCreditCardInfo", otherBankCreditCardInfo);
                allFiles.put("EmeraldCustomer", emeraldCustomer);
                allFiles.put("TaxIdNumber", taxIdNumber);
                log.info("CID List : {}", allFiles);

                List<Object> results = new ArrayList<>();

                log.info("========Request IPFS========");
                allFiles.forEach((key, value) -> {
                    log.info("Key : {}, Value: {}", key, value);
                    if (value != null) {
                        IpfsResponse responseServiceResponseEntity = ipfsService.uploadFile2(value);
                        Object data = responseServiceResponseEntity.getHash();
                        log.info("CID from {}: {}", key, data.toString());
                        results.add(data);
                    } else {
                        results.add(null);
                    }
                });

                cidRequest.setIdentityCopy(String.valueOf(results.get(0)));
                cidRequest.setResidencePermit(String.valueOf(results.get(1)));
                cidRequest.setIncomeProof(String.valueOf(results.get(2)));
                cidRequest.setBusinessDocumentCopy(String.valueOf(results.get(3)));
                cidRequest.setProfessionalLicense(String.valueOf(results.get(4)));
                cidRequest.setOtherBankCreditCardInfo(String.valueOf(results.get(5)));
                cidRequest.setEmeraldCustomer(String.valueOf(results.get(6)));
                cidRequest.setTaxIdNumber(String.valueOf(results.get(7)));
                log.info("All CID: {}", cidRequest);

                RequestService requestService = new RequestService();
                requestService.setNik(payloadJsonRequest.getNik());
                requestService.setFullName(payloadJsonRequest.getFullName());
                requestService.setBirthDate(payloadJsonRequest.getBirthDate());
                requestService.setGender(payloadJsonRequest.getGender());
                requestService.setNationality(payloadJsonRequest.getNationality());
                requestService.setFullAddress(payloadJsonRequest.getFullAddress());
                requestService.setPhoneNumber(payloadJsonRequest.getPhoneNumber());
                requestService.setCid(cidRequest);

                log.info("test3{}", requestService.getCid());

                log.info("========Request Blockchain========");
                ResponseEntity<ResponseService> responseContract = executionContract.updateCustomerContract(requestService);
                log.info("Blockchain response: {}", responseContract.getBody());

                if (responseContract.getBody().getStatusCode().equals("000")) {
                    responseService.setStatusCode("000");
                    responseService.setStatus(true);
                    responseService.setMessage("Success");
                    responseService.setData(requestService);
                } else {
                    responseService.setStatusCode(findCustomer.getBody().getStatusCode());
                    responseService.setStatus(false);
                    responseService.setMessage(findCustomer.getBody().getMessage());
                    responseService.setData(null);
                }
            } else {
                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("Customer (NIK) already exists");
                responseService.setData(findCustomer.getBody().getData());
            }
            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage("General Error");
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }
}
