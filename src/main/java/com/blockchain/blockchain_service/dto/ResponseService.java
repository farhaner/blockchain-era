package com.blockchain.blockchain_service.dto;

import lombok.Data;

@Data
public class ResponseService {

    private String statusCode;
    private boolean status;
    private String message;
    private Object data;
}
