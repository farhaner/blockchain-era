package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.IpfsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpfsServiceImpl implements IpfsService {

    @Value("${ipfs.url}")
    private String ipfsUrl;

    @Value("${folder.path}")
    private String folderPath;

    @Override
    public ResponseEntity<ResponseService> uploadFile(MultipartFile file) {
        OkHttpClient client = new OkHttpClient();
        ResponseService responseService = new ResponseService();
        try {
            // Bangun request body untuk multipart
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            "file",
                            file.getOriginalFilename(),
                            RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream"))
                    )
                    .build();

            // Buat request POST ke /add
            Request request = new Request.Builder()
                    .url(ipfsUrl + "/add")
                    .post(requestBody)
                    .build();

            // Eksekusi request
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                Object responseBody = response.body();
                log.info("IPFS Upload Success: {}", responseBody);

                responseService.setStatusCode("000");
                responseService.setStatus(true);
                responseService.setMessage("IPFS Upload Success");
                responseService.setData(responseBody);

                return ResponseEntity.ok(responseService);
            } else {
                log.error("IPFS Upload Failed: {}", response.message());

                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("IPFS Upload Failed");
                responseService.setData(null);

                return ResponseEntity.ok(responseService);
            }
        } catch (IOException e) {
            log.error("Error uploading to IPFS", e);

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage("General Error");
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }
}


//        @Override
//        public ResponseEntity<ResponseService> getFile (MultipartFile file){
//            OkHttpClient client = new OkHttpClient();
//            ResponseService responseService = new ResponseService();
//            try {
//
//                responseService.setStatusCode("000");
//                responseService.setStatus(true);
//                responseService.setMessage("IPFS Upload Success");
//                responseService.setData(responseBody);
//
//                return ResponseEntity.ok(responseService);
//                if (response.isSuccessful()) {
//                } else {
//                    log.error("IPFS Upload Failed: {}", response.message());
//
//                    responseService.setStatusCode("901");
//                    responseService.setStatus(false);
//                    responseService.setMessage("IPFS Upload Failed");
//                    responseService.setData(null);
//
//                    return ResponseEntity.ok(responseService);
//                }
//            } catch (IOException e) {
//                log.error("Error uploading to IPFS", e);
//
//                responseService.setStatusCode("999");
//                responseService.setStatus(false);
//                responseService.setMessage("General Error");
//                responseService.setData(null);
//
//                return ResponseEntity.ok(responseService);
//
//            }
//        }


//        @Override
//        public ResponseEntity<ResponseService> pinFile (MultipartFile file){
//            OkHttpClient client = new OkHttpClient();
//            ResponseService responseService = new ResponseService();
//            try {
//
//                responseService.setStatusCode("000");
//                responseService.setStatus(true);
//                responseService.setMessage("IPFS Upload Success");
//                responseService.setData(responseBody);
//
//                return ResponseEntity.ok(responseService);
//                if (response.isSuccessful()) {
//                } else {
//                    log.error("IPFS Upload Failed: {}", response.message());
//
//                    responseService.setStatusCode("901");
//                    responseService.setStatus(false);
//                    responseService.setMessage("IPFS Upload Failed");
//                    responseService.setData(null);
//
//                    return ResponseEntity.ok(responseService);
//                }
//            } catch (IOException e) {
//                log.error("Error uploading to IPFS", e);
//
//                responseService.setStatusCode("999");
//                responseService.setStatus(false);
//                responseService.setMessage("General Error");
//                responseService.setData(null);
//
//                return ResponseEntity.ok(responseService);
//
//            }
//        }

//        @Override
//        public ResponseEntity<ResponseService> unpinFile (MultipartFile file){
//            OkHttpClient client = new OkHttpClient();
//            ResponseService responseService = new ResponseService();
//            try {
//
//                responseService.setStatusCode("000");
//                responseService.setStatus(true);
//                responseService.setMessage("IPFS Upload Success");
//                responseService.setData(responseBody);
//
//                return ResponseEntity.ok(responseService);
//                if (response.isSuccessful()) {
//                } else {
//                    log.error("IPFS Upload Failed: {}", response.message());
//
//                    responseService.setStatusCode("901");
//                    responseService.setStatus(false);
//                    responseService.setMessage("IPFS Upload Failed");
//                    responseService.setData(null);
//
//                    return ResponseEntity.ok(responseService);
//                }
//            } catch (IOException e) {
//                log.error("Error uploading to IPFS", e);
//
//                responseService.setStatusCode("999");
//                responseService.setStatus(false);
//                responseService.setMessage("General Error");
//                responseService.setData(null);
//
//                return ResponseEntity.ok(responseService);
//
//            }
//        }

//        @Override
//        public ResponseEntity<ResponseService> listPin (MultipartFile file){
//            OkHttpClient client = new OkHttpClient();
//            ResponseService responseService = new ResponseService();
//            try {
//                // Bangun request body untuk multipart
//                RequestBody requestBody = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart(
//                                "file",
//                                file.getOriginalFilename(),
//                                RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream"))
//                        )
//                        .build();
//
//                // Buat request POST ke /add
//                Request request = new Request.Builder()
//                        .url(ipfsUrl + "/add")
//                        .post(requestBody)
//                        .build();
//
//                // Eksekusi request
//                Response response = client.newCall(request).execute();
//
//                if (response.isSuccessful()) {
//                    Object responseBody = response.body();
//                    log.info("IPFS Upload Success: {}", responseBody);
//
//                    responseService.setStatusCode("000");
//                    responseService.setStatus(true);
//                    responseService.setMessage("IPFS Upload Success");
//                    responseService.setData(responseBody);
//
//                    return ResponseEntity.ok(responseService);
//                } else {
//                    log.error("IPFS Upload Failed: {}", response.message());
//
//                    responseService.setStatusCode("901");
//                    responseService.setStatus(false);
//                    responseService.setMessage("IPFS Upload Failed");
//                    responseService.setData(null);
//
//                    return ResponseEntity.ok(responseService);
//                }
//            } catch (IOException e) {
//                log.error("Error uploading to IPFS", e);
//
//                responseService.setStatusCode("999");
//                responseService.setStatus(false);
//                responseService.setMessage("General Error");
//                responseService.setData(null);
//
//                return ResponseEntity.ok(responseService);
//
//            }
//        }


