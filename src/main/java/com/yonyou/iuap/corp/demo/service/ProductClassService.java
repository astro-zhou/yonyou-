package com.yonyou.iuap.corp.demo.service;

import com.yonyou.iuap.corp.demo.domain.ProductClass;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:
 * @Author: yuhang
 * @Date: 2024/3/1 14:34
 * @Version: 1.0
 **/
public interface ProductClassService {

    ProductClass insertProductClass(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
    String deleteProductClass(String productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
    String selectProductClassTree(ProductClass productClass) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
}
