package com.blockchain.blockchain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private boolean status;
    private String statusCode;
    private String message;
    private Object data;
}
