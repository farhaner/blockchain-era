package com.blockchain.blockchain_service.utils;

import com.blockchain.blockchain_service.contract.CustomerContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Configuration
public class Web3jConfig {

    @Value("${ganache.url}")
    private String urlGanache;

    @Value("${contract.address}")
    private String contractAddress;

    @Value("${private.key}")
    private String privateKey;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(urlGanache));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }

    @Bean
    public ContractGasProvider gasProvider() {
        return new StaticGasProvider(BigInteger.valueOf(20_000_000_000L), BigInteger.valueOf(4_300_000));
    }

    @Bean
    public CustomerContract customerContract(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        return CustomerContract.load(contractAddress, web3j, credentials, gasProvider);
    }
}
