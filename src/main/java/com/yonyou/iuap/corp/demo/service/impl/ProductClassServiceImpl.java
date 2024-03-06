package com.yonyou.iuap.corp.demo.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yonyou.iuap.corp.demo.controller.ProductClassController;
import com.yonyou.iuap.corp.demo.domain.ProductClass;
import com.yonyou.iuap.corp.demo.model.AccessTokenResponse;
import com.yonyou.iuap.corp.demo.service.ProductClassService;
import com.yonyou.iuap.corp.demo.utils.OkClientUtils;
import com.yonyou.iuap.corp.demo.web.AppController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yuhang
 * @Date: 2024/3/1 14:34
 * @Version: 1.0
 **/
@Service
public class ProductClassServiceImpl implements ProductClassService {

    @Autowired
    private AppController appController;

    @Autowired
    private ProductClassController productClassController;

    @Value("${app.openApiUrl}")
    private String openApiUrl;

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductClassController.class);

    /**
     * 新增物料分类
     *
     * @param productClass
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Override
    public ProductClass insertProductClass(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        // 构建要发送的JSON数据
        JSONObject sendJson = new JSONObject();
        sendJson.put("code", productClass.getCode());

        HashMap<Object, Object> nameMap = new HashMap<>();
        nameMap.put("simplifiedName", productClass.getSimplifiedName());

        sendJson.put("name", nameMap);

        // 获取token
        AccessTokenResponse accessTokenResponse = appController.getAccessToken();
        String accessToken = accessTokenResponse.getAccessToken();
        // 拼接路径为YS中请求地址-物料分类新增
        String PRODUCT_CLASS_INSERT = "/yonbip/digitalModel/managementclass/newinsert?";
        String requestUrl = openApiUrl + PRODUCT_CLASS_INSERT + "access_token=" + accessToken;
        // 发送一个POST请求,向requestUrl发送JSON数据sendJson
        String response = OkClientUtils.httpPost(requestUrl, sendJson.toJSONString());
        // 解析返回的数据
        String data = JSONObject.parseObject(response).getString("data");

        return productClass;
    }

    /**
     * 删除物料分类-根据code
     *
     * @param params
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Override
    public String deleteProductClass(String params) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        // data体
        JSONObject sendJson = new JSONObject();
        HashMap<Object, Object> dataMap = new HashMap<>();
        sendJson.put("data", dataMap);
        // data的子对象code
        String code = JSONObject.parseObject(params).getJSONObject("data").getString("code");
        dataMap.put("code", code);
        // 获取token
        AccessTokenResponse accessTokenResponse = appController.getAccessToken();
        String accessToken = accessTokenResponse.getAccessToken();
        // 拼接路径为YS中请求地址-物料分类删除
        String PRODUCT_CLASS_DELETE = "/yonbip/digitalModel/productclass/delete?";
        String requestUrl = openApiUrl + PRODUCT_CLASS_DELETE + "access_token=" + accessToken;
        // 发送一个POST请求,向requestUrl发送JSON数据sendJson
        String response = OkClientUtils.httpPost(requestUrl, sendJson.toJSONString());
        // 解析返回的数据
        String data = JSONObject.parseObject(response).getString("data");

        return data;
    }

    /**
     * 查询物料分类树
     * @param productClass
     * @return
     */
    @Override
    public String selectProductClassTree(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // 构建要发送的JSON数据
        JSONObject sendJson = new JSONObject();
        sendJson.put("pageIndex", "1");
        sendJson.put("pageSize", "100");
        // 获取token
        AccessTokenResponse accessTokenResponse = appController.getAccessToken();
        String accessToken = accessTokenResponse.getAccessToken();
        // 拼接路径为YS中请求地址-物料分类新增
        String PRODUCT_CLASS_TREE = "/yonbip/digitalModel/managementclass/newtree?";
        String requestUrl = openApiUrl + PRODUCT_CLASS_TREE + "access_token=" + accessToken;
        // 发送一个POST请求,向requestUrl发送JSON数据sendJson
        LOGGER.info(sendJson.toJSONString());
        String response = OkClientUtils.httpPost(requestUrl, sendJson.toJSONString());
        // 解析返回的数据
        JSONArray dataArray = JSONObject.parseObject(response).getJSONArray("data");
        // 取data体中的每个数组包含的id,放进列表中
        List<Object> ids = new ArrayList<>();
        for (Object idsArray : dataArray) {
            JSONObject JOIdsArray = JSONObject.parseObject(String.valueOf(idsArray));
            String id = JOIdsArray.getString("id");
            String name = JOIdsArray.getString("name");
            Map<Object, Object> idMap = new HashMap<>();
            idMap.put("id", id);
            idMap.put("name", name);
            ids.add(idMap);
        }
        return ids.toString();
    }




}
