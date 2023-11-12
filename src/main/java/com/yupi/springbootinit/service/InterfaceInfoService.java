package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.springbootinit.model.entity.InterfaceInfo;

/**
* @author Admin
* @description 针对表【interface_info(接口信息表)】的数据库操作Service
* @createDate 2023-11-12 11:19:42
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    public void validInterfaceInfo(InterfaceInfo post, boolean add);

}
