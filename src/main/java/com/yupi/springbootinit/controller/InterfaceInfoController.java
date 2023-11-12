package com.yupi.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.springbootinit.annotation.AuthCheck;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.constant.UserConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.interfaceInfoController.interfaceInfoAddRequest;
import com.yupi.springbootinit.model.dto.interfaceInfoController.interfaceInfoQueryRequest;
import com.yupi.springbootinit.model.dto.interfaceInfoController.interfaceInfoUpdateRequest;
import com.yupi.springbootinit.model.entity.InterfaceInfo;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.InterfaceInfoService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/interface")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoControllerService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param interfaceInfoControllerAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addinterfaceInfo(@RequestBody interfaceInfoAddRequest interfaceInfoControllerAddRequest, HttpServletRequest request) {
        if (interfaceInfoControllerAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoController = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoControllerAddRequest, interfaceInfoController);
        //参数校验
        interfaceInfoControllerService.validInterfaceInfo(interfaceInfoController, true);
        //校验用户登录信息
        User loginUser = userService.getLoginUser(request);

        boolean result = interfaceInfoControllerService.save(interfaceInfoController);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newinterfaceInfoId = interfaceInfoController.getId();
        return ResultUtils.success(newinterfaceInfoId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteinterfaceInfo(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在-- mybatis的原装方法
        InterfaceInfo oldinterfaceInfo = interfaceInfoControllerService.getById(id);
        ThrowUtils.throwIf(oldinterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldinterfaceInfo.getUsername().equals(user.getUserAccount()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = interfaceInfoControllerService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param interfaceInfoControllerUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateinterfaceInfo(@RequestBody interfaceInfoUpdateRequest interfaceInfoControllerUpdateRequest) {
        if (interfaceInfoControllerUpdateRequest == null || interfaceInfoControllerUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoController = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoControllerUpdateRequest, interfaceInfoController);

        // 参数校验
        interfaceInfoControllerService.validInterfaceInfo(interfaceInfoController, true);
        long id = interfaceInfoControllerUpdateRequest.getId();
        // 判断是否存在
        InterfaceInfo oldinterfaceInfo = interfaceInfoControllerService.getById(id);
        ThrowUtils.throwIf(oldinterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = interfaceInfoControllerService.updateById(interfaceInfoController);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getinterfaceInfoVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoController = interfaceInfoControllerService.getById(id);
        return ResultUtils.success(interfaceInfoController);
    }


    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(interfaceInfoQueryRequest interfaceInfoQueryRequest) {
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        if (interfaceInfoQueryRequest != null) {
            BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoControllerService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }


    /**
     * 分页获取列表
     *
     * @param interfaceInfoQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(interfaceInfoQueryRequest interfaceInfoQueryRequest, HttpServletRequest request) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoQueryRequest, interfaceInfoQuery);
        long current = interfaceInfoQueryRequest.getCurrent();
        long size = interfaceInfoQueryRequest.getPageSize();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        String description = interfaceInfoQuery.getDescription();
        // description 需支持模糊搜索
        interfaceInfoQuery.setDescription(null);
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoControllerService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

//    /**
//     * 分页获取列表（封装类）
//     *
//     * @param interfaceInfoControllerQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/list/page")
//    public BaseResponse<Page<interfaceInfoVO>> listinterfaceInfoVOByPage(@RequestBody interfaceInfoQueryRequest interfaceInfoControllerQueryRequest,
//            HttpServletRequest request) {
//        long current = interfaceInfoControllerQueryRequest.getCurrent();
//        long size = interfaceInfoControllerQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//
//        return ResultUtils.success(interfaceInfoControllerService.getinterfaceInfoVOPage(interfaceInfoControllerPage, request));
//    }

//    /**
//     * 分页获取当前用户创建的资源列表
//     *
//     * @param interfaceInfoControllerQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/my/list/page/vo")
//    public BaseResponse<Page<interfaceInfoVO>> listMyinterfaceInfoVOByPage(@RequestBody interfaceInfoQueryRequest interfaceInfoControllerQueryRequest,
//            HttpServletRequest request) {
//        if (interfaceInfoControllerQueryRequest == null) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        User loginUser = userService.getLoginUser(request);
//        interfaceInfoControllerQueryRequest.setUserId(loginUser.getId());
//        long current = interfaceInfoControllerQueryRequest.getCurrent();
//        long size = interfaceInfoControllerQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<InterfaceInfo> interfaceInfoControllerPage = interfaceInfoControllerService.page(new Page<>(current, size),
//                interfaceInfoControllerService.getQueryWrapper(interfaceInfoControllerQueryRequest));
//        return ResultUtils.success(interfaceInfoControllerService.getinterfaceInfoVOPage(interfaceInfoControllerPage, request));
//    }

//    // endregion
//
//    /**
//     * 分页搜索（从 ES 查询，封装类）
//     *
//     * @param interfaceInfoControllerQueryRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/search/page/vo")
//    public BaseResponse<Page<interfaceInfoVO>> searchinterfaceInfoVOByPage(@RequestBody interfaceInfoQueryRequest interfaceInfoControllerQueryRequest,
//            HttpServletRequest request) {
//        long size = interfaceInfoControllerQueryRequest.getPageSize();
//        // 限制爬虫
//        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
//        Page<interfaceInfo> interfaceInfoControllerPage = interfaceInfoControllerService.searchFromEs(interfaceInfoControllerQueryRequest);
//        return ResultUtils.success(interfaceInfoControllerService.getinterfaceInfoVOPage(interfaceInfoControllerPage, request));
//    }
//
//    /**
//     * 编辑（用户）
//     *
//     * @param interfaceInfoControllerEditRequest
//     * @param request
//     * @return
//     */
//    @PostMapping("/edit")
//    public BaseResponse<Boolean> editinterfaceInfo(@RequestBody interfaceInfoEditRequest interfaceInfoControllerEditRequest, HttpServletRequest request) {
//        if (interfaceInfoControllerEditRequest == null || interfaceInfoControllerEditRequest.getId() <= 0) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        InterfaceInfo interfaceInfoController = new InterfaceInfo();
//        BeanUtils.copyProperties(interfaceInfoControllerEditRequest, interfaceInfoController);
//        List<String> tags = interfaceInfoControllerEditRequest.getTags();
//        if (tags != null) {
//            interfaceInfoController.setTags(GSON.toJson(tags));
//        }
//        // 参数校验
//        interfaceInfoControllerService.validinterfaceInfo(interfaceInfoController, false);
//        User loginUser = userService.getLoginUser(request);
//        long id = interfaceInfoControllerEditRequest.getId();
//        // 判断是否存在
//        InterfaceInfo oldinterfaceInfo = interfaceInfoControllerService.getById(id);
//        ThrowUtils.throwIf(oldinterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
//        // 仅本人或管理员可编辑
//        if (!oldinterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
//            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
//        }
//        boolean result = interfaceInfoControllerService.updateById(interfaceInfoController);
//        return ResultUtils.success(result);
//    }

}
