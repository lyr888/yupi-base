package com.yupi.springbootinit.model.dto.interfaceInfoController;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新增接口信息
 */
@Data
public class interfaceInfoAddRequest implements Serializable {

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
     * 用户名
     */
    private String username;




}
