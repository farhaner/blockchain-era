package com.blockchain.blockchain_service.config;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IpfsConfig {

    @Value("${ipfs.api.url}")
    private String ipfsApiUrl;

    @Bean
    public IPFS ipfsClient() {
        String multiAddr = ipfsApiUrl
                .replace("http://", "/ip4/")
                .replace(":5001", "/tcp/5001");

        System.out.println("🔗 Connecting to IPFS daemon at " + ipfsApiUrl);
        IPFS ipfs = new IPFS(multiAddr);

        // Tunggu sampai daemon siap (retry maksimal 8 kali)
        int maxRetry = 8;
        for (int attempt = 1; attempt <= maxRetry; attempt++) {
            try {
                ipfs.version();
                System.out.println("✅ Connected to IPFS daemon!");
                return ipfs;
            } catch (Exception e) {
                System.out.println("⏳ IPFS not ready yet (try " + attempt + "/" + maxRetry + ")");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        throw new RuntimeException("❌ Couldn't connect to IPFS daemon after retries.");
    }
}
