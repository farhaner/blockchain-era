package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.dto.CidRequest;
import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.IpfsService;
import com.blockchain.blockchain_service.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
        CidRequest ipfsCid = new CidRequest();
        log.info("payload:{}", payload);
        try {
            allCid.put("IdentityCopy", identityCopy);
            allCid.put("ResidencePermit", residencePermit);
            allCid.put("IncomeProof", incomeProof);
            allCid.put("BusinessDocumentCopy", businessDocumentCopy);
            allCid.put("ProfessionalLicense", professionalLicense);
            allCid.put("OtherBankCreditCardInfo", otherBankCreditCardInfo);
            allCid.put("EmeraldCustomer", emeraldCustomer);
            allCid.put("TaxIdNumber", taxIdNumber);
            log.info("CID List : {}", allCid);

            for (var entry : allCid.entrySet()) {
                String field = entry.getKey();
                MultipartFile file = entry.getValue();
                log.info("========Request IPFS========");
                ResponseEntity<ResponseService> responseServiceResponseEntity = ipfsService.uploadFile(file);
                log.info("IPFS response: {}", responseServiceResponseEntity.getBody().getMessage());

                log.info("CID : {}", entry.getValue());
            }

            RequestService requestService = new RequestService();
            requestService.setNik(null);
            requestService.setFullName(null);
            requestService.setBirthDate(null);
            requestService.setGender(null);
            requestService.setNationality(null);
            requestService.setFullAddress(null);
            requestService.setPhoneNumber(null);
            requestService.setCid(null);
            log.info("========Request Blockchain========");
            ResponseEntity<ResponseService> responseContract = executionContract.storeCustomerContract(requestService);


        } catch (Exception e) {

        }


        return null;
    }

}
