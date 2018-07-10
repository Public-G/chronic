package com.github.chronic.action.common;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Location;
import com.opensymphony.xwork2.ActionContext;

/**
 * @ClassName CommonAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/17 0:40
 * @Version 1.0
 **/
public class CommonAction {

    protected Integer curr; //当前页

    protected String checkids; //要删除的id

    protected String forwardType; //跳转类型

    protected boolean flag;

    protected JsonBean jsonBean = new JsonBean();

    protected final Location session_location = (Location) ActionContext.getContext().getSession().get("location");


    protected final static String SUCCESS = "success";

    private final static String SUCCESS_MSG = "操作成功";

    private final static String FAIL_MSG = "操作失败";

    private final static Integer SUCCESS_CODE = 200;

    private final static Integer FAIL_CODE = 500;


    public void setCurr(Integer curr) {
        this.curr = curr;
    }

    public void setCheckids(String checkids) {
        this.checkids = checkids;
    }

    public void setForwardType(String forwardType) {
        this.forwardType = forwardType;
    }

    public JsonBean getJsonBean() {
        return jsonBean;
    }

    public JsonBean callbackMsg(){
        jsonBean.setMsg(FAIL_MSG);
        return jsonBean;
    }

    public JsonBean callbackTwo(){
        jsonBean.setCode(SUCCESS_CODE);
        jsonBean.setMsg(SUCCESS_MSG);
        return jsonBean;
    }

    public JsonBean callbackCode(){
        jsonBean.setCode(SUCCESS_CODE);
        return jsonBean;
    }

    public JsonBean callbackFailCode(){
        jsonBean.setCode(FAIL_CODE);
        return jsonBean;
    }



}
