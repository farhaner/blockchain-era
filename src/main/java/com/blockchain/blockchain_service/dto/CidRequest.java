package com.blockchain.blockchain_service.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CidRequest {

    private String identityCopy;

    private String residencePermit;

    private String incomeProof;

    private String businessDocumentCopy;

    private String professionalLicense;

    private String otherBankCreditCardInfo;

    private String emeraldCustomer;

    private String taxIdNumber;
}
