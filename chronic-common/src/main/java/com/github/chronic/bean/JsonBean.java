package com.github.chronic.bean;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName JsonBean
 * @Description
 * @Author ZEALER
 * @Date 2018/6/16 21:01
 * @Version 1.0
 **/
public class JsonBean implements java.io.Serializable{

	private static final long serialVersionUID = 1432423321L;

	private Integer code;

    private String msg;

    private Integer limit;

    private Integer curr;

    private Long count;

    private Object data;

    public JsonBean() {
    }

    public JsonBean(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonBean(Object data) {
        this.data = data;
    }

    public JsonBean(Integer limit, Integer curr, Long count, Object data) {
        this.limit = limit;
        this.curr = curr;
        this.count = count;
        this.data = data;
    }

    /**
    * @Description 对象转json字符串
    * @Param Object
    * @return java.lang.String
    **/
    public String toJSONString(Object object){
        return JSON.toJSONString(object);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getCurr() {
        return curr;
    }

    public void setCurr(Integer curr) {
        this.curr = curr;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", limit=" + limit +
                ", curr=" + curr +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
