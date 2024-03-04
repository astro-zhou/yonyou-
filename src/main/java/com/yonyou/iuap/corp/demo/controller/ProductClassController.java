package com.yonyou.iuap.corp.demo.controller;

import com.yonyou.iuap.corp.demo.domain.ProductClass;
import com.yonyou.iuap.corp.demo.service.ProductClassService;
import com.yonyou.iuap.corp.demo.web.AppController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author: yuhang
 * @Date: 2024/3/1 14:33
 * @Version: 1.0
 **/
@RestController
public class ProductClassController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductClassController.class);

    @Value("${app.key}")
    private String appKey;

    @Value("${app.secret}")
    private String appSecret;

    @Resource
    private ProductClassService productClassService;

    @PostMapping("/insert")
    public ProductClass insertProductClass(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        ProductClass productClass1 = productClassService.insertProductClass(productClass);

        return productClass1;
    }
}
