package com.blockchain.blockchain_service.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class CreateNasabah {
    private BigInteger nik;
    private String fullName;
    private List<String> contractFileCids;
}
