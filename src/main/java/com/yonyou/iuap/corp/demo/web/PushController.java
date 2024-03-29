package com.yonyou.iuap.corp.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.iuap.corp.demo.constraint.EventType;
import com.yonyou.iuap.corp.demo.crypto.EncryptionHolder;
import com.yonyou.iuap.corp.demo.crypto.EventCrypto;
import com.yonyou.iuap.corp.demo.crypto.PrivateAppCrypto;
import com.yonyou.iuap.corp.demo.model.EventContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

/**
 * 处理用友开放平台推送的数据变更事件。
 * <ul>
 *     <li>处理成功后需回复 <code>success</code>，否则开放平台视为推送失败，会进行重试</li>
 *     <li>耗时操作推荐异步处理，平台默认推送超时时间为 5 秒，超时视为推送失败</li>
 *     <li>推送失败后开放平台会进行重试投递，重试策略为每分钟一次，每5分钟一次，每一小时一次，直到 24 小时，超过 24 小时仍未成功成功的不再尝试</li>
 * </ul>
 *
 */
@RestController
public class PushController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushController.class);

    @Value("${app.key}")
    private String appKey;

    @Value("${app.secret}")
    private String appSecret;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/eventPush")
    public String eventCallBackReceiver(@RequestBody EncryptionHolder holder) throws IOException {
        // 构建解密验签处理对象
        EventCrypto crypto = PrivateAppCrypto.newCrypto(appKey, appSecret);
        // 验签解密后的消息体
        String decryptMessage = crypto.decryptMsg(holder);
        // 反序列化后的消息内容对象
        EventContent content = mapper.readValue(decryptMessage, EventContent.class);

        LOGGER.info("新的事件推送，content: {}", content.toString());

        // 根据事件类型进行相关处理操作，耗时操作推荐异步处理，平台默认推送超时时间为 5 秒，超时视为推送失败
        switch (content.getType()) {
            case CHECK_URL:
                LOGGER.info("事件类型: {}, 说明: 检查事件推送回调地址", EventType.CHECK_URL);
                break;

            case STAFF_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工增加, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工更改, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_ENABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工启用, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_DISABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工停用, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;
            case STAFF_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下员工删除, 员工变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getStaffId()));
                break;

            case DEPT_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门创建, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门修改, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_ENABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门启用, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_DISABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门停用, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;
            case DEPT_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下部门删除, 部门变更 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getDeptId()));
                break;

            case USER_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户增加，用户 name: {}, 详细信息: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getUserId()), content.getContent());
                break;
            case USER_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户移除，用户 name: {}, 详细信息: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getUserId()), content.getContent());
                break;
            case USER_ENABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户启用，用户 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getUserId()));
                break;
            case USER_DISABLE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下用户停用，用户 id: {}", content.getType(), content.getTenantId(), Arrays.toString(content.getUserId()));
                break;

            case DELETE_DIWORKSESSION:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 注销，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case META_ALTER:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 元数据修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case JOBINFO_ADD:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下任职新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case JOBINFO_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下任职更新，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCT_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料档案新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCT_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料档案修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCT_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料档案删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_MANAGEMENTCLASS_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料分类新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_MANAGEMENTCLASS_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料分类修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_MANAGEMENTCLASS_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料分类删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_UNIT_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下计量单位新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_UNIT_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物计量单位修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_UNIT_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物计量单位删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCTSKUPRO_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物SKU属性新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCTSKUPRO_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物SKU属性修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCTSKUPRO_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物SKU属性删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCTPRO_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料属性新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCTPRO_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料属性修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_PRODUCTPRO_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料属性删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_AA_WAREHOUSE_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下仓库新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_AA_WAREHOUSE_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下仓库修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_AA_WAREHOUSE_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下仓库删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_AA_MERCHANT_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下客户档案新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_AA_MERCHANT_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下客户档案修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_AA_MERCHANT_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下客户档案删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_SPEPRO_INSERT:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料规格新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_SPEPRO_UPDATE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料规格修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_PC_SPEPRO_DELETE:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料规格删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_ADD_NOTIFY:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料新增，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_UPDATE_NOTIFY:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料修改，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case YXYBASEDOC_DELETE_NOTIFY:
                LOGGER.info("事件类型: {}, 说明: 租户 {} 下物料删除，信息: {}", content.getType(), content.getTenantId(), content.getContent());
                break;
            case UNKNOWN:
                LOGGER.info("未知事件");
                break;
        }

        // 处理成功，回复 "success" 告知开放平台，否则开放平台会重试推送，直到 24 小时
        return "success";
    }
}
