package com.blockchain.blockchain_service.utils;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Formatter {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatTimestamp(BigInteger timestamp) {
        if (timestamp == null) return "-";
        long seconds = timestamp.longValue();
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(seconds),
                ZoneId.of("Asia/Jakarta") // sesuaikan zona waktu kamu
        );
        return dateTime.format(FORMATTER);
    }
}

