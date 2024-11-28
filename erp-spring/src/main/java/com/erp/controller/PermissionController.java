package com.erp.controller;

import java.util.PrimitiveIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import com.erp.utils.JsonResponse;

import com.erp.entity.User;
import com.erp.entity.Permission;
import com.erp.entity.Model;

import com.erp.service.UserService;
import com.erp.service.PermissionService;
import com.erp.service.ModelService;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelService modelService;

    @GetMapping("/{userId}/permission")
    public JsonResponse<List<Permission>> getUserPermission(@PathVariable("userId") Integer userId) {
        JsonResponse<List<Permission>> response = new JsonResponse<List<Permission>>(false, "获取失败", null);
        try {
            List<Permission> permission = permissionService.getPermissionByUserId(userId);
            response.success = true;
            response.message = "获取权限成功";
            response.data = permission;
        } catch (Exception e) {
            response.success = false;
            response.message = e.getMessage();
        }
        return response;
    }

    @RequestMapping("/updpermission")
    public JsonResponse<Integer> updPermission(@RequestBody addPermissionRequest request) {
        JsonResponse<Integer> response = new JsonResponse<Integer>(false, "添加失败", null);
        try {
            User user = userService.getUserByName(request.userName);
            if (user == null) {
                response.message = "用户不存在";
                return response;
            }
            Model model = modelService.getModelByName(request.modelName);
            if (model == null) {
                response.message = "模块不存在";
                return response;
            }
            Permission permission = permissionService.getPermissionByUserIdAndModelId(user.getId(), model.getId());
            if (permission == null) {
                permission = permissionService.addPermission(user.getId(), model.getId(), request.permissionLevel);
            } else {
                permissionService.updPermission(permission.getId(), request.permissionLevel);
            }
            response.success = true;
            response.message = "更新权限成功";
            response.data = permission.getId();
        } catch (Exception e) {
            response.success = false;
            response.message = e.getMessage();
        }
        return response;
    }

    public class addPermissionRequest {
        String userName;
        String modelName;
        Integer permissionLevel;

    }

    public JsonResponse<Integer> initPermission(Integer userId , Integer permissionLevel) {
        JsonResponse<Integer> response = new JsonResponse<Integer>(false, "添加失败", null);
        try {
            User user = userService.getUserByName(request.userName);
            if (user == null) {
                response.message = "用户不存在";
                return response;
            }
            Model model = modelService.getModelByName(request.modelName);
            if (model == null) {
                response.message = "模块不存在";
                return response;
            }
            Permission permission = permissionService.getPermissionByUserIdAndModelId(user.getId(), model.getId());
            if (permission == null) {
                permission = permissionService.addPermission(user.getId(), model.getId(), request.permissionLevel);
            } else {
                permissionService.updPermission(permission.getId(), request.permissionLevel);
            }
            response.success = true;
            response.message = "更新权限成功";
            response.data = permission.getId();
        } catch (Exception e) {
            response.success = false;
            response.message = e.getMessage();
        }
        return response;
    }

}
