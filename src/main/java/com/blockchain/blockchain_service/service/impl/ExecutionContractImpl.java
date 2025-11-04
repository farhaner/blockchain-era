//package com.blockchain.blockchain_service.service.impl;
//
//import com.blockchain.blockchain_service.contract.NasabahContract;
//import com.blockchain.blockchain_service.dto.Nasabah;
//import com.blockchain.blockchain_service.service.ExecutionContract;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.ResponseHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.web3j.crypto.Credentials;
//import org.web3j.protocol.Web3j;
//import org.web3j.tx.gas.DefaultGasProvider;
//
//@Service
//@Slf4j
////@RequiredArgsConstructor
//public class ExecutionContractImpl implements ExecutionContract {
//
//    @Value("${contract.address}")
//    private String contractAddress;
//
//    @Value("${private.key}")
//    private String privateKey;
//
//    private final Web3j web3j;
//    private final Credentials credentials = Credentials.create(privateKey);
//
//    public ExecutionContractImpl(Web3j web3j) {
//        this.web3j = web3j;
//    }
//
//    public NasabahContract loadNasabahContract(String contractAddress) {
//        try {
//            log.info("hahahha");
//        } catch (Exception e) {
//            log.info("hahahha error : {}", e.getMessage());
//            e.printStackTrace();
//        }
//        //        return NasabahContract.load(contractAddress, web3j, credentials, new DefaultGasProvider());
//        return null;
//    }
//
////    @Override
////    public ResponseEntity<ResponseHandler> addNasabahContract(Nasabah request) {
////        return null;
////    }
//}
//
//
