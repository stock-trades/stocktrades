package io.stocktrades.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class ChecksumGenerator {

    public static String generateChecksum(String apiKey,String requestToken,String secret){
        String sha256hex = Hashing.sha256()
                .hashString(apiKey+requestToken+secret, StandardCharsets.UTF_8)
                .toString();
        return sha256hex;
    }
}
