package com.yonyou.iuap.corp.demo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yonyou.iuap.corp.demo.controller.ProductClassController;
import com.yonyou.iuap.corp.demo.domain.ProductClass;
import com.yonyou.iuap.corp.demo.service.ProductClassService;
import com.yonyou.iuap.corp.demo.utils.OkClientUtils;
import com.yonyou.iuap.corp.demo.web.AppController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

//    @Override
//    public ProductClass insertProductClass(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//
//        String sendjson = "{\n" +
//                "    \"pageIndex\": 1,\n" +
//                "    \"pageSize\": 500\n" +
//                "}";
//        // 拼接路径为YS中请求地址-物料分类详情查询
//        String PRODUCT_CLASS_INSERT = "/yonbip/digitalModel/managementclass/newinsert?";
//        String requestUrl = openApiUrl + PRODUCT_CLASS_INSERT + "access_token=" + appController.getAccessToken();
//
//        // 发送一个POST请求,该请求向requestUrl发送JSON数据'sendjson',返回响应结果product
//        String productLists = OkClientUtils.httpPost(requestUrl, sendjson);
//        // 解析,从中获取data数组,并遍历
//        JSONArray data = JSONObject.parseObject(productLists).getJSONArray("data");
//        for (Object codeOrgIds : data) {
//            // 对每个元素进行解析,获取其中2个字段,并将这些数据存入一个Map中
//            JSONObject jsonObjects = JSONObject.parseObject(String.valueOf(codeOrgIds));
//            String code = jsonObjects.getString("code");
//            String simplifiedName = jsonObjects.getString("simplifiedName");
//            Map<String, String> sendMap = new HashMap<>();
//            sendMap.put("code", code);
//            sendMap.put("simplifiedName", simplifiedName);
//
//            // 构建一个URL，将token和之前的数据拼接-物料分类新增
////            String PRODUCT_CLASS_INSERT = "/yonbip/digitalModel/managementclass/newinsert?";
////            String requestUrl1 = openApiUrl + PRODUCT_CLASS_INSERT + "access_token=" + appController.getAccessToken();
//            // 通过POST请求发送给该URL
//            String detail = OkClientUtils.httpPost(requestUrl, JSON.toJSONString(sendMap));
//            String data1 = JSONObject.parseObject(detail).getString("data");
//
//        }
//        return productClass;
//    }
//}

    @Override
    public ProductClass insertProductClass(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        // 构建要发送的JSON数据
        JSONObject sendJson = new JSONObject();
        sendJson.put("code", productClass.getCode());
        sendJson.put("simplifiedName", productClass.getSimplifiedName());

        // 拼接路径为YS中请求地址-物料分类新增
        String PRODUCT_CLASS_INSERT = "/yonbip/digitalModel/managementclass/newinsert?";
        String requestUrl = openApiUrl + PRODUCT_CLASS_INSERT + "access_token=" + appController.getAccessToken();
        // 发送一个POST请求,向requestUrl发送JSON数据sendJson
        String response = OkClientUtils.httpPost(requestUrl, sendJson.toJSONString());
        // 解析返回的数据
        String data = JSONObject.parseObject(response).getString("data");


        return productClass;
    }
}
