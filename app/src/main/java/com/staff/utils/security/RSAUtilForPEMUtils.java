package com.staff.utils.security;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;


/**
 * RSA加解密
 */
public class RSAUtilForPEMUtils {

//	private static final String ALGORITHM = "RSA";
//
//	private static final String KEYSTOREFILE = "rsa.keystorefile";
//
//	private static final String CERFILE = "rsa.cerfile";

	private static final String TAG = "RSAUtilForPEMUtils";

	
	/**
     * 用默认私钥对参数串进行签名
     * @param param
     * @return
     */
    public static String signData(Context context, String param, String keyFileName) {
        try {
            Signature dsa = Signature.getInstance("SHA1withRSA");
            dsa.initSign(loadSignatureKey(getKeyFromFile(keyFileName, context)));
            dsa.update(param.getBytes());
            return new String(Base64.encode(dsa.sign(),Base64.DEFAULT));
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    
    private static RSAPrivateKey loadSignatureKey(String privateKeyStr)
			throws Exception {
		try {
			byte[] buffer = Base64.decode(privateKeyStr, Base64.DEFAULT);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			Log.i("tag", "加载私钥失败");
			Log.e("tag", e+"");
			throw e;
		}
	}
	
	/**
	 * 加密算法
	 * @param content
	 * @param cerFile
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private static String encrypt(String content, String cerFile,
			Context context) throws Exception {
        //直接 RSA 时 则必须 new BouncyCastleProvider()(第三方jar包) 和java服务端对接这套可以，但是ios貌似不适用这套
//		Cipher cipher = Cipher.getInstance(ALGORITHM,       
//				new BouncyCastleProvider());                
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");    //这套 三方都通用

		cipher.init(Cipher.ENCRYPT_MODE,
				loadPublicKey(getKeyFromFile(cerFile, context)));

		byte[] encrypted = cipher.doFinal(content.getBytes());

		return encode(encrypted);
	}

	/**
	 * 解密算法
	 * @param content
	 * @param keyFileName
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private static String decrypt(String content, String keyFileName,
			Context context) throws Exception {

//		Cipher cipher = Cipher.getInstance(ALGORITHM,
//				new BouncyCastleProvider());
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 

		cipher.init(Cipher.DECRYPT_MODE,
				loadPrivateKey(getKeyFromFile(keyFileName, context)));

		byte[] decrypted = cipher.doFinal(new BASE64Decoder()
				.decodeBuffer(content));

		return new String(decrypted);
	}

	public static String decode(String source) throws Exception {
		String result = source;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] userCertBase64;
		userCertBase64 = decoder.decodeBuffer(source);
		result = new String(userCertBase64);
		return result;
	}

	public static String encode(String source) {
		String result = source;
		BASE64Encoder encoder = new BASE64Encoder();
		result = encoder.encode(source.getBytes());
		return result;
	}

	public static String encode(byte[] source) {
		BASE64Encoder encoder = new BASE64Encoder();
		String result = encoder.encode(source);
		return result;
	}

	/**
	 * 加密
	 * @param content
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String encrypt(Context context, String content,
			String fileName) {

		try {
			return encrypt(content, fileName, context);
		} catch (Exception e) {
			return content;
		}
	}

	/**
	 * 解密
	 * @param content
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static String decrypt(Context context, String content,
			String fileName) {

		try {
			return decrypt(content, fileName, context);
		} catch (Exception e) {
			return content;
		}
	}

	/**
	 * 根据加密文件得到加密key
	 * @param keyFileName
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private static String getKeyFromFile(String keyFileName, Context context)
			throws Exception {

		// InputStream stream =
		// RSAUtilForPA.class.getResourceAsStream(filePath);
		InputStream stream = context.getAssets().open(keyFileName);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(stream));

		String line = null;
		List<String> list = new ArrayList<String>();
		while ((line = bufferedReader.readLine()) != null) {
			list.add(line);
		}

		// remove the firt line and last line
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 1; i < list.size() - 1; i++) {
			stringBuilder.append(list.get(i)).append("\r");
		}

		String key = stringBuilder.toString();
		return key;
	}

	/**
	 * 加载私钥
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	private static RSAPrivateKey loadPrivateKey(String privateKeyStr)
			throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}

	/**
	 * 加载公钥
	 * @param publicKeyStr
	 * @return
	 * @throws Exception
	 */
	private static RSAPublicKey loadPublicKey(String publicKeyStr)
			throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw e;
		}
	}


}
