package com.github.chronic.pojo;

import java.util.HashSet;
import java.util.Set;


public class Location implements java.io.Serializable{

	private static final long serialVersionUID = 142342312L;

	private Integer locid;

    private String org_code;

    private String area_code;

    private String org_name;

/*    private Integer level;*/

    private Set<User> users = new HashSet<>();

    public Location() {
    }

    public Location(Integer locid, String org_code, String area_code, String org_name) {
        this.locid = locid;
        this.org_code = org_code;
        this.area_code = area_code;
        this.org_name = org_name;
    }

    public Location(String org_code, String area_code, String org_name) {
        this.org_code = org_code;
        this.area_code = area_code;
        this.org_name = org_name;
    }


    public Integer getLocid() {
        return locid;
    }

    public void setLocid(Integer locid) {
        this.locid = locid;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }


/*    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }*/

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locid=" + locid +
                ", org_code='" + org_code + '\'' +
                ", area_code='" + area_code + '\'' +
                ", org_name='" + org_name + '\'' +
                '}';
    }
}
