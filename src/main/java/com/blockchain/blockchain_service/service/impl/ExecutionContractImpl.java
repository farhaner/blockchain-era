package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.contract.NasabahContract;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import static com.blockchain.blockchain_service.contract.NasabahContract.load;

@Service
public class ExecutionContractImpl {

    private Web3j web3j;
    private Credentials credentials;
    private NasabahContract nasabahContract;

    public void BlockchainService() {
        // ✅ Inisialisasi langsung di constructor — dijamin gak error
        this.web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));
        this.credentials = Credentials.create("YOUR_PRIVATE_KEY"); // ganti dgn private key Ganache

        String contractAddress = "0xYourDeployedContractAddress"; // ganti dgn address hasil deploy

        this.nasabahContract = NasabahContract.load(
                contractAddress,
                this.web3j,
                this.credentials,
                new DefaultGasProvider()
        );
    }

    public ExecutionContractImpl(Web3j web3j, Credentials credentials, NasabahContract nasabahContract) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.nasabahContract = nasabahContract;
    }

}
