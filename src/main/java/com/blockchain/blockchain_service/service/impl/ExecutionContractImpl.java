package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.contract.CustomerContract;
import com.blockchain.blockchain_service.dto.RequestService;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.ExecutionContract;
import com.blockchain.blockchain_service.service.IpfsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExecutionContractImpl implements ExecutionContract {

    private final CustomerContract customerContract;
    private String badRequest = "904";


    @Override
    public ResponseEntity<ResponseService> storeData(RequestService request) throws JsonProcessingException {
        ResponseService responseService = new ResponseService();
        ObjectMapper objectMapper = new ObjectMapper();
        TransactionReceipt contractResponse = null;

        try {
            if (!request.getBirthDate().matches("^\\d{4}-\\d{2}-\\d{2}$") ||
                    !request.getValidUntil().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                throw new Exception(badRequest);
            }

//            String normalizeGender = normalize(String.valueOf(request.getGender()));
//            String normalizeMaritalStatus = normalize(String.valueOf(request.getMaritalStatus()));

            String requestId = String.valueOf(UUID.randomUUID());
            log.info("requestId: {}", requestId);
//            byte[] id = generateBytes32UUID();
            byte[] id = Arrays.copyOf(
                    String.valueOf(request.getNik()).getBytes(StandardCharsets.UTF_8),
                    32
            );
//            BigInteger genderValue = BigInteger.valueOf(convertGenderToOrdinal(normalizeGender));
//            BigInteger maritalStatusValue = BigInteger.valueOf(convertMaritalStatusToOrdinal(normalizeMaritalStatus));
//            BigInteger maritalStatusValue1 = BigInteger.valueOf(0);
//            BigInteger bloodTypeValue = BigInteger.valueOf(convertBloodTypeToOrdinal(String.valueOf(request.getBloodType())));
//            BigInteger religionValue = BigInteger.valueOf(convertReligionToOrdinal(String.valueOf(request.getReligion())));
//
//            log.info("gender: {}", genderValue);
//            log.info("maritalStatus: {}", maritalStatusValue);
//            log.info("bloodType: {}", bloodTypeValue);
//            log.info("religion: {}", religionValue);

            CustomerContract.CustomerInput customerInput = new CustomerContract.CustomerInput(
                    BigInteger.valueOf(request.getNik()),                         // uint64
                    request.getFullName(),                                        // string
                    request.getBirthPlace(),                                      // string
                    request.getBirthDate().getBytes(StandardCharsets.UTF_8),      // bytes10
                    BigInteger.valueOf(request.getGender().ordinal()),            // enum Gender → uint8
                    BigInteger.valueOf(request.getReligion().ordinal()),          // enum Religion → uint8
                    BigInteger.valueOf(request.getMaritalStatus().ordinal()),     // enum MaritalStatus → uint8
                    BigInteger.valueOf(request.getBloodType().ordinal()),         // enum BloodType → uint8
                    request.getNationality(),                                     // string
                    request.getFullAddress(),                                     // string
                    request.getValidUntil().getBytes(StandardCharsets.UTF_8),     // bytes10
                    request.getOccupation(),                                      // string
                    request.getCid(),                                             // List<String>
                    true                                                          // Boolean active
            );

            log.info(Arrays.toString(customerInput.gender.toByteArray()));

            contractResponse = customerContract
                    .createCustomer(id, customerInput)
                    .send();
            log.info("contractResponse: {}", customerInput);

            responseService.setStatusCode("000");
            responseService.setStatus(true);
            responseService.setMessage("Success");
            responseService.setData(contractResponse);

            return ResponseEntity.ok(responseService);
        } catch (TransactionException te) {
            Map errorContractResponse = objectMapper.readValue(te.getMessage(), Map.class);
            log.error("Error: {}", te.getMessage());
            log.error("Error: {}", te.getStackTrace());

            responseService.setStatusCode("901");
            responseService.setStatus(false);
            responseService.setMessage("Call Contract Failed");
            responseService.setData(errorContractResponse);

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

    public ResponseEntity<ResponseService> getCustomerContract(RequestService request) {
        return null;
    }

    private int convertGenderToOrdinal(String gender) {
        return switch (gender) {
            case "LakiLaki" -> 0;
            case "Perempuan" -> 1;
            default -> throw new IllegalArgumentException("Invalid Gender");
        };
    }

    private int convertBloodTypeToOrdinal(String bloodType) {
        return switch (bloodType.toUpperCase()) {
            case "A" -> 0;
            case "B" -> 1;
            case "AB" -> 2;
            case "O" -> 3;
            default -> throw new IllegalArgumentException("Invalid Blood Type");
        };
    }

    private int convertReligionToOrdinal(String religion) {
        return switch (religion) {
            case "Islam" -> 0;
            case "Kristen" -> 1;
            case "Katolik" -> 2;
            case "Hindu" -> 3;
            case "Buddha" -> 4;
            case "Konghucu" -> 5;
            default -> throw new IllegalArgumentException("Invalid religion");
        };
    }

    private int convertMaritalStatusToOrdinal(String status) {
        return switch (status) {
            case "Kawin" -> 0;
            case "BelumKawin" -> 1;
            default -> throw new IllegalArgumentException("Invalid marital status");
        };
    }

    private String normalize(String input) {
        if (input == null) return null;

        String clean = input.toLowerCase().replaceAll("[\\s\\-]", "");

        if (clean.equals("belumkawin")) return "BelumKawin";
        if (clean.equals("kawin")) return "Kawin";
        if (clean.equals("lakilaki")) return "LakiLaki";
        if (clean.equals("perempuan")) return "Perempuan";

        return null;
    }
}