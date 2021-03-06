package com.enioka.scanner.bt.api;

public final class Helpers {

    private Helpers(){}

    public static String byteArrayToHex(byte[] buffer, int length) {
        return byteArrayToHex(buffer, 0, length);
    }

    public static String byteArrayToHex(byte[] buffer, int offset, int length) {
        StringBuilder sb = new StringBuilder(length * 2);
        for (int i = offset; i < offset + length; i++) {
            sb.append(String.format("%02x ", buffer[i]));
        }
        return sb.toString();
    }
}
