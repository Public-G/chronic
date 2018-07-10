package com.github.chronic.action.profession;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.File;
import com.github.chronic.service.permission.AreaService;
import com.github.chronic.service.profession.FileService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FileAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/23 16:30
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class FileAction extends CommonAction implements ModelDriven<File>{

    private File file;

    @Autowired
    private FileService fileService;

    @Autowired
    private AreaService areaService;

    private List<Area> areaList;

    private List<File> fileList;


    public String file_del() {
        try{
            fileService.deleteBatchOrSingle(checkids, file.getPid());
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String file_update() {
        flag = fileService.updateFile(file);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String file_add() {
        flag = fileService.saveFile(file);
        if (flag) {
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }


    public String file_list() {
        if (curr != null) {
            jsonBean = fileService.findFile(curr, file, session_location.getArea_code());
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    /**
     * 页面跳转
     */
    public String file_forward() {
        String urlPath = null;
        if (forwardType == null) {
            return "file_tab";

        }

        switch (forwardType) {
            case "addSelect":
                areaList = areaService.findAreaByAreacode(session_location.getArea_code());
                urlPath = "file_add";
                break;
            case "addChild":
                urlPath = "file_childAdd";
                break;
            case "update":
                fileList = fileService.findFileByFid(file.getFid());
                urlPath = "file_update";
                break;
            case "childUpdate":
                fileList = fileService.findFileByFid(file.getFid());
                urlPath = "file_childUpdate";
        }
        return urlPath;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public List<File> getFileList() {
        return fileList;
    }


    @Override
    public File getModel() {
        file = new File();
        return file;
    }

}
