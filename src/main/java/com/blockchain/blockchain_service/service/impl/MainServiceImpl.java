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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final ExecutionContractImpl executionContract;
    private final IpfsService ipfsService;

    @Override
    public ResponseEntity<ResponseService> addCustomer(String payload, MultipartFile identityCopy,
                                                       MultipartFile residencePermit, MultipartFile incomeProof,
                                                       MultipartFile businessDocumentCopy, MultipartFile professionalLicense,
                                                       MultipartFile otherBankCreditCardInfo, MultipartFile emeraldCustomer,
                                                       MultipartFile taxIdNumber) {
        Map<String, MultipartFile> allCid = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseService responseService = new ResponseService();
        CidRequest cidRequest = new CidRequest();
        try {

            RequestService payloadJsonRequest = objectMapper.readValue(payload, RequestService.class);

            allCid.put("IdentityCopy", identityCopy);
            allCid.put("ResidencePermit", residencePermit);
            allCid.put("IncomeProof", incomeProof);
            allCid.put("BusinessDocumentCopy", businessDocumentCopy);
            allCid.put("ProfessionalLicense", professionalLicense);
            allCid.put("OtherBankCreditCardInfo", otherBankCreditCardInfo);
            allCid.put("EmeraldCustomer", emeraldCustomer);
            allCid.put("TaxIdNumber", taxIdNumber);
            log.info("CID List : {}", allCid);

            List<Object> results = new ArrayList<>();

            log.info("========Request IPFS========");
            for (var entry : allCid.entrySet()) {
                MultipartFile file = entry.getValue();
                ResponseEntity<ResponseService> responseServiceResponseEntity = ipfsService.uploadFile(file);
                Object data = responseServiceResponseEntity.getBody().getData();

                results.add(data);
            }
            log.info("REsult IPFS: {}", results.toString());

            cidRequest.setIdentityCopy(String.valueOf(results.get(0)));
            cidRequest.setResidencePermit(String.valueOf(results.get(1)));
            cidRequest.setIncomeProof(String.valueOf(results.get(2)));
            cidRequest.setBusinessDocumentCopy(String.valueOf(results.get(3)));
            cidRequest.setProfessionalLicense(String.valueOf(results.get(4)));
            cidRequest.setOtherBankCreditCardInfo(String.valueOf(results.get(5)));
            cidRequest.setEmeraldCustomer(String.valueOf(results.get(6)));
            cidRequest.setTaxIdNumber(String.valueOf(results.get(7)));

            RequestService requestService = new RequestService();
            requestService.setNik(payloadJsonRequest.getNik());
            requestService.setFullName(payloadJsonRequest.getFullName());
            requestService.setBirthDate(payloadJsonRequest.getBirthDate());
            requestService.setGender(payloadJsonRequest.getGender());
            requestService.setNationality(payloadJsonRequest.getNationality());
            requestService.setFullAddress(payloadJsonRequest.getFullAddress());
            requestService.setPhoneNumber(payloadJsonRequest.getPhoneNumber());
            requestService.setCid(cidRequest);

            log.info("========Request Blockchain========");
            ResponseEntity<ResponseService> responseContract = executionContract.storeCustomerContract(requestService);
            log.info("Blockchain response: {}", responseContract.getBody().getStatusCode());
            if (responseContract.getBody().getStatusCode().equals("000")) {
                responseService.setStatusCode("000");
                responseService.setStatus(true);
                responseService.setMessage("Success");
                responseService.setData(responseContract.getBody().getData());
            }else {
                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("Failed");
                responseService.setData(null);
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
