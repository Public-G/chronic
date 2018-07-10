package com.github.chronic.pojo;


public class Area implements java.io.Serializable{

	private static final long serialVersionUID = 143254L;

	private Integer aid;

    private String area_code;

    private String pid;

    private Integer level;

    private String area_name;

    public Area() {

    }

    public Area(String area_code, String pid, String area_name) {
        this.area_code = area_code;
        this.pid = pid;
        this.area_name = area_name;
    }

    public Area(Integer aid, String area_code, String pid, String area_name, Integer level) {
        this.aid = aid;
        this.area_code = area_code;
        this.pid = pid;
        this.area_name = area_name;
        this.level = level;
    }



    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }


    @Override
    public String toString() {
        return "Area{" +
                "aid=" + aid +
                ", area_code='" + area_code + '\'' +
                ", pid='" + pid + '\'' +
                ", level=" + level +
                ", area_name='" + area_name + '\'' +
                '}';
    }
}
