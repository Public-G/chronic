package com.github.chronic.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User implements java.io.Serializable{

	private static final long serialVersionUID = 1111920L;

	private Integer user_id;

    private String user_name;

    private String password_md5;

    private String cellphone;

    private String job_id;

    private String email;

    private Date create_time;

    private Location location;

    private Set<Role> roles = new HashSet<>();


    private String org_name;


    public User() {
    }

    public User(Integer user_id, String user_name, String cellphone, String job_id, String email, Date create_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.cellphone = cellphone;
        this.job_id = job_id;
        this.email = email;
        this.create_time = create_time;
    }

    public User(Integer user_id, String user_name, String password_md5, String cellphone, String job_id, String email, Date create_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password_md5 = password_md5;
        this.cellphone = cellphone;
        this.job_id = job_id;
        this.email = email;
        this.create_time = create_time;
    }

    public User(Integer user_id, String user_name, String cellphone, String job_id, String email, Date create_time, Location location) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.cellphone = cellphone;
        this.job_id = job_id;
        this.email = email;
        this.create_time = create_time;
        this.location = location;
    }

    public User(Integer user_id, String user_name, String password_md5, String cellphone, String job_id, String email, Date create_time, Location location, Set<Role> roles) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password_md5 = password_md5;
        this.cellphone = cellphone;
        this.job_id = job_id;
        this.email = email;
        this.create_time = create_time;
        this.location = location;
        this.roles = roles;
    }

    public User(Integer user_id, String user_name, String cellphone, String job_id, String email, Date create_time, String org_name) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.cellphone = cellphone;
        this.job_id = job_id;
        this.email = email;
        this.create_time = create_time;
        this.org_name = org_name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword_md5() {
        return password_md5;
    }

    public void setPassword_md5(String password_md5) {
        this.password_md5 = password_md5;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", password_md5='" + password_md5 + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", job_id=" + job_id +
                ", email='" + email + '\'' +
                ", create_time=" + create_time +
                ", location=" + location +
                ", roles=" + roles +
                '}';
    }
}
