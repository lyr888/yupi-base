package com.yupi.springbootinit.model.dto.interfaceInfoController;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 查询接口
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class interfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 主键
     * 用户根据id查询
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;


    /**
     * url地址
     */
    private String url;

    /**
     * 调用方法类型
     */
    private String method;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 描述
     */
    private String description;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 请求状态码
     */
    private Integer status;

    /**
     * 请求Id
     */
    private Integer userId;

}
