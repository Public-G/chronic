package com.github.chronic.action.permission;

import com.github.chronic.action.common.CommonAction;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.Location;
import com.github.chronic.service.permission.AreaService;
import com.github.chronic.service.permission.LocationService;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @ClassName LocationAction
 * @Description
 * @Author ZEALER
 * @Date 2018/6/19 17:44
 * @Version 1.0
 **/
@Controller
@Scope("prototype")
public class LocationAction extends CommonAction implements ModelDriven<Location>{

    private Location location;

    @Autowired
    private LocationService locationService;

    @Autowired
    private AreaService areaService;

    

    private List<Area> areaList;


    public String location_update() {
        String urlPath = null;
        if (forwardType.equals("updateSelect")) {
            areaList = areaService.findAreaByLevel();
            urlPath = "location_update";
        }
        if (forwardType.equals("update")) {
            flag = locationService.updateLocation(location);
            if (flag) {
                callbackTwo();
            } else {
                callbackMsg();
            }
            urlPath = SUCCESS;
        }
        return urlPath;
    }


    public String location_del() {
        if (!checkids.trim().equals("")) {
            locationService.deleteBatchOrSingle(checkids);
            callbackTwo();
        } else {
            callbackMsg();
        }
        return SUCCESS;
    }

    public String location_fieldVerify() {
        flag = locationService.fieldVerify(location);
        if (flag) {
            callbackCode();
        }
        return SUCCESS;
    }

    public String location_add() {
        String urlPath = null;
        if (forwardType.equals("addSelect")) {
            areaList = areaService.findAreaByLevel();
            urlPath = "location_add";
        }
        if (forwardType.equals("add")) {
            flag = locationService.saveLocation(location);
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
    * @Description 网点信息
    * @Param
    * @return
    **/
    public String location_list() {
        if (curr != null) {
            jsonBean = locationService.findLocation(location.getOrg_name(), curr);
        }
        if (jsonBean.getData() != null) {
            callbackCode();
        }
        return SUCCESS;
    }


    /**
     * 页面跳转
     */
    public String location_forward() {
        String urlPath = null;
        switch (forwardType) {
            case "location_tab":
                urlPath = "location_tab";
                break;
        }
        return urlPath;
    }
    
    
    @Override
    public Location getModel() {
        location = new Location();
        return location;
    }


    public List<Area> getAreaList() {
        return areaList;
    }
}
