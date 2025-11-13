package com.blockchain.blockchain_service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Slf4j
public class IpfsDaemonRunner {

    public static void runnerDaemon() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ipfs", "daemon");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            log.info("===============Starting Ipfs Daemon===============");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
