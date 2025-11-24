package com.blockchain.blockchain_service.service.impl;

import com.blockchain.blockchain_service.dto.CreateNasabah;
import com.blockchain.blockchain_service.dto.GetNasabah;
import com.blockchain.blockchain_service.dto.UpdateNasabah;
import com.blockchain.blockchain_service.service.host.NasabahContract;
import com.blockchain.blockchain_service.utils.Formatter;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class NasabahContractImpl {

    private final Web3j web3j;
    private final Credentials credentials;
    private final NasabahContract nasabahContract;
    private final ContractGasProvider gasProvider;

    public NasabahContractImpl(
            Web3j web3j,
            Credentials credentials,
            @Value("${contract.address}") String contractAddress
    ) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = new StaticGasProvider(
                BigInteger.valueOf(20000000000L),  // Gas Price: 20 Gwei
                BigInteger.valueOf(6721975L)       // Gas Limit
        );
        this.nasabahContract = NasabahContract.load(contractAddress, web3j, credentials, gasProvider);
    }

    public String createNasabah(byte[] id, CreateNasabah nasabah) throws Exception {
        NasabahContract.NasabahInput input = new NasabahContract.NasabahInput(
                nasabah.getNik(),
                nasabah.getFullName(),
                nasabah.getContractFileCids()
        );

        TransactionReceipt receipt = nasabahContract.createNasabah(id, input).send();

        System.out.printf("Create Nasabah\nTransaction Hash : %s\nGas Used : %d",
                receipt.getTransactionHash(), receipt.getGasUsed());

        return String.format("\nCreate Nasabah\nTransaction Hash : %s\nGas Used : %d",
                receipt.getTransactionHash(), receipt.getGasUsed());
    }

    public GetNasabah getNasabah(byte[] id) throws Exception {
        Tuple7<byte[], String, BigInteger, List<String>, String, BigInteger, BigInteger> nasabahOnChain = nasabahContract.getNasabah(id).send();
//
        GetNasabah nasabah = new GetNasabah();
        nasabah.setId(bytes32ToHex(nasabahOnChain.getValue1()));
        nasabah.setNik(nasabahOnChain.getValue3());
        nasabah.setFullName(nasabahOnChain.getValue2());
        nasabah.setContractFileCids(nasabahOnChain.getValue4());
        nasabah.setOwner(nasabahOnChain.getValue5());
        nasabah.setCreateTimeStamp(Formatter.formatTimestamp(nasabahOnChain.getValue6()));
        nasabah.setUpdateTimeStamp(Formatter.formatTimestamp(nasabahOnChain.getValue7()));

//        nasabah.setNik(BigInteger.valueOf(900000));
//        nasabah.setFullName("Muha");
//        nasabah.setContractFileCids(new LinkedList<String>());
//        nasabah.setOwner("9hueqwme289");
//        nasabah.setCreateTimeStamp("021201");
//        nasabah.setUpdateTimeStamp("021201");
        return nasabah;
    }

    /**
     * Ambil semua ID nasabah
     */
    public List<String> getAllNasabahIds() throws Exception {
        List<byte[]> rawIds = nasabahContract.getAllNasabahIds().send();
        List<String> ids = new ArrayList<>();

        for (byte[] bytes : rawIds) {
            ids.add(bytes32ToHex(bytes));
            System.out.println(bytes);
        }
        return ids;
    }

    public List<String> getAllFileCids() throws Exception {
        List<String> rawIds = nasabahContract.getAllFileCids().send();

        return rawIds;
    }

    public String updateNasabah(UpdateNasabah nasabah) throws Exception {
        byte[] newId = generateId(nasabah.getNik().toString());

        NasabahContract.NasabahUpdate input = new NasabahContract.NasabahUpdate(
                newId,
                nasabah.getNik(),
                nasabah.getFullName()
        );

        TransactionReceipt receipt = nasabahContract.updateNasabah(hexToBytes32(nasabah.getId()), input).send();

        System.out.printf("Update Nasabah\nTransaction Hash : %s\nGas Used : %d",
                receipt.getTransactionHash(), receipt.getGasUsed());

        return String.format("\nUpdate Nasabah\nTransaction Hash : %s\nGas Used : %d",
                receipt.getTransactionHash(), receipt.getGasUsed());
    }

    public String deleteNasabah(byte[] id) throws Exception {
        TransactionReceipt receipt = nasabahContract.deleteNasabah(id).send();

        return String.format("\nUpdate Nasabah\nTransaction Hash : %s\nGas Used : %d",
                receipt.getTransactionHash(), receipt.getGasUsed());
    }

    // Helper: ubah bytes32 ke hex string agar mudah dilihat
    private String bytes32ToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return "0x" + sb.toString();
    }

    // Helper: membuat ID unik dari hash string
    public static byte[] generateId(String seed) {
        byte[] hash = org.web3j.crypto.Hash.sha3(seed.getBytes());
        return Arrays.copyOf(hash, 32);
    }


    public static byte[] hexToBytes32(String hex) {
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        }
        byte[] bytes = new BigInteger(hex, 16).toByteArray();

        byte[] result = new byte[32];

        // Jika length < 32, pad di depan
        if (bytes.length <= 32) {
            System.arraycopy(bytes, 0, result, 32 - bytes.length, bytes.length);
        } else {
            // Jika lebih dari 32, ambil bagian akhirnya
            System.arraycopy(bytes, bytes.length - 32, result, 0, 32);
        }

        return result;
    }


}
