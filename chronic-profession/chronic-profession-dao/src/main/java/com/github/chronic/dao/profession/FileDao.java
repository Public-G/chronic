package com.github.chronic.dao.profession;

import com.github.chronic.pojo.File;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface FileDao {
    List<File> findFileByPage(Integer curr, Integer limit, Integer pid);

    List<File> findFileByPage(Integer curr, Integer limit, String area_code, String username, Integer pid);

    List<File> findFileByPageAndCode(Integer curr, Integer limit, String area_code, Integer pid);

    List<File> findFileByPageAndName(Integer curr, Integer limit, String username, Integer pid, String session_area_code);

    List<File> findFamilyByName(Integer curr, Integer limit, String username, String entity, String session_area_code);

    Long findCountByAreaCode(DetachedCriteria detachedCriteria);

    List<File> findFileByFid(Integer fid);

    Long findCountByPage(String area_code, String username, Integer pid);

    Long findCountByPage(String area_code,  Integer pid);


    Long findFamilyCountByName(String username, String entity, String session_area_code);

    void deleteBatchOrSingle(DetachedCriteria detachedCriteria);

    List<File> findAllFileByPage(Integer curr, Integer limit, int pid, String session_area_code);

    Long findCountAllByPage(int pid, String session_area_code);


    Long findCountByPageAndName(String username, int pid, String session_area_code);

    List findFileByIdentity(String identity, String session_area_code);
}
