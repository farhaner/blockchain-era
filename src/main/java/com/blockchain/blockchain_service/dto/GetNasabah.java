package com.blockchain.blockchain_service.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class GetNasabah {
    private String id;
    private BigInteger nik;
    private String fullName;
    private List<String> contractFileCids;
    private String owner;
    private String createTimeStamp;
    private String updateTimeStamp;
}
