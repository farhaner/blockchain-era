package com.blockchain.blockchain_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpfsResponse {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Hash")
    private String hash;
    @JsonProperty("Size")
    private String size;
}
