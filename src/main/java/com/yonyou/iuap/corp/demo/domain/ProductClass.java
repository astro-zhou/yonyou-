package com.yonyou.iuap.corp.demo.domain;

import org.springframework.context.annotation.Bean;

/**
 * @Description:
 * @Author: yuhang
 * @Date: 2024/3/1 14:28
 * @Version: 1.0
 **/

public class ProductClass {

    private String code;
    private String simplifiedName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSimplifiedName() {
        return simplifiedName;
    }

    public void setSimplifiedName(String simplifiedName) {
        this.simplifiedName = simplifiedName;
    }

    public ProductClass() {
    }

    public ProductClass(String code, String simplifiedName) {
        this.code = code;
        this.simplifiedName = simplifiedName;
    }

    @Override
    public String toString() {
        return "ProductClass{" +
                "code='" + code + '\'' +
                ", simplifiedName='" + simplifiedName + '\'' +
                '}';
    }
}
