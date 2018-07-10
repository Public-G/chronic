package com.github.chronic.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName Reimburse
 * @Description
 * @Author ZEALER
 * @Date 2018/6/28 18:02
 * @Version 1.0
 **/
public class Reimburse implements java.io.Serializable{

	private static final long serialVersionUID = 1666341L;

	private Integer rid;

    private String reimburse_id; //报销单号

    private String identity; //身份证号

    private String username; //姓名

    private String join_id; //参合证号

    private String proof_id; //慢性病号

    private String proof_name; //疾病名称

    private String medical_name; //医疗机构名称

    private String medical_invoice; //医院发票号

    private BigDecimal medical_expenses; //医疗费用

    private Date medical_time; //就诊时间

    private BigDecimal reimburse_amount; //本次报销金额

    private String org_name; //经办机构

    private String job_id; //操作人工号

    private String ip; //操作IP

    private Date reimburse_time; //报销时间

    private int is_delete;

    public Reimburse() {
    }

    public Reimburse(Integer rid, String reimburse_id, String identity, String username, String join_id, String proof_id, String proof_name, String medical_name, String medical_invoice, BigDecimal medical_expenses, Date medical_time, BigDecimal reimburse_amount, String org_name, String job_id, String ip, Date reimburse_time, int is_delete) {
        this.rid = rid;
        this.reimburse_id = reimburse_id;
        this.identity = identity;
        this.username = username;
        this.join_id = join_id;
        this.proof_id = proof_id;
        this.proof_name = proof_name;
        this.medical_name = medical_name;
        this.medical_invoice = medical_invoice;
        this.medical_expenses = medical_expenses;
        this.medical_time = medical_time;
        this.reimburse_amount = reimburse_amount;
        this.org_name = org_name;
        this.job_id = job_id;
        this.ip = ip;
        this.reimburse_time = reimburse_time;
        this.is_delete = is_delete;
    }

    public Reimburse(Integer rid, String reimburse_id, String identity, String username, String join_id, String proof_id, String proof_name, String medical_name, String medical_invoice, BigDecimal medical_expenses, Date medical_time, BigDecimal reimburse_amount, String org_name, String job_id, String ip, Date reimburse_time) {
        this.rid = rid;
        this.reimburse_id = reimburse_id;
        this.identity = identity;
        this.username = username;
        this.join_id = join_id;
        this.proof_id = proof_id;
        this.proof_name = proof_name;
        this.medical_name = medical_name;
        this.medical_invoice = medical_invoice;
        this.medical_expenses = medical_expenses;
        this.medical_time = medical_time;
        this.reimburse_amount = reimburse_amount;
        this.org_name = org_name;
        this.job_id = job_id;
        this.ip = ip;
        this.reimburse_time = reimburse_time;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getReimburse_id() {
        return reimburse_id;
    }

    public void setReimburse_id(String reimburse_id) {
        this.reimburse_id = reimburse_id;
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

    public String getJoin_id() {
        return join_id;
    }

    public void setJoin_id(String join_id) {
        this.join_id = join_id;
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

    public String getMedical_name() {
        return medical_name;
    }

    public void setMedical_name(String medical_name) {
        this.medical_name = medical_name;
    }

    public String getMedical_invoice() {
        return medical_invoice;
    }

    public void setMedical_invoice(String medical_invoice) {
        this.medical_invoice = medical_invoice;
    }

    public BigDecimal getMedical_expenses() {
        return medical_expenses;
    }

    public void setMedical_expenses(BigDecimal medical_expenses) {
        this.medical_expenses = medical_expenses;
    }

    public Date getMedical_time() {
        return medical_time;
    }

    public void setMedical_time(Date medical_time) {
        this.medical_time = medical_time;
    }

    public BigDecimal getReimburse_amount() {
        return reimburse_amount;
    }

    public void setReimburse_amount(BigDecimal reimburse_amount) {
        this.reimburse_amount = reimburse_amount;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getReimburse_time() {
        return reimburse_time;
    }

    public void setReimburse_time(Date reimburse_time) {
        this.reimburse_time = reimburse_time;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    @Override
    public String toString() {
        return "Reimburse{" +
                "rid=" + rid +
                ", reimburse_id='" + reimburse_id + '\'' +
                ", identity='" + identity + '\'' +
                ", username='" + username + '\'' +
                ", join_id='" + join_id + '\'' +
                ", proof_id='" + proof_id + '\'' +
                ", proof_name='" + proof_name + '\'' +
                ", medical_name='" + medical_name + '\'' +
                ", medical_invoice='" + medical_invoice + '\'' +
                ", medical_expenses=" + medical_expenses +
                ", medical_time=" + medical_time +
                ", reimburse_amount=" + reimburse_amount +
                ", org_name='" + org_name + '\'' +
                ", job_id='" + job_id + '\'' +
                ", ip='" + ip + '\'' +
                ", reimburse_time=" + reimburse_time +
                ", is_delete=" + is_delete +
                '}';
    }
}
