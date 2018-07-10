package com.github.chronic.dao.impl.menu;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.menu.MenuDao;
import com.github.chronic.pojo.Menu;
import com.github.chronic.pojo.Role;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @ClassName MenuDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 18:39
 * @Version 1.0
 **/
@Repository
public class MenuDaoImpl extends CommonDaoImpl<Menu> implements MenuDao {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Menu> findAllMenu() {
        String hql = "select new Menu(menu_id, menu_name, menu_icon, pid, request_url) from Menu";
        return hibernateUtil.getSession().createQuery(hql).list();
    }

    @Override
    public List<Menu> findMenusByRoleid(Integer role_id) {
        String sqlQuery = "SELECT m.menu_id, m.menu_name, m.menu_icon, m.pid, m.request_url FROM tb_menu m INNER JOIN tb_role_menu rm ON m.`menu_id` = rm.`menu_id` WHERE rm.`role_id`= ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Menu.class), role_id);
    }

    @Override
    public void deleteRoleMenu(Integer role_id) {
        Session session = hibernateUtil.getSession();
        Set<Menu> menus = session.get(Role.class, role_id).getMenus();
        //查询现有的菜单
        String sqlQuery = "SELECT m.* FROM tb_menu m INNER JOIN tb_role_menu rm ON m.`menu_id` = rm.`menu_id` WHERE rm.`role_id`= ?";
        List<Menu> query = session.createSQLQuery(sqlQuery).addEntity(Menu.class).setParameter(0, role_id).list();

        //删除现有菜单
        for (Menu menu : query) {
            menus.remove(menu);
        }
    }

    @Override
    public void updateRoleMenu(DetachedCriteria detachedCriteria, Integer role_id) {
        Session session = hibernateUtil.getSession();
        Set<Menu> menus = session.get(Role.class, role_id).getMenus();
        //查询要分配的菜单
        List<Menu> menuList = detachedCriteria.getExecutableCriteria(session).list();

        //添加新分配菜单
        for (Menu menu : menuList) {
            menus.add(menu);
        }

        //删除现有菜单（可能会有延迟，session.get(Role.class, role_id).getMenus().add() 可能以为此集合已包含该元素，而添加不进去）
//        String delSql = "DELETE FROM t_role_menu  WHERE `role_id`= ?";
//        jdbcTemplate.update(delSql, role_id);
    }

    @Override
    public List<Menu> findMenusByPid() {
        String sqlQuery = "SELECT m.menu_id, m.menu_name FROM tb_menu m WHERE m.`pid`= ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Menu.class), 0);
    }

    @Override
    public boolean saveMenu(Menu menu) {
        return false;
    }

    @Override
    public Integer updateT(Menu entity) {
        String hql = "update Menu set menu_name = ?, menu_icon = ?, pid = ?, request_url = ? where menu_id = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, entity.getMenu_name())
                .setParameter(1, entity.getMenu_icon()).setParameter(2, entity.getPid())
                .setParameter(3, entity.getRequest_url()).setParameter(4, entity.getMenu_id()).executeUpdate();
    }
}
