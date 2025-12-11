package com.blockchain.blockchain_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

import static com.blockchain.blockchain_service.utils.ContractGenerator.generate;
//import static com.blockchain.blockchain_service.utils.IpfsDaemonRunner.runnerDaemon;

@SpringBootApplication
@Slf4j
public class BlockchainServiceApplication implements CommandLineRunner {

    @Value("${java.wrapper.path}")
    String folderPath;

    @Value("${contract.path}")
    String contractPath;

    @Value("${contract.name}")
    String contractName;

    @Value("${abi.bin.path}")
    String abibinPath;

    @Override
    public void run(String... args) throws IOException {

        File file = new File(folderPath);
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            generate(contractPath, contractName, abibinPath, folderPath);
        } else {
            log.info("Java wrapper is exist bro");
        }
//        runnerDaemon();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockchainServiceApplication.class, args);
    }

}
