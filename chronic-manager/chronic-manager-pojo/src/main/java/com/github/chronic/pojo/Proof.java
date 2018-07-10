package com.github.chronic.pojo;

import java.util.Date;

/**
 * @ClassName Proof
 * @Description 慢性病证
 * @Author ZEALER
 * @Date 2018/6/23 15:15
 * @Version 1.0
 **/
public class Proof implements java.io.Serializable{

	private static final long serialVersionUID = 232131211L;

	private Integer pfid;

    private String proof_id; //慢性病证号

    private String proof_name; //慢性病名称

    private Date start_time; //起始时间

    private Date end_time; //终止时间

    //体现一对一的关系。保存映射类的实例对象
    private File file;

    public Proof() {
    }

    public Proof(Integer pfid, String proof_id, String proof_name, Date start_time, Date end_time) {
        this.pfid = pfid;
        this.proof_id = proof_id;
        this.proof_name = proof_name;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public Integer getPfid() {
        return pfid;
    }

    public void setPfid(Integer pfid) {
        this.pfid = pfid;
    }

    public String getProof_id() {
        return proof_id;
    }

    public void setProof_id(String proof_id) {
        this.proof_id = proof_id;
    }

    public String getProof_name() {
        return proof_name;
    }

    public void setProof_name(String proof_name) {
        this.proof_name = proof_name;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Proof{" +
                "pfid=" + pfid +
                ", proof_id='" + proof_id + '\'' +
                ", proof_name='" + proof_name + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}
