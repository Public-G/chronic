package com.github.chronic.service.impl.profession;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.profession.FileDao;
import com.github.chronic.pojo.Area;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.Proof;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.profession.FileService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.FormatUtil;
import com.github.chronic.service.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @ClassName FileServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/23 16:41
 * @Version 1.0
 **/
@Service
public class FileServiceImpl extends CommonService implements FileService {

    @Autowired
    private FileDao fileDao;

    private List<File> fileList;

    Map<String, Object> map = new HashMap<>();

    @Override
    public JsonBean findFile(Integer curr, File file, String session_area_code) {

        if (!StringUtils.isEmpty(file.getArea_code()) && !StringUtils.isEmpty(file.getUsername())) {
            fileList = fileDao.findFileByPage(curr, limit, file.getArea_code(), file.getUsername(), file.getPid());
            count = fileDao.findCountByPage(file.getArea_code(), file.getUsername(), file.getPid());

        } else if (!StringUtils.isEmpty(file.getArea_code())) {
            fileList = fileDao.findFileByPageAndCode(curr, limit, file.getArea_code(), file.getPid());
            count = fileDao.findCountByPage(file.getArea_code(), file.getPid());

        } else if (!StringUtils.isEmpty(file.getUsername())) {
            fileList = fileDao.findFileByPageAndName(curr, limit, file.getUsername(), file.getPid(), session_area_code);
            count = fileDao.findCountByPageAndName(file.getUsername(), file.getPid(), session_area_code);

        } else {
            fileList = fileDao.findAllFileByPage(curr, limit, file.getPid(), session_area_code);
            count = fileDao.findCountAllByPage(file.getPid(), session_area_code);
        }

        return new JsonBean(limit, curr, count, fileList);
    }


    @Override
    public boolean saveFile(File file) {
        if (file.getPid() == 0) { //保存家庭档案
            map.put("area_code", file.getArea_code());
            map.put("group_id", file.getGroup_id());
            map.put("pid", 0);

            Long codeCount = formatFamilyId(map);
            String family_id = file.getArea_code() + file.getGroup_id() + FormatUtil.formatStr("000", (codeCount + 1)); //生成家庭编号

            file.setFamily_id(family_id);
            file.setCreate_time(new Date());
        } else { //保存家庭成员
            fileList = findFileByFid(file.getPid());
            if (fileList != null && fileList.size() > 0) {
                File f;
                f = fileList.get(0);
                file.setArea_code(f.getArea_code());
                file.setGroup_id(f.getGroup_id());
                file.setFamily_id(f.getFamily_id());
            }
        }

        Integer rowResult = null;
        try {
            rowResult = ((CommonDao<File>) fileDao).saveT(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<File> findFileByFid(Integer fid) {
        return fileDao.findFileByFid(fid);
    }

    @Override
    public List findFileByIdentity(String identity, String session_area_code) {
        return fileDao.findFileByIdentity(identity, session_area_code);
    }

    @Override
    public JsonBean findFamilyByName(Integer curr, String username, String entity, String session_area_code) {
        fileList = fileDao.findFamilyByName(curr, limit, username, entity, session_area_code);
        count = fileDao.findFamilyCountByName(username, entity, session_area_code);
        return new JsonBean(limit, curr, count, fileList);
    }

    @Override
    public boolean updateFile(File file) {

        if (((CommonDao<File>) fileDao).updateT(file) > 0) {
            return true;
        }
        return false;

/*         fileList = findFileByFid(file.getFid());
            //若删除了一条记录，再重新插入一条，家庭编号会重复。
           for (File f: fileList) {
           if(!Objects.equals(file.getArea_code(), f.getArea_code()) || !Objects.equals(file.getGroup_id(), f.getGroup_id())){ //区域编号、组编号是否改变
                String codeCountStr = formatFamilyId("area_code", file.getArea_code(),"group_id", file.getGroup_id(), "pid", 0);
                String family_id = file.getArea_code() + file.getGroup_id() + codeCountStr; //生成家庭编号
                file.setFamily_id(family_id);
           }else{
               file.setFamily_id(f.getFamily_id());
           }
        }

        Long codeCount = formatFamilyId("area_code", file.getArea_code(),"group_id", file.getGroup_id(), "pid", 0);

        String family_id = file.getArea_code() + file.getGroup_id() + FormatUtil.formatStr("000", (codeCount - 1)); //生成家庭编号
        file.setFamily_id(family_id);
*/
    }

    @Override
    public void deleteBatchOrSingle(String checkids, Integer pid) {
        List<Integer> fid = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(File.class, "fid", fid);
        fileDao.deleteBatchOrSingle(detachedCriteria);

        if (pid == 0) {
            detachedCriteria = DetachedCriteriaUtil.setIn(File.class, "pid", fid); //删除pid为fid的成员
            fileDao.deleteBatchOrSingle(detachedCriteria);
        }
    }



    private Long formatFamilyId(Map<String, Object> map) {
        detachedCriteria = DetachedCriteriaUtil.setRequirePlus(File.class, map);
        return fileDao.findCountByAreaCode(detachedCriteria); //统计该区域该组内的档案数
    }


}
