package com.yonyou.iuap.corp.demo.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yonyou.iuap.corp.demo.model.GenericResponse;
import com.yonyou.iuap.corp.demo.utils.RequestTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取网关地址示例
 */
@RestController
public class GatewayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);

    @Value("${open-api.url}")
    private String openApiUrl;

    @GetMapping("/getGatewayAddress")
    public String getGatewayAddress(@RequestParam(name = "tenantId") String tenantId) throws IOException {
        LOGGER.info("tenantId: {}", tenantId);
        String gateway = getGateway(tenantId);
        return gateway;
    }

    public String getGateway(String tenantId) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("tenantId", tenantId);
        String requestUrl = openApiUrl + "/open-auth/dataCenter/getGatewayAddress";
        GenericResponse<String> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<String>>() {});
        return response.getData();
    }
}
