package com.blockchain.blockchain_service.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaData {
    private String fileName;
    private String mimeType;
    private String cid;
}