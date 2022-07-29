package com.yonyou.iuap.corp.demo.crypto;

/**
 * 加密后的消息载体
 */
public class EncryptionHolder {

    /**
     * 消息签名
     */
    private String signature;

    /**
     * 消息发送 unix 时间戳
     */
    private long timestamp;

    /**
     * 随机值，盐
     */
    private String nonce;

    /**
     * AES -> BASE64 之后的消息体
     */
    private String encrypt;

    public EncryptionHolder() {
    }

    public EncryptionHolder(String signature, long timestamp, String nonce, String encrypt) {
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.encrypt = encrypt;
    }

    public EncryptionHolder(long timestamp, String nonce) {
        this.timestamp = timestamp;
        this.nonce = nonce;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

}
