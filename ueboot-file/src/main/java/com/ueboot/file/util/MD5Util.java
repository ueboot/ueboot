/* 文档密级：秘密 */
package com.ueboot.file.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Static functions to simplifiy common {@link MessageDigest} tasks.  This
 * class is thread safe.
 * 
 * @author 99bill
 *
 */
public class MD5Util {

    private MD5Util() {
    }

    /**
     * Returns a MessageDigest for the given <code>algorithm</code>.
     * 
     * @param algorithm
     *            The MessageDigest algorithm name.
     * @return An MD5 digest instance.
     * @throws RuntimeException
     *             when a {@link NoSuchAlgorithmException} is
     *             caught
     */

    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     * 
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }
    
//    public static void main(String[] args){
//    	String md = MD5Util.md5Hex("eupfile_!Abcwo29480CAei4859");
//    	System.out.println(md);
//    }
}
