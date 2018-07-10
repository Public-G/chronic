package com.github.chronic.pojo;

import java.math.BigDecimal;

/**
 * @ClassName Policy
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 23:02
 * @Version 1.0
 **/
public class Policy implements java.io.Serializable{

	private static final long serialVersionUID = 14343333L;

	private Integer plid;
    
    private String year;

    private BigDecimal capline;

    private BigDecimal scale;


    public Policy() {
    }

    public Policy(Integer plid, String year, BigDecimal capline, BigDecimal scale) {
        this.plid = plid;
        this.year = year;
        this.capline = capline;
        this.scale = scale;
    }

    public Integer getPlid() {
        return plid;
    }

    public void setPlid(Integer plid) {
        this.plid = plid;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getCapline() {
        return capline;
    }

    public void setCapline(BigDecimal capline) {
        this.capline = capline;
    }

    public BigDecimal getScale() {
        return scale;
    }

    public void setScale(BigDecimal scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "plid=" + plid +
                ", year='" + year + '\'' +
                ", capline=" + capline +
                ", scale=" + scale +
                '}';
    }
}
