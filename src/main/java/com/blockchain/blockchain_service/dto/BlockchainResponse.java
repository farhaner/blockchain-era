package com.blockchain.blockchain_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class BlockchainResponse {

    private String hash;
    private String programCounter;
    private String result;
    private String reason;
}
