package com.blockchain.blockchain_service.dto;

import lombok.Data;

@Data
public class RequestService {

    private String nik;

    private String fullName;

    private String birthDate;

    private String gender;

    private String nationality;

    private String fullAddress;

    private String phoneNumber;

    private CidRequest cid;

}