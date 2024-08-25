package io.github.dodo939.utils;

import java.nio.charset.StandardCharsets;

public class MD5Util {

    public static String getMD5(String str) {
        return org.springframework.util.DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }
}
