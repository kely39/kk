package com.kk.d.qny.util;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yangqh
 * @date 2020/1/14
 **/
public class InnerUtils {
    private static final int CHUNK_SIZE = 1 << 22;

    public static byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("sha1");
        return mDigest.digest(data);
    }

    public static String urlSafeBase64Encode(byte[] data) {
        String encodedString = DatatypeConverter.printBase64Binary(data);
        encodedString = encodedString.replace('+', '-').replace('/', '_');
        return encodedString;
    }

    public static String calcETag(String fileName) throws IOException,
            NoSuchAlgorithmException {
        String etag = "";
        File file = new File(fileName);
        if (!(file.exists() && file.isFile() && file.canRead())) {
            System.err.println("Error: File not found or not readable");
            return etag;
        }
        long fileLength = file.length();
        FileInputStream inputStream = new FileInputStream(file);
        if (fileLength <= CHUNK_SIZE) {
            byte[] fileData = new byte[(int) fileLength];
            inputStream.read(fileData, 0, (int) fileLength);
            byte[] sha1Data = sha1(fileData);
            int sha1DataLen = sha1Data.length;
            byte[] hashData = new byte[sha1DataLen + 1];
            System.arraycopy(sha1Data, 0, hashData, 1, sha1DataLen);
            hashData[0] = 0x16;
            etag = urlSafeBase64Encode(hashData);
        } else {
            int chunkCount = (int) (fileLength / CHUNK_SIZE);
            if (fileLength % CHUNK_SIZE != 0) {
                chunkCount += 1;
            }
            byte[] allSha1Data = new byte[0];
            for (int i = 0; i < chunkCount; i++) {
                byte[] chunkData = new byte[CHUNK_SIZE];
                int bytesReadLen = inputStream.read(chunkData, 0, CHUNK_SIZE);
                byte[] bytesRead = new byte[bytesReadLen];
                System.arraycopy(chunkData, 0, bytesRead, 0, bytesReadLen);
                byte[] chunkDataSha1 = sha1(bytesRead);
                byte[] newAllSha1Data = new byte[chunkDataSha1.length
                        + allSha1Data.length];
                System.arraycopy(allSha1Data, 0, newAllSha1Data, 0,
                        allSha1Data.length);
                System.arraycopy(chunkDataSha1, 0, newAllSha1Data,
                        allSha1Data.length, chunkDataSha1.length);
                allSha1Data = newAllSha1Data;
            }
            byte[] allSha1DataSha1 = sha1(allSha1Data);
            byte[] hashData = new byte[allSha1DataSha1.length + 1];
            System.arraycopy(allSha1DataSha1, 0, hashData, 1,
                    allSha1DataSha1.length);
            hashData[0] = (byte) 0x96;
            etag = urlSafeBase64Encode(hashData);
        }
        inputStream.close();
        return etag;
    }

    public  static String getNextId() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuffer str = new StringBuffer();
        str.append(f.format(new Date()));
        str.append(java.util.UUID.randomUUID().toString().replaceAll("-",""));
        return str.toString();
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}