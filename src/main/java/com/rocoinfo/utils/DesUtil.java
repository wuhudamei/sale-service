package com.rocoinfo.utils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * 3DES(DESede) + 字符串 反转 随机 加密类
 *
 * @author yunpeng@xiu8.com
 * @version 2014年8月7日
 */
public class DesUtil {
    private static final byte[] Key = new byte[]{0x09, 0x35, 0x11, 0x18, 0x78, 0x3f, 0x7c, 0x5d, 0x71, 0x44, 0x29, 0x5b, 0x31, 0x40, 0x18, 0x7d, 0x21, 0x64, 0x29, 0x53, 0x5b, 0x40, 0x55, 0x4c};
    private static final String[] rans = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "g", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final int qIndex = 8;
    private static final int hIndex = 6;
    private static final String Algorithm = "DESede"; // DES,DESede,Blowfish

    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return Base64.encode(c1.doFinal(src)).getBytes();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(Base64.decode(new String(src)));
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    private static String enReverse(String code) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int rIndex = r.nextInt(9) + 1;
        sb.append(code.substring(rIndex));
        sb.reverse();
        sb.append(code.substring(0, rIndex));
        sb.append(rIndex);
        sb.reverse();
        int ran = 0;
        for (int i = 0; i < hIndex; i++) {
            ran = r.nextInt(rans.length - 1);
            sb.append(rans[ran]);
        }
        StringBuilder sq = new StringBuilder();
        for (int i = 0; i < qIndex; i++) {
            ran = r.nextInt(rans.length - 1);
            sq.append(rans[ran]);
        }
        return sq.append(sb).toString();
    }

    private static String deReverse(String code) {
        code = code.substring(qIndex, code.length() - hIndex);
        StringBuilder sb = new StringBuilder(code);
        code = sb.reverse().toString();
        int rIndex = Integer.valueOf(code.substring(code.length() - 1));
        int len = code.length();
        code = code.substring(0, len - 1);
        len = len - 1;
        rIndex = len - rIndex;
        sb = new StringBuilder();
        sb.append(code.substring(0, rIndex));
        String q = sb.reverse().toString();
        sb = new StringBuilder();
        sb.append(code.substring(rIndex));
        sb.append(q);
        return sb.toString();
    }

    public static String encode(String src) {
        byte[] encoded = encryptMode(Key, src.getBytes());
        return enReverse(new String(encoded));
    }

    public static String decode(String enSrc) {
        byte[] srcBytes = decryptMode(Key, deReverse(enSrc).getBytes());
        return new String(srcBytes);
    }

    public static void main(String[] args){
        String s = decode("niaYrQQR9WFAlVNk4OQ45uab+h/MirhXkX5PKOPFKodz2D9yrCvkF2G/9EwwVeBOH7h1QE8NuRTBCbyHxHICS9nqwXBJiIyMkJel2O92YvESfTCmAMOjKB/BbQnXG3RTe8OZ52eTmLtDSo0QvHmjaLZYlhHFVvwJ/ZlLF4QbJyKEysj506jznpfeDNBPhWA==i04gPo");
        System.out.println(s);
    }
}
