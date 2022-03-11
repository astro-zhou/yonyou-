package com.yonyou.iuap.corp.demo.web;

import com.yonyou.iuap.corp.demo.model.DcUrlResult;
import com.yonyou.iuap.corp.demo.utils.DcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 获取网关地址示例
 */
@RestController
public class GatewayController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private DcService dcService;

    @GetMapping("/getGatewayAddress")
    @ResponseBody
    public DcUrlResult getGatewayAddress(@RequestParam(name = "tenantId") String tenantId) throws IOException {
        LOGGER.info("tenantId: {}", tenantId);
        DcUrlResult dcUrl = dcService.getGateway(tenantId);
        return dcUrl;
    }

}
