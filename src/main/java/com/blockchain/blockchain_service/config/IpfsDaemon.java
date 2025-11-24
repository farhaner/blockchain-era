//package com.blockchain.blockchain_service.config;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//import java.util.Map;
//
//
//@Component("ipfsDaemon")
//public class IpfsDaemon {
//
//    private Process ipfsProcess;
//    private boolean daemonReady = false;
//    private Thread monitorThread;
//
//    @PostConstruct
//    public void startIpfsDaemon() {
//        try {
//            // 🔹 Cek apakah sudah berjalan
//            Process check = new ProcessBuilder("ipfs", "swarm", "peers").start();
//            if (check.waitFor() == 0) {
//                System.out.println("ℹ️ IPFS daemon already running.");
//                daemonReady = true;
//                startMonitorThread(); // tetap monitor walau sudah jalan
//                return;
//            }
//
//            System.out.println("🚀 Starting IPFS daemon in background...");
//
//            ProcessBuilder pb = new ProcessBuilder("ipfs", "daemon");
//            pb.redirectErrorStream(true);
//            ipfsProcess = pb.start();
//
//            // 🔹 Baca log daemon di thread terpisah
//            new Thread(() -> {
//                try (BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(ipfsProcess.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println("[IPFS] " + line);
//                        if (line.contains("Daemon is ready")) {
//                            daemonReady = true;
//                            System.out.println("✅ IPFS daemon is ready to accept requests!");
//                            startMonitorThread();
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//
//            // 🔹 Tunggu sampai siap
//            int retries = 0;
//            while (!daemonReady && retries < 15) {
//                Thread.sleep(1000);
//                retries++;
//            }
//
//            if (!daemonReady) {
//                System.err.println("⚠️ IPFS daemon did not report 'ready' within timeout. Proceeding anyway.");
//            }
//
//        } catch (IOException e) {
//            System.err.println("❌ IPFS executable not found! Make sure 'ipfs' is installed and added to PATH.");
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private void startMonitorThread() {
//        if (monitorThread != null && monitorThread.isAlive()) return;
//
//        monitorThread = new Thread(() -> {
//            try {
//                while (true) {
//                    try {
//                        // Ambil info langsung dari REST API IPFS
//                        Map<String, Object> versionResp = fetchJson("http://127.0.0.1:5001/api/v0/version");
//                        Map<String, Object> repoResp = fetchJson("http://127.0.0.1:5001/api/v0/repo/stat");
//                        Map<String, Object> peersResp = fetchJson("http://127.0.0.1:5001/api/v0/swarm/peers");
//
//                        String version = versionResp.get("Version").toString();
//                        int peers = peersResp.containsKey("Peers")
//                                ? ((List<?>) peersResp.get("Peers")).size()
//                                : 0;
//                        long repoSize = Long.parseLong(repoResp.get("RepoSize").toString());
//
//                        System.out.printf("""
//                    ---------------------------
//                    📡 IPFS Node Monitor
//                    🔹 Version     : %s
//                    🔹 Peers       : %d
//                    🔹 Repo Size   : %.2f MB
//                    🔹 Status      : ONLINE
//                    ---------------------------
//                    """, version, peers, repoSize / (1024.0 * 1024.0));
//
//                        Thread.sleep(10_000);
//                    } catch (Exception e) {
//                        System.err.println("⚠️ IPFS seems offline or unreachable: " + e.getMessage());
//                        Thread.sleep(10_000);
//                    }
//                }
//            } catch (Exception e) {
//                System.err.println("❌ Failed to start IPFS monitor thread: " + e.getMessage());
//            }
//        });
//        monitorThread.setDaemon(true);
//        monitorThread.start();
//    }
//
//    @SuppressWarnings("unchecked")
//    private Map<String, Object> fetchJson(String url) throws IOException {
//        var conn = new java.net.URL(url).openConnection();
//        conn.setConnectTimeout(2000);
//        conn.setReadTimeout(3000);
//        try (var in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()))) {
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = in.readLine()) != null) sb.append(line);
//            return new com.fasterxml.jackson.databind.ObjectMapper().readValue(sb.toString(), Map.class);
//        }
//    }
//
//
//
//    public boolean isDaemonReady() {
//        return daemonReady;
//    }
//
//    @PreDestroy
//    public void stopIpfsDaemon() {
//        System.out.println("🛑 Shutting down IPFS...");
//        if (ipfsProcess != null && ipfsProcess.isAlive()) {
//            ipfsProcess.destroy();
//        }
//        if (monitorThread != null && monitorThread.isAlive()) {
//            monitorThread.interrupt();
//        }
//    }
//}
