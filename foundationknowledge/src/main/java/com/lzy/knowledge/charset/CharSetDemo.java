/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.lzy.knowledge.charset;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 字符集Demo
 *
 * @author taigai
 * @version CharSetDemo.java, v 0.1 2020年06月14日 18:58 taigai Exp $
 */
public class CharSetDemo {

    private static final int MASK = 0X01;

    public static void main(String[] args) throws Exception{
        String s = "🔑😆";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            int codePoint = s.codePointAt(i);
            System.out.println(i + ": " + codePoint);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                i++;
            }
        }
        System.out.println(s.toString());
    }

    /**
     * 通过此网址可以将 字符和Unicode辅助平面编码 进行互相转换
     * <p> http://www.russellcottrell.com/greek/utilities/surrogatepaircalculator.htm
     */
    public static void convertCodePointAndChar() {
        // 🔑的Unicode为：U+1F511,Surrogate Pair 为：\uD83D\uDD11
        //String key = "\uD83D\uDD11";
        String str = "🔑开room";
        int len = str.length();
        int codePointCount = str.codePointCount(0, len);

        System.out.println("key.length=" + len);
        System.out.println("key.codePointCount=" + codePointCount);

        // 无法正常处理
        for (int i = 0; i < codePointCount; i++) {
            int codePoint = str.codePointAt(i);
            System.out.println(
                    "code point at " + i + ", codePoint=" + codePoint + ",isSurrogate=" + Character.isSupplementaryCodePoint(codePoint));
        }

        System.out.println("===========================");

        // 可以正常处理
        for (int i = 0; i < len; i++) {
            int codePoint = str.codePointAt(i);
            System.out.println(
                    "code point at " + i + ", codePoint=" + codePoint + ",isSurrogate=" + Character.isSupplementaryCodePoint(codePoint));
            if (Character.isSupplementaryCodePoint(codePoint)) {
                i++;
            }
        }

        System.out.println("===========================");

        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            System.out.println("char at " + i + ", isHighSurrogate=" + Character.isHighSurrogate(c) + ", isLowSurrogate=" + Character
                    .isLowSurrogate(c));
            // 每个字符单行打印的话，前2个字符均无法单独正常展示
            System.out.println(c);
        }
    }

    public static void printCharCode() {
        // 🔑的Unicode为：U+1F511,Surrogate Pair 为：\uD83D\uDD11
        //String key = "\uD83D\uDD11";
        String str = "\uD83D\uDD11";
        System.out.println("UTF-8\t\t" + bytesToBits(str.getBytes(StandardCharsets.UTF_8)));
        // 会在前面多加一个BOM（byte order mark）: FEFF
        System.out.println("UTF-16\t\t" + bytesToBits(str.getBytes(StandardCharsets.UTF_16)));
        System.out.println("UTF-16LE\t" + bytesToBits(str.getBytes(StandardCharsets.UTF_16LE)));
        System.out.println("UTF-16BE\t" + bytesToBits(str.getBytes(StandardCharsets.UTF_16BE)));
    }

    public static String bytesToBits(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(byteToBit(b)).append(" ");
        }
        return sb.toString();
    }

    public static String byteToBit(byte b) {
        return "" + (b >> 7 & MASK) + (b >> 6 & MASK)
                + (b >> 5 & MASK) + (b >> 4 & MASK)
                + (b >> 3 & MASK) + (b >> 2 & MASK)
                + (b >> 1 & MASK) + (b & MASK);
    }

    public static String filterUtf8mb4(String str) {
        StringBuilder sb = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            int codePoint = str.codePointAt(i);
            if (!Character.isSupplementaryCodePoint(codePoint)) {
                sb.append(str.charAt(i));
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    public static String toUnicode(int codePoint) {
        return "\\u" + StringUtils.leftPad(Integer.toHexString(codePoint), 4, '0');
    }
}
