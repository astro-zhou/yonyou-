package com.yonyou.iuap.corp.demo.constraint;

/**
 * 推送事件定义
 */
public enum EventType {

    /**
     * 检查回调地址有效性
     */
    CHECK_URL,

    /**
     * 员工增加
     */
    STAFF_ADD,

    /**
     * 员工更改
     */
    STAFF_UPDATE,

    /**
     * 员工启用
     */
    STAFF_ENABLE,

    /**
     * 员工停用
     */
    STAFF_DISABLE,

    /**
     * 员工删除
     */
    STAFF_DELETE,


    /**
     * 部门创建
     */
    DEPT_ADD,

    /**
     * 部门修改
     */
    DEPT_UPDATE,

    /**
     * 部门启用
     */
    DEPT_ENABLE,

    /**
     * 部门停用
     */
    DEPT_DISABLE,

    /**
     * 部门删除
     */
    DEPT_DELETE,


    /**
     * 用户增加
     */
    USER_ADD,

    /**
     * 用户删除
     */
    USER_DELETE,
    /**
     * 用户启用
     */
    USER_ENABLE,
    /**
     * 用户停用
     */
    USER_DISABLE,
    /**
     * 用户注销事件
     */
    DELETE_DIWORKSESSION,

    /**
     * 元数据修改
     */
    META_ALTER,

    /**
     * UNKNOWN
     **/
    UNKNOWN,

    /**
     * 任职增加
     */
    JOBINFO_ADD,

    /**
     * 任职更新
     */
    JOBINFO_UPDATE,

    /**
     * 物料档案新增
     */
    YXYBASEDOC_PC_PRODUCT_INSERT,

    /**
     * 物料档案修改
     */
    YXYBASEDOC_PC_PRODUCT_UPDATE,

    /**
     * 物料档案删除
     */
    YXYBASEDOC_PC_PRODUCT_DELETE,

    /**
     * 物料分类新增
     */
    YXYBASEDOC_PC_MANAGEMENTCLASS_INSERT,

    /**
     * 物料分类修改
     */
    YXYBASEDOC_PC_MANAGEMENTCLASS_UPDATE,

    /**
     * 物料分类删除
     */
    YXYBASEDOC_PC_MANAGEMENTCLASS_DELETE,

    /**
     * 计量单位新增
     */
    YXYBASEDOC_PC_UNIT_INSERT,

    /**
     * 计量单位修改
     */
    YXYBASEDOC_PC_UNIT_UPDATE,

    /**
     * 计量单位删除
     */
    YXYBASEDOC_PC_UNIT_DELETE,

    /**
     * SKU属性新增
     */
    YXYBASEDOC_PC_PRODUCTSKUPRO_INSERT,

    /**
     * SKU属性修改
     */
    YXYBASEDOC_PC_PRODUCTSKUPRO_UPDATE,

    /**
     * SKU属性删除
     */
    YXYBASEDOC_PC_PRODUCTSKUPRO_DELETE,

    /**
     * 物料属性新增
     */
    YXYBASEDOC_PC_PRODUCTPRO_INSERT,

    /**
     * 物料属性修改
     */
    YXYBASEDOC_PC_PRODUCTPRO_UPDATE,

    /**
     * 物料属性删除
     */
    YXYBASEDOC_PC_PRODUCTPRO_DELETE,

    /**
     * 仓库新增
     */
    YXYBASEDOC_AA_WAREHOUSE_INSERT,

    /**
     * 仓库修改
     */
    YXYBASEDOC_AA_WAREHOUSE_UPDATE,

    /**
     * 仓库删除
     */
    YXYBASEDOC_AA_WAREHOUSE_DELETE,

    /**
     * 客户档案新增
     */
    YXYBASEDOC_AA_MERCHANT_INSERT,

    /**
     * 客户档案修改
     */
    YXYBASEDOC_AA_MERCHANT_UPDATE,

    /**
     * 客户档案删除
     */
    YXYBASEDOC_AA_MERCHANT_DELETE,

    /**
     * 物料规格新增
     */
    YXYBASEDOC_PC_SPEPRO_INSERT,

    /**
     * 物料规格修改
     */
    YXYBASEDOC_PC_SPEPRO_UPDATE,

    /**
     * 物料规格删除
     */
    YXYBASEDOC_PC_SPEPRO_DELETE,

    /**
     * 物料新增-old
     */
    YXYBASEDOC_ADD_NOTIFY,

    /**
     * 物料修改-old
     */
    YXYBASEDOC_UPDATE_NOTIFY,

    /**
     * 物料删除-old
     */
    YXYBASEDOC_DELETE_NOTIFY;

}
