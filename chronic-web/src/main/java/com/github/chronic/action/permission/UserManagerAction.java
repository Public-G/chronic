package com.github.chronic.action.permission;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.User;
import com.github.chronic.service.permission.LocationService;
import com.github.chronic.service.permission.UserManagerService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName UserManagerAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 0:38
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class UserManagerAction extends CommonAction implements ModelDriven<User> {

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private LocationService locationService;

    private User user;

    private Integer role_id;

    private Integer locid;

    private List<Location> locationList;

    /**
    * @Description 分配经办点
    * @Param []
    * @return java.lang.String
    **/
    public String user_grantLocation() {
        flag = userManagerService.updateUserLocation(user.getUser_id(), locid);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return
     * @Description 取消分配
     * @Param
     **/
    public String user_revokeRole() {
        flag = userManagerService.updateUserRole("remove", user.getUser_id(), role_id);
        if (flag) {
            callbackTwo();
        } else {
            jsonBean.setMsg("取消失败，角色已取消");
        }
        return SUCCESS;
    }

    /**
     * @return
     * @Description 分配角色
     * @Param
     **/
    public String user_grantRole() {
        if (role_id == null) {
            jsonBean.setMsg("请选择角色");
        } else {
            flag = userManagerService.updateUserRole("add", user.getUser_id(), role_id);
            if (flag) {
                callbackTwo();
            } else {
                jsonBean.setMsg("分配失败，角色已分配");
            }
        }
        return SUCCESS;
    }

    /**
     * @return
     * @Description 分配角色信息显示
     * @Param
     **/
    public String user_role() {
        jsonBean = userManagerService.findUserRole(user.getUser_id(), curr);
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    /**
     * @return
     * @Description 更新
     * @Param
     **/
    public String user_update() {
        flag = userManagerService.updateUser(user);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return java.lang.String
     * @Description 重置密码
     * @Param []
     **/
    public String user_resetPwd() {
        flag = userManagerService.updateResetPwd(user.getUser_id());
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return java.lang.String
     * @Description 删除
     * @Param []
     **/
    public String user_del() {
        if (!checkids.trim().equals("")) {
            userManagerService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return java.lang.String
     * @Description 添加
     * @Param []
     **/
    public String user_add() {
        flag = userManagerService.saveUser(user);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return java.lang.String
     * @Description 校验字段唯一性
     * @Param []
     **/
    public String user_fieldVerify() {
        flag = userManagerService.fieldVerify(user);
        if (flag) {
            callbackCode();
        }

        return SUCCESS;
    }

    /**
     * 用户信息
     */
    public String user_list() {
        if (curr != null) {
            jsonBean = userManagerService.findUsers(user.getUser_name(), curr);
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    /**
     * 页面跳转
     */
    public String user_forward() {
        String urlPath = null;
        switch (forwardType) {
            case "user_tab":
                urlPath = "user_tab";
                break;
            case "user_add":
                urlPath = "user_add";
                break;
            case "user_update":
                urlPath = "user_update";
                break;
            case "user_role":
                urlPath = "user_role";
                break;
            case "user_location":
                locationList = locationService.findAllLocation();
                urlPath = "user_location";
                break;
        }
        return urlPath;
    }


    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public void setLocid(Integer locid) {
        this.locid = locid;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    @Override
    public User getModel() {
        user = new User();
        return user;
    }


}
