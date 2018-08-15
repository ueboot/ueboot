package com.ueboot.file.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 采用DES加密，能与.net互通加密解密。
 * 
 * @author YangKui
 *
 */
public class DesUtil {
	

	// 解密数据
	public static String decrypt(String message, String key) throws Exception {
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte);
	}

	public static byte[] encrypt2Byte(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes("UTF-8"));
	}

	public static byte[] convertHexString(String ss) {
		byte[] digest = new byte[ss.length() / 2];

		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, (2 * i) + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	public static String encrypt(String message, String key) throws Exception {
		return toHexString(encrypt2Byte(message, key)).toUpperCase();
	}

	public static void main(String[] args) throws Exception {
		String key = "Sinochem";
		String jiami = "sdfs^4545s";
		System.out.println("加密数据:" + jiami);

		String a = DesUtil.encrypt(jiami, key);

		System.out.println("加密后的数据为:" + a);

		String b = decrypt(a, key);
		System.out.println("解密后的数据:" + b);
	}

	public static String toHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);

			if (plainText.length() < 2) {
				plainText = "0" + plainText;
			}

			hexString.append(plainText);
		}

		return hexString.toString();
	}
}