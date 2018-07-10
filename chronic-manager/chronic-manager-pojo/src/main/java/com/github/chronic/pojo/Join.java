package com.github.chronic.pojo;

import java.util.Date;

/**
 * @ClassName Join
 * @Description 参合登记
 * @Author ZEALER
 * @Date 2018/6/23 15:17
 * @Version 1.0
 **/
public class Join implements java.io.Serializable{

	private static final long serialVersionUID = 14325423523L;

	private Integer jid;
    
    private String join_id; //参合证号
    
    private String invoice; //参合发票号

    private Date create_time; //参合时间

    //体现一对一的关系。保存映射类的实例对象
    private File file;

    public Join() {
    }

    public Join(Integer jid, String join_id, String invoice, Date create_time) {
        this.jid = jid;
        this.join_id = join_id;
        this.invoice = invoice;
        this.create_time = create_time;
    }

    public Join(String join_id, String invoice, Date create_time) {
        this.join_id = join_id;
        this.invoice = invoice;
        this.create_time = create_time;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public String getJoin_id() {
        return join_id;
    }

    public void setJoin_id(String join_id) {
        this.join_id = join_id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Join{" +
                "jid=" + jid +
                ", join_id='" + join_id + '\'' +
                ", invoice='" + invoice + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
