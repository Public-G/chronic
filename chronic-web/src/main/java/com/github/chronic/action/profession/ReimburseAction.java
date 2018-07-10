package com.github.chronic.action.profession;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.Reimburse;
import com.github.chronic.pojo.User;
import com.github.chronic.service.profession.FileService;
import com.github.chronic.service.profession.ProofService;
import com.github.chronic.service.profession.ReimburseService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ReimburseAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/28 18:10
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class ReimburseAction extends CommonAction implements ModelDriven<Reimburse> {

    private Reimburse reimburse;

    @Autowired
    private ReimburseService reimburseService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProofService proofService;

    private String area_code;

    private Integer fid;


    public String reimburse_del() {
        try{
            reimburseService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }
        return SUCCESS;
    }


    public String reimburse_list() {
        if (curr != null) {
            jsonBean = reimburseService.findReimburse(curr, reimburse, area_code, session_location.getArea_code());
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    public String reimburse_add() {
        User user = (User) ActionContext.getContext().getSession().get("loginUser");
        flag = reimburseService.saveReimburse(reimburse, session_location, user.getJob_id());
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }


    public String reimburse_check() {
        flag = proofService.findField(fid, reimburse.getProof_id(), reimburse.getProof_name(), reimburse.getMedical_time());  //校验慢性病证合法性
        if (flag) { //计算本次报销金额
            BigDecimal bigDecimal = reimburseService.findCalculateAmount(reimburse.getIdentity(), reimburse.getMedical_expenses());
            if (bigDecimal.compareTo(BigDecimal.valueOf(0)) == 0) {
                callbackFailCode();
                jsonBean.setMsg("剩余报销金额为0");
            } else {
                callbackCode();
                jsonBean.setData(bigDecimal);
                jsonBean.setMsg("本次报销金额：" + bigDecimal + "元");
            }

        } else {
            callbackFailCode();
            jsonBean.setMsg("慢性病证信息不符");
        }
        return SUCCESS;
    }

    public String reimburse_search() {

        List list = fileService.findFileByIdentity(reimburse.getIdentity(), session_location.getArea_code());
        if (list != null && list.size() > 0) {
            jsonBean.setData(list.get(0));
            callbackCode();
        } else {
            jsonBean.setMsg("系统未找到该身份证号或者该身份证号尚未参合");
        }
        return SUCCESS;
    }


    /**
     * 页面跳转
     */
    public String reimburse_forward() {
        String urlPath = "reimburse_tab";
        if (forwardType != null) {
            switch (forwardType) {
                case "reimburse_add":
                    urlPath = "reimburse_add";
                    break;
                case "reimburse_list":
                    urlPath = "reimburse_list";
                    break;
            }
        }
        return urlPath;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    @Override
    public Reimburse getModel() {
        reimburse = new Reimburse();
        return reimburse;
    }

}
