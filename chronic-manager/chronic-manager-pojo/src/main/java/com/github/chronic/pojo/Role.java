package com.github.chronic.pojo;


import java.util.HashSet;
import java.util.Set;

public class Role implements java.io.Serializable{

	private static final long serialVersionUID = 183411L;

	private Integer role_id;

    private String role_name;

    private String role_label;

    private Set<User> users = new HashSet<>();

    private Set<Menu> menus = new HashSet<>();

    public Role() {
    }

    public Role(Integer role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public Role(Integer role_id, String role_name, String role_label) {
        this.role_id = role_id;
        this.role_name = role_name;
        this.role_label = role_label;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_label() {
        return role_label;
    }

    public void setRole_label(String role_label) {
        this.role_label = role_label;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", role_name='" + role_name + '\'' +
                ", role_label='" + role_label + '\'' +
                '}';
    }
}
