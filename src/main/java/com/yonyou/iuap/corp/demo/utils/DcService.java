package com.yonyou.iuap.corp.demo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yonyou.iuap.corp.demo.model.DcUrlResult;
import com.yonyou.iuap.corp.demo.model.GenericResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @author: wangdir
 * @Date: 2022/3/11 16:32
 * @Description: TODO
 */

@Component
public class DcService {

    @Value("${open-api.url}")
    private String openApiUrl;

    public DcUrlResult getGateway(String tenantId) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("tenantId", tenantId);
        String requestUrl = openApiUrl + "/open-auth/dataCenter/getGatewayAddress";
        GenericResponse<DcUrlResult> response = RequestTool.doGet(requestUrl, params, new TypeReference<GenericResponse<DcUrlResult>>() {});
        return response.getData();
    }
}
