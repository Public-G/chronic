package com.github.chronic.pojo;

import java.util.Date;

/**
 * @ClassName File
 * @Description 档案
 * @Author ZEALER
 * @Date 2018/6/23 15:11
 * @Version 1.0
 **/
public class File implements java.io.Serializable{

	private static final long serialVersionUID = 187623L;

	private Integer fid;

    private String area_code; //区域编码

    private String group_id; //组编号

    private String family_id; //家庭编号

/*    private String family_head; //户主*/

    private String identity; //身份证号

    private String username; //姓名

    private char sex; //性别

    private Date birth; //出生年月日

    private String address; //地址

    private String cellphone; //联系电话

    private int pid; //父ID

    private String relation; //与户主的关系

    private Date create_time; //建档时间

    private int is_delete; //是否删除

    //体现一对一的关系。保存映射类的实例对象
    private Join join;

    //体现一对一的关系。保存映射类的实例对象
    private Proof proof;



    public File() {
    }

    public File(Integer fid, String area_code, String group_id, String family_id, String identity, String username, char sex, Date birth, String address, String cellphone, String relation, Date create_time) {
        this.fid = fid;
        this.area_code = area_code;
        this.group_id = group_id;
        this.family_id = family_id;
        this.identity = identity;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.cellphone = cellphone;
        this.relation = relation;
        this.create_time = create_time;
    }

    public File(String area_code, String group_id,  String family_id, String identity, String username, char sex, Date birth, String address, String cellphone, String relation, Date create_time) {
        this.area_code = area_code;
        this.group_id = group_id;
        this.family_id = family_id;
        this.identity = identity;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.cellphone = cellphone;
        this.relation = relation;
        this.create_time = create_time;
    }

    public File(Integer fid, String area_code, String group_id, String family_id, String identity, String username, char sex, Date birth, String address, String cellphone, Date create_time) {
        this.fid = fid;
        this.area_code = area_code;
        this.group_id = group_id;
        this.family_id = family_id;
        this.identity = identity;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.cellphone = cellphone;
        this.create_time = create_time;
    }

    public File(Integer fid, String area_code, String group_id, String family_id, String identity, String username, char sex, Date birth, String address, String cellphone, int pid, String relation, Date create_time) {
        this.fid = fid;
        this.area_code = area_code;
        this.group_id = group_id;
        this.family_id = family_id;
        this.identity = identity;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.cellphone = cellphone;
        this.pid = pid;
        this.relation = relation;
        this.create_time = create_time;
    }

    public File(Integer fid, String area_code, String group_id, String family_id, String identity, String username, char sex, Date birth, String address, String cellphone, int pid, String relation, Date create_time, int is_delete, Join join, Proof proof) {
        this.fid = fid;
        this.area_code = area_code;
        this.group_id = group_id;
        this.family_id = family_id;
        this.identity = identity;
        this.username = username;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
        this.cellphone = cellphone;
        this.pid = pid;
        this.relation = relation;
        this.create_time = create_time;
        this.is_delete = is_delete;
        this.join = join;
        this.proof = proof;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Join getJoin() {
        return join;
    }

    public void setJoin(Join join) {
        this.join = join;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    @Override
    public String toString() {
        return "File{" +
                "fid=" + fid +
                ", area_code='" + area_code + '\'' +
                ", group_id='" + group_id + '\'' +
                ", family_id='" + family_id + '\'' +
                ", identity='" + identity + '\'' +
                ", username='" + username + '\'' +
                ", sex=" + sex +
                ", birth=" + birth +
                ", address='" + address + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", pid=" + pid +
                ", relation='" + relation + '\'' +
                ", create_time=" + create_time +
                ", is_delete=" + is_delete +
                ", join=" + join +
                ", proof=" + proof +
                '}';
    }
}
