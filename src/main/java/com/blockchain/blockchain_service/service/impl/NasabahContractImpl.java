package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.contract.NasabahContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Service
public class NasabahContractImpl {

    private final Web3j web3j;
    private final Credentials credentials;
    private final NasabahContract nasabahContract;
    private final ContractGasProvider gasProvider;

    public NasabahContractImpl(
            Web3j web3j,
            Credentials credentials,
            @Value("${contract.goldSavings.address}") String contractAddress
    ) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = new StaticGasProvider(
                BigInteger.valueOf(20000000000L),  // Gas Price: 20 Gwei
                BigInteger.valueOf(6721975L)       // Gas Limit
        );
        this.nasabahContract = NasabahContract.load(contractAddress, web3j, credentials, gasProvider);
    }
}
