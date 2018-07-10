package com.github.chronic.action.permission;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Menu;
import com.github.chronic.service.menu.MenuService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName MenuManagerAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/18 11:19
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class MenuManagerAction extends CommonAction implements ModelDriven<Menu> {

    private Menu menu;

    @Autowired
    private MenuService menuService;


    /**
     * @return
     * @Description 删除
     * @Param
     **/
    public String menu_del() {
        try {
            menuService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return
     * @Description 更新
     * @Param
     **/
    public String menu_update() {
        flag = menuService.updateMenu(menu);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    /**
     * @return
     * @Description 添加
     * @Param
     **/
    public String menu_add() {
        if (forwardType.equals("menuList")) {
            jsonBean = menuService.findMenusByPid();
            if (jsonBean.getData() != null) {
                callbackCode();
            }
        }
        if (forwardType.equals("add")) {
            flag = menuService.saveMenu(menu);
            if (flag) {
                callbackTwo();
            } else {
                callbackMsg();
            }
        }
        return SUCCESS;
    }


    /**
     * @return java.lang.String
     * @Description 全部菜单信息
     * @Param []
     **/
    public String menu_list() {
        List<Menu> menuTree = menuService.findAllMenu();
        jsonBean.setData(menuTree);
        return SUCCESS;
    }

    /**
     * 页面跳转
     */
    public String menu_forward() {
        String urlPath = null;
        switch (forwardType) {
            case "menu_tab":
                urlPath = "menu_tab";
                break;
            case "menu_add":
                urlPath = "menu_add";
                break;
            case "menu_update":
                urlPath = "menu_update";
                break;
        }
        return urlPath;
    }

    @Override
    public Menu getModel() {
        menu = new Menu();
        return menu;
    }

}
