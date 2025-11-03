package com.blockchain.blockchain_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.blockchain.blockchain_service.utils.ContractGenerator.generate;

@SpringBootApplication
public class BlockchainServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Gunakan/aktifkan ketika hanya untuk generate java wrapper
                 generate("NasabahContract");
    }
}
