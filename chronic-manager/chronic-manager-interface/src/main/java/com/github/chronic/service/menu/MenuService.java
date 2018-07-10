package com.github.chronic.service.menu;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Menu;

import java.util.List;

public interface MenuService {

/*    List<Menu> findMenus(String type);*/

    List<Menu> findMenusByRoleid(Integer role_id);

    void updateRoleMenu(String checkids, Integer role_id);

    JsonBean findMenusByPid();

    boolean saveMenu(Menu menu);

    boolean updateMenu(Menu menu);

    void deleteBatchOrSingle(String checkids);

    List<Menu> findAllMenu();

    List<Menu> findMenuByUser(Object object);
}
