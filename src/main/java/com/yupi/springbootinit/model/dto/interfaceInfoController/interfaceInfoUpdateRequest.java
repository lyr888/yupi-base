package com.yupi.springbootinit.model.dto.interfaceInfoController;


import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;


/**
 * 接口更新
 */
@Data
public class interfaceInfoUpdateRequest {

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
}
