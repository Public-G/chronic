package com.github.chronic.action.profession;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Policy;
import com.github.chronic.service.profession.PolicyService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @ClassName PolicyAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 23:07
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class PolicyAction extends CommonAction implements ModelDriven<Policy> {

    private Policy policy;

    @Autowired
    private PolicyService policyService;

    public String policy_del() {
        try {
            policyService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String policy_update() {
        flag = policyService.updatePolicy(policy);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String policy_add() {
        flag = policyService.savePolicy(policy);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String policy_list() {
        if (curr != null) {
            jsonBean = policyService.findPolicy(curr, policy);
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    /**
     * 页面跳转
     */
    public String policy_forward() {
        String urlPath = null;
        if (forwardType == null) {
            return "policy_tab";

        }
        switch (forwardType) {
            case "policy_add":
                urlPath = "policy_add";
                break;
            case "policy_update":
                urlPath = "policy_update";
                break;

        }
        return urlPath;
    }

    @Override
    public Policy getModel() {
        policy = new Policy();
        return policy;
    }
}
