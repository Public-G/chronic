package com.github.chronic.action.dispatcher;

import com.github.chronic.pojo.Menu;
import com.github.chronic.service.menu.MenuService;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Scope("prototype")
public class DispatcherAction implements ServletRequestAware {

    private HttpServletRequest request;

    @Autowired
    private MenuService menuService;

    public String index() {
        // 校验
        // 判断session中是否有这个用户，如果没有去登陆页面
        Object object = request.getSession().getAttribute("loginUser");
        if (object == null) {
            // 用户没登陆
            return "invalid";
        }
        // 用户登陆才来到主页，session中没有菜单，或者菜单从session中清除了
        if (request.getSession().getAttribute("userMenus") == null) {
            // 查出所有菜单
            List<Menu> menus = menuService.findMenuByUser(object);

            // 将查到的菜单放在session域中
            request.getSession().setAttribute("userMenus", menus);
        }
        return "success";
    }


    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}
