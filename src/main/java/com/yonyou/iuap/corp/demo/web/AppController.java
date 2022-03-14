package com.yonyou.iuap.corp.demo.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.iuap.corp.demo.crypto.JWTHelper;
import com.yonyou.iuap.corp.demo.crypto.SignHelper;
import com.yonyou.iuap.corp.demo.model.*;
import com.yonyou.iuap.corp.demo.utils.DcService;
import com.yonyou.iuap.corp.demo.utils.RequestTool;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Value("${app.key}")
    private String appKey;

    @Value("${app.secret}")
    private String appSecret;

    @Value("${open-api.url}")
    private String openApiUrl;

    @Value("${app.tenantId:tenantId}")
    private String tenantId;

    @Autowired
    private DcService dcService;

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 多数据中心适配之前通过appKey获取accessToken
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/getAccessToken")
    public AccessTokenResponse getAccessToken() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, String> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("appKey", appKey);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // 计算签名
        String signature = SignHelper.sign(params, appSecret);
        params.put("signature", signature);

        // 请求
        String requestUrl = openApiUrl + "/open-auth/selfAppAuth/getAccessToken";
        GenericResponse<AccessTokenResponse> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<AccessTokenResponse>>() {});

        if (response.isSuccess()) {
            return response.getData();
        }

        LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
        throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
    }

    /**
     * 多数据中心适配之后通过tenantId和appKey获取accessToken
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/getAccessTokenV2")
    public AccessTokenResponse getAccessTokenV2() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        String tokenUrl;
        try {
            DcUrlResult dcUrl = dcService.getGateway(tenantId);
            tokenUrl = dcUrl.getTokenUrl();
        } catch (Exception e) {
            LOGGER.error("获取多数据中心获取token地址出现异常", e);
            throw new RuntimeException("获取多数据中心获取token地址出现异常", e);
        }
        Map<String, String> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("appKey", appKey);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // 计算签名
        String signature = SignHelper.sign(params, appSecret);
        params.put("signature", signature);

        // 请求
        String requestUrl = tokenUrl + "/open-auth/selfAppAuth/getAccessToken";
        GenericResponse<AccessTokenResponse> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<AccessTokenResponse>>() {});

        if (response.isSuccess()) {
            return response.getData();
        }

        LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
        throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
    }


    /**
     * 通过appkey获取租户id
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    @GetMapping("/getTenantId")
    public String getTenantId() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        Map<String, String> params = new HashMap<>();
        // 除签名外的其他参数
        params.put("appKey", appKey);
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // 计算签名
        String signature = SignHelper.sign(params, appSecret);
        params.put("signature", signature);

        // 请求
        String requestUrl = openApiUrl + "/open-auth/selfAppAuth/getTenantId";
        GenericResponse<String> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<String>>() {});

        if (response.isSuccess()) {
            return response.getData();
        }

        LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
        throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
    }

    /**
     * 通过accessToken和友空间免登code获取userToken
     * @param accessToken 开放平台accessToken
     * @param code 友空间免登code
     * @return
     */
    @GetMapping("/getUserToken")
    public UserTokenResponse getUserToken(@RequestParam("access_token") String accessToken, @RequestParam String code) throws Exception{
        Assert.notNull(accessToken, "accessToken must not be null.");
        Assert.notNull(code, "code must not be null.");
        String requestUrl = openApiUrl + "/open-auth/selfAppAuth/getUserToken";
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("code", code);
        GenericResponse<Object> response = RequestTool.doGet(requestUrl, paramMap, GenericResponse.class);
        if(response.isSuccess()){
            UserTokenResponse userTokenResponse = mapper.readValue(mapper.writeValueAsString(response.getData()), UserTokenResponse.class);
            //从返回中获取userToken
            String userToken = userTokenResponse.getUserAccessToken();
            //从返回中获取refreshToken
            String refreshToken = userTokenResponse.getRefreshToken();
            //从返回中获取idToken
            String idToken = userTokenResponse.getIdToken();
            //解析jwtToken
            Claims jwtClaims = JWTHelper.parseJwtToken(idToken, appSecret);
            //获取idToken中携带的用户信息
            String tenantId = jwtClaims.get("tenantId", String.class);
            String yhtUserId = jwtClaims.get("yhtUserId", String.class);
            String tenantName = jwtClaims.get("tenantName", String.class);
            String userName = jwtClaims.get("userName", String.class);
            String mobile = jwtClaims.get("mobile", String.class);
            String email = jwtClaims.get("email", String.class);
            String locale = jwtClaims.get("locale", String.class);
            return userTokenResponse;
        } else {
            LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
            throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
        }
    }

    /**
     * 使用refreshToken刷新userToken
     * @param refreshToken
     * @return 新的userToken
     * @throws Exception
     */
    @GetMapping("/refreshUserToken")
    public RefreshTokenResponse refreshUserToken(@RequestParam String refreshToken) throws Exception{
        Assert.notNull(refreshToken, "refreshToken must not be null.");
        String requestUrl = openApiUrl + "/open-auth/userToken/refresh";
        Map<String, String> paramMap = new TreeMap<>();
        paramMap.put("appKey", appKey);
        paramMap.put("refreshToken", refreshToken);
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        //计算签名
        String signature = SignHelper.sign(paramMap, appSecret);
        paramMap.put("signature", signature);
        GenericResponse<RefreshTokenResponse> response = RequestTool.doGet(requestUrl, paramMap, GenericResponse.class);
        if(response.isSuccess()){
            RefreshTokenResponse refTokenResponse = mapper.readValue(mapper.writeValueAsString(response.getData()), RefreshTokenResponse.class);
            return refTokenResponse;
        } else {
            LOGGER.error("请求开放平台接口失败，code: {}, message: {}", response.getCode(), response.getMessage());
            throw new RuntimeException("请求开放平台接口失败, code: " + response.getCode() + ", message: " + response.getMessage());
        }
    }

}
