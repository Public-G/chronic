package com.github.chronic.pojo;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Menu implements Comparable<Menu>, java.io.Serializable{

	private static final long serialVersionUID = 1236921L;

	private Integer menu_id;

    private String menu_name;

    private String menu_icon;

    private Integer pid;

    private String request_url;

    //用来保存子菜单
    private List<Menu> childs;

    private Set<Role> roles = new HashSet<>();

    public Menu() {
    }

    public Menu(Integer menu_id, String menu_name, String menu_icon, Integer pid, String request_url) {
        this.menu_id = menu_id;
        this.menu_name = menu_name;
        this.menu_icon = menu_icon;
        this.pid = pid;
        this.request_url = request_url;
    }

    public Menu(String menu_name, String menu_icon, Integer pid, String request_url) {
        this.menu_name = menu_name;
        this.menu_icon = menu_icon;
        this.pid = pid;
        this.request_url = request_url;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.menu_icon = menu_icon;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRequest_url() {
        return request_url;
    }

    public void setRequest_url(String request_url) {
        this.request_url = request_url;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Menu> getChilds() {
        return childs;
    }

    public void setChilds(List<Menu> childs) {
        this.childs = childs;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (menu_id != null ? !menu_id.equals(menu.menu_id) : menu.menu_id != null) return false;
        if (menu_name != null ? !menu_name.equals(menu.menu_name) : menu.menu_name != null) return false;
        if (menu_icon != null ? !menu_icon.equals(menu.menu_icon) : menu.menu_icon != null) return false;
        if (pid != null ? !pid.equals(menu.pid) : menu.pid != null) return false;
        if (request_url != null ? !request_url.equals(menu.request_url) : menu.request_url != null) return false;
        if (childs != null ? !childs.equals(menu.childs) : menu.childs != null) return false;
        return roles != null ? roles.equals(menu.roles) : menu.roles == null;
    }

    @Override
    public int hashCode() {
        int result = menu_id != null ? menu_id.hashCode() : 0;
        result = 31 * result + (menu_name != null ? menu_name.hashCode() : 0);
        result = 31 * result + (menu_icon != null ? menu_icon.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (request_url != null ? request_url.hashCode() : 0);
        result = 31 * result + (childs != null ? childs.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menu_id=" + menu_id +
                ", menu_name='" + menu_name + '\'' +
                ", menu_icon='" + menu_icon + '\'' +
                ", pid=" + pid +
                ", request_url='" + request_url + '\'' +
                '}';
    }

    /**
    * @Description 定义排序规则
    * @Param [o]
    * @return int
    **/
    @Override
    public int compareTo(Menu o) {

        int len = this.getMenu_name().length() - o.getMenu_name().length();

        if(len == 0){
            return this.getMenu_id() - o.getMenu_id();
        }

        return len;
    }
}
