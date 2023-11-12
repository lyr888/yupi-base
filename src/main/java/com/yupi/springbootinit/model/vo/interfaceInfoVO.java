package com.yupi.springbootinit.model.vo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class interfaceInfoVO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableLogic
    private Integer isDeleted;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
