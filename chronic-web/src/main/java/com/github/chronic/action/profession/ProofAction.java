package com.github.chronic.action.profession;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Proof;
import com.github.chronic.service.profession.FileService;
import com.github.chronic.service.profession.ProofService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @ClassName ProofAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 10:19
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class ProofAction extends CommonAction implements ModelDriven<Proof> {

    @Autowired
    private ProofService proofService;

    @Autowired
    private FileService fileService;

    private Proof proof = new Proof();

    private String username;

    public String proof_del() {
        try {
            proofService.deleteBatchOrSingle(proof.getPfid());
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String proof_update() {
        flag = proofService.updateProof(proof);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String proof_add() {
        flag = proofService.saveProof(proof);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String proof_list() {
        if (curr != null) {
            jsonBean = fileService.findFamilyByName(curr, username, "proof", session_location.getArea_code());
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }


    /**
     * 页面跳转
     */
    public String proof_forward() {
        String urlPath = "proof_tab";
        if (forwardType != null) {
            switch (forwardType) {
                case "proof_add":
                    urlPath = "proof_add";
                    break;
                case "proof_update":
                    proof = proofService.findProofByPfid(proof.getPfid());
                    System.out.println("proof:" + proof);
                    urlPath = "proof_update";
                    break;
            }
        }
        return urlPath;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public Proof getModel() {
        return proof;
    }

    public Proof getProof() {
        return proof;
    }
}
