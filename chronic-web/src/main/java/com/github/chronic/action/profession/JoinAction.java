package com.github.chronic.action.profession;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Join;
import com.github.chronic.service.profession.FileService;
import com.github.chronic.service.profession.JoinService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 * @ClassName JoinAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/25 11:05
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class JoinAction extends CommonAction {

    @Autowired
    private JoinService joinService;

    @Autowired
    private FileService fileService;

    private String username;

    private Integer jid;

    public String join_add() {
        flag = joinService.saveJoin(jid);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String join_del() {
        if (jid != null) {
            try {
                joinService.deleteBatchOrSingle(jid);
                callbackTwo();
            } catch (Exception e) {
                callbackMsg();
            }
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String join_list() {
        if (curr != null) {
            jsonBean = fileService.findFamilyByName(curr, username, "join", session_location.getArea_code());
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    /**
     * 页面跳转
     */
    public String join_forward() {
        return "join_tab";
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
