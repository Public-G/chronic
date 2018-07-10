package com.github.chronic.service.impl.menu;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.menu.MenuDao;
import com.github.chronic.pojo.Menu;
import com.github.chronic.pojo.Role;
import com.github.chronic.pojo.User;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.menu.MenuService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName MenuServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/16 23:34
 * @Version 1.0
 **/
@Service
public class MenuServiceImpl extends CommonService implements MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * @return java.util.List<com.github.chronic.pojo.Menu>
     * @Description 查询全部菜单
     * @Param []
     **/
    public List<Menu> findAllMenu() {
        return menuDao.findAllMenu();
    }

    @Override
    public List<Menu> findMenuByUser(Object object) {
        //获取用户所对应的菜单
        Set<Role> userRole = null;
        Set<Menu> roleMenu = null;
        TreeSet<Menu> treeSetMenu = new TreeSet<>();
        try {
            userRole = ((User) object).getRoles();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        for (Role r : userRole) {
            roleMenu = r.getMenus();
            treeSetMenu.addAll(roleMenu);
        }

        // 菜单整理
        // 保存父菜单
        List<Menu> menus = new ArrayList<>();

        // 保存所有菜单，便于根据父id查询到父菜单
        Map<Integer, Menu> map = new HashMap<>();

        // 将所有菜单放入map，menu_id作为key
        for (Menu menu : treeSetMenu) {
            map.put(menu.getMenu_id(), menu);

        }

        for (Menu menu : treeSetMenu) {
            if (menu.getPid() == 0) {
                // 保存父菜单
                menus.add(menu);
            } else {
                // 根据parent_id得到父菜单id
                Integer pid = menu.getPid();

                // 根据父菜单id得到父菜单
                Menu p_menu = map.get(pid);

                // 得到当前父菜单的子菜单，第一次为空
                List<Menu> childs = p_menu.getChilds();
                if (childs != null) {
                    // 当前有子菜单
                    childs.add(menu);
                } else {
                    // 当前没有子菜单
                    childs = new ArrayList<>();
                    // 添加当前子菜单
                    childs.add(menu);
                    // 将当前整理好的子菜单设置进去
                    p_menu.setChilds(childs);
                }
            }

        }
        return menus;

    }

    /**
     * @return
     * @Description 用户对应的菜单
     * @Param
     **/
    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> menu_id = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Menu.class, "menu_id", menu_id);
        ((CommonDao<Menu>) menuDao).deleteBatchOrSingle(detachedCriteria);
    }

    /**
     * @return java.util.List<com.github.chronic.pojo.Menu>
     * @Description 查找角色的菜单
     * @Param [role_id]
     **/
    @Override
    public List<Menu> findMenusByRoleid(Integer role_id) {
        return menuDao.findMenusByRoleid(role_id);
    }

    /**
     * @return void
     * @Description 分配与取消分配菜单
     * @Param [checkids, role_id]
     **/
    @Override
    public void updateRoleMenu(String checkids, Integer role_id) {
        List<Integer> menu_id = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Menu.class, "menu_id", menu_id);
        menuDao.deleteRoleMenu(role_id);
        menuDao.updateRoleMenu(detachedCriteria, role_id);
    }

    /**
     * @return java.util.List<com.github.chronic.pojo.Menu>
     * @Description 查找顶级菜单
     * @Param []
     **/
    @Override
    public JsonBean findMenusByPid() {
        return new JsonBean(((CommonDao<Menu>) menuDao).findFieldVerify("Menu", "pid", 0));

    }


    @Override
    public boolean saveMenu(Menu menu) {
        Integer rowResult = ((CommonDao<Menu>) menuDao).saveT(menu);
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateMenu(Menu menu) {
        if (((CommonDao<Menu>) menuDao).updateT(menu) > 0) {
            return true;
        }
        return false;
    }





/*    *//**
     * @return java.util.List<com.github.chronic.pojo.Menu>
     * @Description 查询菜单
     * @Param []
     **//*
    @Override
    public List<Menu> findMenus(String type) {
        List<Menu> setMenu = findsetMenu();

        if (type.trim().equals("defined")) {

            // 保存父菜单
            List<Menu> menus = new ArrayList<>();

            // 保存所有菜单，便于根据父id查询到父菜单
            Map<Integer, Menu> map = new HashMap<>();

            // 将所有菜单放入map，menu_id作为key
            for (Menu menu : setMenu) {
                map.put(menu.getMenu_id(), menu);

            }

            for (Menu menu : setMenu) {
                if (menu.getPid() == 0) {
                    // 保存父菜单
                    menus.add(menu);
                } else {
                    // 根据parent_id得到父菜单id
                    Integer pid = menu.getPid();

                    // 根据父菜单id得到父菜单
                    Menu p_menu = map.get(pid);

                    // 得到当前父菜单的子菜单，第一次为空
                    List<Menu> childs = p_menu.getChilds();
                    if (childs != null) {
                        // 当前有子菜单
                        childs.add(menu);
                    } else {
                        // 当前没有子菜单
                        childs = new ArrayList<>();
                        // 添加当前子菜单
                        childs.add(menu);
                        // 将当前整理好的子菜单设置进去
                        p_menu.setChilds(childs);
                    }
                }

            }
            return menus;
        }
        return setMenu;
    }*/



}
