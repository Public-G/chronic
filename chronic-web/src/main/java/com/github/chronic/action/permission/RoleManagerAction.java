package com.github.chronic.action.permission;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Menu;
import com.github.chronic.pojo.Role;
import com.github.chronic.service.menu.MenuService;
import com.github.chronic.service.permission.RoleManagerService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName RoleManagerAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 21:41
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class RoleManagerAction extends CommonAction implements ModelDriven<Role>{

    @Autowired
    private RoleManagerService roleManagerService;

    @Autowired
    private MenuService menuService;

    private Role role;


    /**
    * @Description 分配菜单
    * @Param 
    * @return 
    **/
    public String role_grantMenu() {
        try {
            menuService.updateRoleMenu(checkids, role.getRole_id());
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
    * @Description 查询角色所拥有的菜单
    * @Param 
    * @return 
    **/
    public String role_haveMenu() {
        List<Menu> roleHaveMenu = menuService.findMenusByRoleid(role.getRole_id());
        jsonBean.setData(roleHaveMenu);
        return SUCCESS;
    }

    /**
    * @Description 初始化菜单树
    * @Param
    * @return
    **/
    public String role_menu() {
        List<Menu> menuTree = menuService.findAllMenu();
        jsonBean.setData(menuTree);
        return SUCCESS;
    }

    /**
    * @Description 更新
    * @Param []
    * @return java.lang.String
    **/
    public String role_update() {
        flag = roleManagerService.updateRole(role);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
    * @Description 删除
    * @Param []
    * @return java.lang.String
    **/
    public String role_del() {
        if (!checkids.trim().equals("")) {
            roleManagerService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
    * @Description 校验字段唯一性
    * @Param []
    * @return java.lang.String
    **/
    public String role_fieldVerify() {
        flag = roleManagerService.fieldVerify(role);
        if (flag) {
            callbackCode();
        }
        return SUCCESS;
    }


    /**
    * @Description 添加
    * @Param []
    * @return java.lang.String
    **/
    public String role_add() {
        flag = roleManagerService.saveRole(role);
        if (flag) {
            callbackTwo();
        } else{
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
    * @Description 角色信息
    * @Param []
    * @return java.lang.String
    **/
    public String role_list() {
        if (curr != null) {
            jsonBean = roleManagerService.findRoles(role.getRole_name(), curr);
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }


    /**
     * 页面跳转
     */
    public String role_forward() {
        String urlPath = null;
        switch (forwardType) {
            case "role_tab":
                urlPath = "role_tab";
                break;
            case "role_add":
                urlPath = "role_add";
                break;
            case "role_update":
                urlPath = "role_update";
                break;
            case "role_menu":
                urlPath = "role_menu";
                break;
        }
        return urlPath;
    }


    @Override
    public Role getModel() {
        role = new Role();
        return role;
    }
}
