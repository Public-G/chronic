package com.github.chronic.action.permission;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.Menu;
import com.github.chronic.service.permission.AreaService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName AreaAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/21 21:45
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction implements ModelDriven<Area> {

    private Area area;

    @Autowired
    private AreaService areaService;

    private List<Area> areaList;


    public String area_treeList() {
        areaList = areaService.findAreaByAreacode(session_location.getArea_code());
        jsonBean.setData(areaList);
        return SUCCESS;
    }

    public String area_update() {
        String urlPath = null;
        if (forwardType.equals("addSelect")) {
            areaList = areaService.findAreaByLevel();
            urlPath = "area_update";
        }

         if(forwardType.equals("update")){
             flag = areaService.updateArea(area);
             if (flag) {
                 callbackTwo();
             } else {
                 callbackMsg();
             }
             urlPath = SUCCESS;
         }

        return urlPath;
    }

    public String area_del() {
        try {
            areaService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } catch (Exception e) {
            callbackMsg();
        }

        return SUCCESS;
    }

    public String area_fieldVerify() {
        flag = areaService.fieldVerify(area);
        if (flag) {
            callbackCode();
        }
        return SUCCESS;
    }


    public String area_add() {
        String urlPath = null;
        if (forwardType.equals("addSelect")) {
            areaList = areaService.findAreaByLevel();
            urlPath = "area_add";
        }
        if (forwardType.equals("add")) {
            flag = areaService.saveArea(area);
            if (flag) {
                callbackTwo();
            } else {
                callbackMsg();
            }
            urlPath = SUCCESS;
        }

        return urlPath;
    }

    /**
     * @return
     * @Description 行政区域
     * @Param
     **/
    public String area_list() {
        if (curr != null) {
            jsonBean = areaService.findArea(area.getArea_name(), curr);
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }

    /**
     * 页面跳转
     */
    public String area_forward() {
        String urlPath = null;
        switch (forwardType) {
            case "area_tab":
                urlPath = "area_tab";
                break;
        }
        return urlPath;
    }

    @Override
    public Area getModel() {
        area = new Area();
        return area;
    }

    public List<Area> getAreaList() {
        return areaList;
    }
}
