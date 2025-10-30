package com.blockchain.blockchain_service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class ContractGenerator {

    public static void generate(String contractName) {
        try {
            // Path ke file hasil build Truffle
            File jsonFile = new File("src/main/resources/smartContract/build/contracts/" + contractName + ".json");

            if (!jsonFile.exists()) {
                throw new RuntimeException("File tidak ditemukan: " + jsonFile.getAbsolutePath());
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonFile);

            // Ambil abi dan bin
            String abi = mapper.writeValueAsString(json.get("abi"));
            String bin = json.get("bytecode").asText();

            // Simpan sementara
            Files.write(Paths.get(contractName + ".abi"), abi.getBytes(StandardCharsets.UTF_8));
            Files.write(Paths.get(contractName + ".bin"), bin.getBytes(StandardCharsets.UTF_8));

            // Argumen generator Web3j
            String[] params = {
                    "-a", contractName + ".abi",
                    "-b", contractName + ".bin",
                    "-o", "src/main/java",
                    "-p", "com.blockchain.blockchain_service.service.host"
            };

            // Jalankan generator Web3j
            SolidityFunctionWrapperGenerator.main(params);

            System.out.println("File Java berhasil dibuat di src/main/java/com/blockchain/blockchain_service/service/host/" + contractName + ".java");
        } catch (Exception e) {
            System.out.println("Error woi");
        }
    }
}
