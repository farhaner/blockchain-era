package com.blockchain.blockchain_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

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
            String url = "src/main/java/com/blockchain/blockchain_service/contract/";
            File folder = new File(url);

            // Cek apakah folder ada dan memang folder
            if (!folder.exists() || !folder.isDirectory()) {
                System.out.println(List.of("Folder tidak ditemukan atau bukan folder: "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
