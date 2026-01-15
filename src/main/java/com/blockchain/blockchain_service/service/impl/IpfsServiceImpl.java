package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.dto.IpfsResponse;
import com.blockchain.blockchain_service.dto.ResponseService;
import com.blockchain.blockchain_service.service.IpfsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpfsServiceImpl implements IpfsService {

    @Value("${ipfs.url.add}")
    private String ipfsUrlAdd;

    @Value("${ipfs.url.get}")
    private String ipfsUrlGet;

    @Value("${ipfs.url.unpin}")
    private String ipfsUrlUnpin;

    @Value("${ipfs.url.list.cid}")
    private String ipfsUrlListCid;

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    ResponseService responseService = new ResponseService();
    IpfsResponse ipfsResponse = new IpfsResponse();

    @Override
    public IpfsResponse uploadFile2(MultipartFile file) {
        IpfsResponse ipfsResponse1 = new IpfsResponse();
        try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                            "file",
                            file.getOriginalFilename(),
                            RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream"))
                    )
                    .build();

            Request request = new Request.Builder()
                    .url(ipfsUrlAdd)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();

            ipfsResponse = objectMapper.readValue(response.body().string(), IpfsResponse.class);
            log.info("Response IPFS {}:", ipfsResponse);

            if (response.isSuccessful()) {
                ipfsResponse1.setHash(ipfsResponse.getHash());
                ipfsResponse1.setName(ipfsResponse.getName());
                ipfsResponse1.setSize(ipfsResponse.getSize());

                return ipfsResponse1;
            } else {
                log.error("IPFS Upload Failed: {}", response.message());

                ipfsResponse1.setHash(ipfsResponse.getHash());
                ipfsResponse1.setName(ipfsResponse.getName());
                ipfsResponse1.setSize(ipfsResponse.getSize());

                return ipfsResponse1;
            }
        } catch (IOException e) {
            log.error("Error uploading to IPFS", e);

            ipfsResponse1.setHash(null);
            ipfsResponse1.setName(null);
            ipfsResponse1.setSize(null);

            return ipfsResponse1;
        }
    }

    @Override
    public ResponseEntity<ResponseService> getFile(String cid) {
        try {
            Request request = new Request.Builder()
                    .url(ipfsUrlGet + cid)
                    .post(okhttp3.RequestBody.create(new byte[0]))
                    .build();
            Response execute = client.newCall(request).execute();
            log.info("Response IPFS {}:", execute.body().string());

            if (execute.isSuccessful()) {
                responseService.setStatusCode("000");
                responseService.setStatus(true);
                responseService.setMessage("IPFS Get Success");
                responseService.setData(execute.body().string());

                return ResponseEntity.ok(responseService);
            } else {
                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("IPFS Get Failed");
                responseService.setData(null);

                return ResponseEntity.ok(responseService);
            }
        } catch (Exception e) {
            log.error("Error retrieving file from IPFS {}", e);

            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage("General Error");
            responseService.setData(null);

            return ResponseEntity.ok(responseService);
        }
    }

    @Override
    public ResponseEntity<ResponseService> getAllCid() {

        try {
            Request request = new Request.Builder()
                    .url(ipfsUrlListCid)
                    .post(okhttp3.RequestBody.create(new byte[0]))
                    .build();

            Response execute = client.newCall(request).execute();
            Map listCid = objectMapper.readValue(execute.body().string(), Map.class);
            if (execute.isSuccessful()) {

                responseService.setStatusCode("000");
                responseService.setStatus(true);
                responseService.setMessage("IPFS Get Success");
                responseService.setData(listCid);
                return ResponseEntity.ok(responseService);

            } else {
                responseService.setStatusCode("901");
                responseService.setStatus(false);
                responseService.setMessage("IPFS Get Failed");
                responseService.setData(null);
                return ResponseEntity.ok(responseService);

            }
        } catch (Exception e) {
            log.error("Error retrieving file from IPFS {}", e);
            responseService.setStatusCode("999");
            responseService.setStatus(false);
            responseService.setMessage("General Error");
            responseService.setData(null);
            return ResponseEntity.ok(responseService);
        }
    }
}