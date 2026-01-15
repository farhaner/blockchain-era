package com.blockchain.blockchain_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class BlockchainServiceApplicationTests {

    @Test
    void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();
    }
}
