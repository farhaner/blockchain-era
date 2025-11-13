package com.blockchain.blockchain_service.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class ContractGenerator {


    public static void generate(String contractPath, String contractName, String abibinPath, String folderPath) throws IOException {
        try {
            File jsonFile = new File(contractPath + contractName + ".json");

            if (!jsonFile.exists()) {
                throw new RuntimeException("File tidak ditemukan: " + jsonFile.getAbsolutePath());
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonFile);

            System.out.println("===============Generation ABI BIN===============");

            // Ambil abi dan bin
            String abi = mapper.writeValueAsString(json.get("abi"));
            String bin = json.get("bytecode").asText();

            // Simpan sementara
            Files.write(Paths.get(abibinPath + contractName + ".abi"), abi.getBytes(StandardCharsets.UTF_8));
            Files.write(Paths.get(abibinPath + contractName + ".bin"), bin.getBytes(StandardCharsets.UTF_8));

            // Argumen generator Web3j
            String[] params = {
                    "-a", abibinPath + contractName + ".abi",
                    "-b", abibinPath + contractName + ".bin",
                    "-o", "src/main/java",
                    "-p", "com.blockchain.blockchain_service.contract"
            };

            System.out.println("===============Generation Java Wripper===============");
            SolidityFunctionWrapperGenerator.main(params);
            System.out.println("===============Complete===============");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error woi");
        }
    }
}
