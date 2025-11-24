package com.blockchain.blockchain_service.controllers;

import com.blockchain.blockchain_service.dto.*;
import com.blockchain.blockchain_service.service.impl.IpfsServiceImpl;
import com.blockchain.blockchain_service.service.impl.NasabahContractImpl;
import com.blockchain.blockchain_service.utils.Formatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.utils.Numeric;

import java.util.*;

@RestController
@RequestMapping(value = "/api/telesales")
@RequiredArgsConstructor
public class BlockchainController {

    private final NasabahContractImpl nasabahService;
    private final IpfsServiceImpl ipfsService;


    // --- CREATE ---
    @PostMapping("/create-nasabah")
    public ResponseEntity<Object> createNasabah(@RequestParam("files") MultipartFile[] files, @RequestPart("data") String jsonData) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateNasabah nasabah = objectMapper.readValue(jsonData, CreateNasabah.class);
            List<FileMetaData> metaDatas = ipfsService.uploadFiles(Arrays.asList(files));

            List<String> cids = new LinkedList<>();

            for (var data: metaDatas){
                cids.add(data.getCid());
            }

            byte[] id = NasabahContractImpl.generateId(nasabah.getNik().toString());

            nasabah.setContractFileCids(cids);
            String strCreateNasabah = nasabahService.createNasabah(id, nasabah);
            Response response = new Response(true, "000", "Create Nasabah Successfully", nasabah);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response response = new Response(false, "999", "Create Nasabah Failed", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get-nasabah")
    public ResponseEntity<Object> getNasabah(
            @RequestParam(required = false) String nik,
            @RequestParam(required = false) String id
    ) throws Exception {
        try{
            byte[] ids;
            if(nik != null){
                ids = NasabahContractImpl.generateId(nik);
            } else {
                ids = NasabahContractImpl.hexToBytes32(id);
            }

            System.out.println(ids.toString());

            GetNasabah nasabah = nasabahService.getNasabah(ids);
            Response response = new Response(true, "000", "Get Nasabah Successfully", nasabah);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            System.out.println(e.toString());
            System.out.println(e.getLocalizedMessage());

            Response response = new Response(false, "999", "Get Nasabah Failed", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get-all-nasabah")
    public ResponseEntity<Object> getAllNasabah() throws Exception {
        try{
            List<String> nasabah = nasabahService.getAllNasabahIds();
            Response response = new Response(true, "000", "Get All Nasabah Successfully", nasabah);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            Response response = new Response(false, "999", "Get All Nasabah Failed", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/get-all-files")
    public ResponseEntity<Object> getAllFiles() throws Exception {
        try{
            List<String> nasabah = nasabahService.getAllFileCids();
            Response response = new Response(true, "000", "Get All Nasabah Successfully", nasabah);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            Response response = new Response(false, "999", "Get All Nasabah Failed", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/update-nasabah")
    public ResponseEntity<Object> updateNasabah(@RequestBody UpdateNasabah nasabah) throws Exception {
        try {
            String strUpdateNasabah = nasabahService.updateNasabah(nasabah);
            Response response = new Response(true, "000", "Update Nasabah Successfully", nasabah);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response response = new Response(false, "999", "Update Nasabah Failed", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/delete-nasabah")
    public ResponseEntity<Object> deleteNasabah(@RequestParam(required = true) String id) throws Exception {
        try {
            byte[] ids = NasabahContractImpl.hexToBytes32(id);

            String strDeleteNasabah = nasabahService.deleteNasabah(ids);
            GetNasabah nasabah = new GetNasabah();
            nasabah.setId(id);
            Response response = new Response(true, "000", "Delete Nasabah Successfully", nasabah);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response response = new Response(false, "999", "Delete Nasabah Failed", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
