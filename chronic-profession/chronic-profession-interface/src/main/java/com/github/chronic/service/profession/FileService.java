package com.github.chronic.service.profession;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.File;

import java.util.List;

public interface FileService {
    JsonBean findFile(Integer curr, File file, String session_area_code);

    boolean saveFile(File file);

    List<File> findFileByFid(Integer fid);

    List findFileByIdentity(String identity, String session_area_code);

    JsonBean findFamilyByName(Integer curr,String username, String entity, String session_area_code);

    boolean updateFile(File file);

    void deleteBatchOrSingle(String checkids, Integer pid);

}
