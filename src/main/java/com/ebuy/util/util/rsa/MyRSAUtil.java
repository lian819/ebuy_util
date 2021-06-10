package com.ebuy.util.util.rsa;

import apiplatform.util.Base64Util;
import apiplatform.util.HexUtil;
import apiplatform.util.SignUtil;

import javax.crypto.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2021/5/18
 */
public class MyRSAUtil {

    public final static class SignAlgorithm {
        public final static String SHA1withRSA = "SHA1withRSA";
        public final static String SHA224withRSA = "SHA224withRSA";
        public final static String SHA256withRSA = "SHA256withRSA";
        public final static String SHA384withRSA = "SHA384withRSA";
        public final static String SHA512withRSA = "SHA512withRSA";
    }

    private final static byte[] PEM_LINE_SEPARATOR = new byte[]{ 0x0a };
    private final static int PEM_LINE_LEN = 76;


    public static boolean verifySign(
            String signAlgorithm,
            PublicKey publicKey,
            byte[] input, int inputOffset, int inputLen,
            byte[] signature, int signOffset, int signLen
    ) {
        try {
            Signature sign = Signature.getInstance(signAlgorithm);
            sign.initVerify(publicKey);

            //sign.update(input, inputOffset, inputLen);
            ByteArrayInputStream in = new ByteArrayInputStream(input, inputOffset, inputLen);
            try {
                SignUtil.doSign(sign, in);
            } finally {
                in.close();
            }

            return sign.verify(signature, signOffset, signLen);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sign(
            String signAlgorithm,
            PrivateKey privateKey,
            byte[] input, int offset, int len
    ) {
        try {
            SecureRandom random = createSecureRandom();

            Signature sign = Signature.getInstance(signAlgorithm);
            sign.initSign(privateKey, random);

            //sign.update(input, offset, len);

            ByteArrayInputStream in = new ByteArrayInputStream(input, offset, len);
            try {
                SignUtil.doSign(sign, in);
                return sign.sign();
            } finally {
                in.close();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String publicKeyHexToPem(String hex) throws IOException {
        String base64 = hexToBase64(hex).replace("\n", "").replace("\r", "");

        char[] cArr = base64.toCharArray();

        //format to 64
        StringBuilder output = new StringBuilder();
        output.append("-----BEGIN PUBLIC KEY-----").append('\n');
        int start = 0;
        while(true) {
            int end = start + 64;
            if(end >= cArr.length) {
                output.append(cArr, start, cArr.length - start).append('\n');
                break;
            }

            output.append(cArr, start, 64).append('\n');
            start += 64;
        }

        output.append("-----END PUBLIC KEY-----");

        return output.toString();
    }

    private static String hexToBase64(String hex) throws IOException {
        return new String(hexToBase64Bytes(hex));
    }

    private static byte[] hexToBase64Bytes(String hex) throws IOException {
        byte[] bytes = new byte[hex.length() / 2];
        HexUtil.parseHexString(hex, bytes, 0);

        return Base64Util.encodeToBytes(bytes, PEM_LINE_LEN, PEM_LINE_SEPARATOR);
    }

    /*
    private static String base64ToHex(String base64) throws IOException {
        return HexUtil.toHexString(decodeBase64(base64));
    }

    private static byte[] decodeBase64(String base64) throws IOException {
        return Base64Util.decode(base64);
    }
    */

    public static PublicKey readPublicKeyFromPem(File pemFile) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        return readPublicKeyFromPem(readText(pemFile));
    }

    public static PublicKey readPublicKeyFromPem(String pemStr) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Util.decode(formatPemToBase64(pemStr)));
        PublicKey pubkey = keyFactory.generatePublic(keySpec);

        return pubkey;
    }

    public static PrivateKey readPrivateKeyFromPem(File pemFile) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String pemStr = readText(pemFile);

        return readPrivateKeyFromPem(pemStr);
    }

    public static PrivateKey readPrivateKeyFromPem(String pemStr) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64Util.decode(formatPemToBase64(pemStr))
        );
        PrivateKey prikey = keyFactory.generatePrivate(keySpec);

        return prikey;
    }

    public static byte[] encrypt(Key key, byte[] inputBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            //return cipher.doFinal(input);

            ByteArrayInputStream input = new ByteArrayInputStream(inputBytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                doCipher(cipher, key, true, input, output);
                return output.toByteArray();
            } finally {
                input.close();
                output.close();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(Key key, byte[] inputBytes) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            //return cipher.doFinal(input);

            ByteArrayInputStream input = new ByteArrayInputStream(inputBytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                doCipher(cipher, key, false, input, output);
                return output.toByteArray();
            } finally {
                input.close();
                output.close();
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static void doCipher(
            Cipher cipher,
            Key key,
            boolean isEncrypt,
            InputStream input,
            OutputStream output
    ) throws IOException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        final int readBlockLen, writeBlockLen;
        {
            final String fmt = key.getFormat();
            final int encodedKeyLen = key.getEncoded().length;

            int mul;
            if(fmt.indexOf("PKCS") >= 0) {
                //private key
                mul = (int) Math.pow(2, ((int) (Math.log(encodedKeyLen / 256) / Math.log(2))));
            } else {
                //public key
                mul = (int) Math.pow(2, ((int) (Math.log(encodedKeyLen / 64) / Math.log(2))));
            }
            if(isEncrypt) {
                readBlockLen = 53 * mul;
            } else {
                readBlockLen = 64 * mul;
            }

            writeBlockLen = 128 * mul;
        }

        final byte[] readBuf = new byte[readBlockLen];
        final byte[] writeBuf = new byte[writeBlockLen];
        final ByteBuffer lastBlock = ByteBuffer.wrap(readBuf);
        while (true) {
            final int nRead = input.read(readBuf);
            if(nRead < 0) {
                break;
            }

            int nWrite = cipher.doFinal(readBuf, 0, nRead, writeBuf);
            output.write(writeBuf, 0, nWrite);
        }
    }

    public static KeyPair generateKeyPair(int keyBitLen) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        KeyPairGenerator keyGen = createKeyPairGenerator(keyBitLen);

        return keyGen.generateKeyPair();
    }

    private static KeyPairGenerator createKeyPairGenerator(int keyBitLen) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

        SecureRandom random = createSecureRandom();
        keyGen.initialize(keyBitLen, random);

        return keyGen;
    }

    private static String formatPemToBase64(String pemStr) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(pemStr));
        try {
            StringBuilder str = new StringBuilder();

            while(true) {
                String line = reader.readLine();
                if(line == null) {
                    break;
                }

                if(line.charAt(0) == '-') {
                    continue;
                }

                if(line.length() > 0) {
                    str.append(line);
                }
            }

            return str.toString();
        } finally {
            reader.close();
        }
    }

    private static String readText(File file) throws IOException {
        char[] cBuf = new char[512];

        InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
        try {
            StringBuilder str = new StringBuilder();
            int readLen;
            while(true) {
                readLen = reader.read(cBuf);
                if(readLen < 0) {
                    break;
                }

                if(readLen > 0) {
                    str.append(cBuf, 0, readLen);
                }
            }

            return str.toString();
        } finally {
            reader.close();
        }
    }

    private static SecureRandom createSecureRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG");
    }
}
