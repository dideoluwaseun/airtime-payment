package com.oluwaseun.airtimepayment.util;

import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PaymentHashGenerator {
    public static String calculateHMAC512(String data, String key) {

        String HMAC_SHA512 = "HmacSHA512";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);

        Mac mac = null;

        try {

            mac = Mac.getInstance(HMAC_SHA512);

            mac.init(secretKeySpec);

            return Hex.encodeHexString(mac.doFinal(data.getBytes()));

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {

            e.printStackTrace();

            throw new RuntimeException(e.getMessage());

        }

    }
}
