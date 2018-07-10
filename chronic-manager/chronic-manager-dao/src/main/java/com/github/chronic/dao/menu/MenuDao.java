package com.github.chronic.dao.menu;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Menu;
import org.hibernate.criterion.DetachedCriteria;


import java.util.List;


public interface MenuDao{

    List<Menu> findAllMenu();

    List<Menu> findMenusByRoleid(Integer role_id);

    void deleteRoleMenu(Integer role_id);

    void updateRoleMenu(DetachedCriteria detachedCriteria, Integer role_id);

    List<Menu> findMenusByPid();

    boolean saveMenu(Menu menu);

}
