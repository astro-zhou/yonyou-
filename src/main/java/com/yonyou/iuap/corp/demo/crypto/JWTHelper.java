package com.yonyou.iuap.corp.demo.crypto;

import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

public class JWTHelper {

    /**
     * 生成JWT
     * @param iss
     * @param sub
     * @param aud
     * @param secret
     * @param ttlMillis
     */
    public static String createJwtToken(String iss, String sub, String aud, String secret, long ttlMillis, Map<String, Object> claims) throws Exception{
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setIssuer(iss).setSubject(sub)
                .setAudience(aud).setIssuedAt(now).signWith(signatureAlgorithm, generalKey(secret));
        if(null != claims && claims.size() > 0){
            builder.setClaims(claims);
        }
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 设置过期时间
        }
        return builder.compact();
    }

    /**
     *
     * 解析JWT
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJwtToken(String jwt, String secret) throws Exception{
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("The secret of JWS is null.");
        }
        SecretKey secretKey;
        try{
            secretKey = generalKey(secret);
        } catch (Exception e){
            throw new IllegalArgumentException("The secret of JWS is illegal.");
        }
        Claims claims;
        try{
            claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
        } catch (MalformedJwtException e){
            throw new IllegalArgumentException("The JWS is illegal.");
        } catch (ExpiredJwtException e){
            throw new IllegalArgumentException("The JWS is expired.");
        } catch (SignatureException e){
            throw new IllegalArgumentException("JWS signature validation fails.");
        } catch (UnsupportedJwtException e){
            throw new IllegalArgumentException("The claimsJws argument does not represent an Claims JWS.");
        } catch (Exception e){
            throw new Exception("Validate JWS failed," + e.getMessage());
        }
        return claims;
    }

    private static SecretKey generalKey(String secret){
        byte[] encodedKey = secret.getBytes();
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static String createBase64(String source) throws Exception{
        if(StringUtils.isBlank(source)){
            return "";
        }
        return Base64.encodeBase64String(source.getBytes("utf-8"));
    }

}
