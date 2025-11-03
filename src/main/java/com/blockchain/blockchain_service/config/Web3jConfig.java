package com.blockchain.blockchain_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Value("${web3j.client-address}")
    private String clientAddress;

    @Value("${wallet.private-key}")
    private String privateKey;

    @Bean
    public Web3j web3j() {
        // Membuat koneksi ke node blockchain
        Web3j web3j = Web3j.build(new HttpService(clientAddress));
        System.out.println("✅ Tersambung ke blockchain: " + clientAddress);
        return web3j;
    }

    @Bean
    public Credentials credentials() {
        // Membuat credential dari private key
        Credentials credentials = Credentials.create(privateKey);
        System.out.println("👛 Wallet address: " + credentials.getAddress());
        return credentials;
    }
}
