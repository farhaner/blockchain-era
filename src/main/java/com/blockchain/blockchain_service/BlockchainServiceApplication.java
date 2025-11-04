package com.blockchain.blockchain_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import static com.blockchain.blockchain_service.utils.ContractGenerator.generate;
import static com.blockchain.blockchain_service.utils.IpfsDaemonRunner.runnerDaemon;

@SpringBootApplication
@Slf4j
public class BlockchainServiceApplication implements CommandLineRunner {

    @Value("${contract.path}")
    String folderPath;

    @Override
    public void run(String... args) throws Exception {
        File file = new File(folderPath);

        // Ambil semua file dan folder di dalamnya
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            log.info("Generate java wrapper.......");
            generate("NasabahContract");
        } else {
            log.info("Java wrapper is exist bro");
        }
        runnerDaemon();
        log.info("=============BLOCKCHAIN ACTIVED=============");
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockchainServiceApplication.class, args);
    }

}
