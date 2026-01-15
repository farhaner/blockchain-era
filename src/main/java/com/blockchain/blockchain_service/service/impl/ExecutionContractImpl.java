package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.contract.CustomerContract;
import com.blockchain.blockchain_service.dto.CidRequest;
import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.ExecutionContract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExecutionContractImpl implements ExecutionContract {

    private final CustomerContract customerContract;
    ResponseService responseService = new ResponseService();

    @Override
    public ResponseEntity<ResponseService> storeCustomerContract(RequestService request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionReceipt contractResponse = null;

        try {
            CustomerContract.CustomerCid cid = new CustomerContract.CustomerCid(
                    request.getCid().getIdentityCopy(),
                    request.getCid().getResidencePermit(),
                    request.getCid().getIncomeProof(),
                    request.getCid().getBusinessDocumentCopy(),
                    request.getCid().getProfessionalLicense(),
                    request.getCid().getOtherBankCreditCardInfo(),
                    request.getCid().getEmeraldCustomer(),
                    request.getCid().getTaxIdNumber()
            );

            UUID uuid = UUID.randomUUID();
            CustomerContract.Customer customer = new CustomerContract.Customer(
                    uuid.toString(),
                    request.getNik(),
                    request.getFullName(),
                    request.getBirthDate(),
                    request.getGender(),
                    request.getNationality(),
                    request.getFullAddress(),
                    request.getPhoneNumber(),
                    cid
            );

            contractResponse = customerContract
                    .createCustomer(customer)
                    .send();
//            objectMapper.readValue(contractResponse, BlockchainResponse.class);
            String s = objectMapper.writeValueAsString(contractResponse);
            log.info("contractResponse: {}", s);

            responseService.setStatusCode("000");
            responseService.setStatus(true);
            responseService.setMessage("Success");
            responseService.setData(contractResponse);

            return ResponseEntity.ok(responseService);

        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage(e.getMessage());
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }

    public ResponseEntity<ResponseService> getCustomerContract(String nik) {
        RequestService responseServer = new RequestService();
        responseServer.setCid(new CidRequest());
        ResponseService responseService = new ResponseService();

        try {
            CustomerContract.Customer result = customerContract
                    .getCustomer(nik)
                    .send();
            log.info("getData: {}", result.getValue().toString());

            responseServer.setNik(result.nik);
            responseServer.setFullName(result.fullName);
            responseServer.setBirthDate(result.birthDate);
            responseServer.setGender(result.gender);
            responseServer.setNationality(result.nationality);
            responseServer.setFullAddress(result.fullAddress);
            responseServer.setPhoneNumber(result.phoneNumber);
            responseServer.getCid().setIdentityCopy(result.cid.identityCopy);
            responseServer.getCid().setResidencePermit(result.cid.residencePermit);
            responseServer.getCid().setIncomeProof(result.cid.incomeProof);
            responseServer.getCid().setBusinessDocumentCopy(result.cid.businessDocumentCopy);
            responseServer.getCid().setProfessionalLicense(result.cid.professionalLicense);
            responseServer.getCid().setOtherBankCreditCardInfo(result.cid.otherBankCreditCardInfo);
            responseServer.getCid().setTaxIdNumber(result.cid.taxIdNumber);
            responseServer.getCid().setEmeraldCustomer(result.cid.emeraldCustomer);

            log.info("response: {}", responseServer);

            responseService.setStatusCode("000");
            responseService.setStatus(true);
            responseService.setMessage("Success");
            responseService.setData(responseServer);

            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage(e.getMessage());
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }

    public ResponseEntity<ResponseService> getAllCustomerContract() {
        try {

            List getAllResponse = customerContract.getAllCustomerNiks().send();
            log.info("response: {}", getAllResponse);
            responseService.setStatusCode("000");
            responseService.setStatus(true);
            responseService.setMessage("Success");
            responseService.setData(getAllResponse);

            return ResponseEntity.ok(responseService);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage(e.getMessage());
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }

    @Override
    public ResponseEntity<ResponseService> updateCustomerContract(RequestService request) throws JsonProcessingException {
        try {
            ResponseEntity<ResponseService> findCustomerContract = getCustomerContract(request.getNik());
            log.info("findCustomerContract: {}", findCustomerContract.getBody().getStatusCode());
            if (findCustomerContract.getBody().getStatusCode().equals("000")) {
                CustomerContract.CustomerCid cid = new CustomerContract.CustomerCid(
                        request.getCid().getIdentityCopy(),
                        request.getCid().getResidencePermit(),
                        request.getCid().getIncomeProof(),
                        request.getCid().getBusinessDocumentCopy(),
                        request.getCid().getProfessionalLicense(),
                        request.getCid().getOtherBankCreditCardInfo(),
                        request.getCid().getEmeraldCustomer(),
                        request.getCid().getTaxIdNumber()
                );

                UUID uuid = UUID.randomUUID();
                CustomerContract.Customer customer = new CustomerContract.Customer(
                        uuid.toString(),
                        request.getNik(),
                        request.getFullName(),
                        request.getBirthDate(),
                        request.getGender(),
                        request.getNationality(),
                        request.getFullAddress(),
                        request.getPhoneNumber(),
                        cid
                );
                TransactionReceipt updateContract = customerContract
                        .updateCustomer(customer)
                        .send();

                responseService.setStatusCode("000");
                responseService.setStatus(true);
                responseService.setMessage("Success updated contract customer ".concat(request.getNik()));
                responseService.setData(updateContract);

            } else {
                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("Customer not found");
                responseService.setData(request);
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