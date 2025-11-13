package com.blockchain.blockchain_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockchainServiceApplicationTests {

//    @Value("${ipfs.url}")
//    private String ipfsUrl;
//
//    @Value("${folder.path}")
//    private String folderPath;

    @Test
    void contextLoads() {
        try {
            ProcessBuilder pb = new ProcessBuilder("ipfs", "id");
            Process process = pb.start();
            int exitCode = process.waitFor();
            System.out.println(exitCode == 0);
        } catch (Exception e) {
            System.out.println(false);
        }
    }

}
