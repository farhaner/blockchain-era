package com.blockchain.blockchain_service.service.impl;

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

@Service
@Slf4j
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final ExecutionContractImpl executionContract;
    private final IpfsService ipfsService;

    @Override
    public ResponseEntity<ResponseService> addNasabah(RequestService request, MultipartFile file) {
        try {

            ResponseEntity<ResponseService> responseIpfs = ipfsService.uploadFile(file);
            log.info("Upload ipfs response: {}", responseIpfs.getBody());

//            ObjectMapper objectMapper = new ObjectMapper();
////            objectMapper.readValue()

            ResponseEntity<ResponseService> responseContract = executionContract.storeData(request);
            log.info("Contract response: {}", responseContract.getBody());


        } catch (Exception e) {

        }


        return null;
    }

}
