package com.github.chronic.dao.profession;

import com.github.chronic.pojo.Reimburse;
import org.hibernate.criterion.DetachedCriteria;

import java.math.BigDecimal;
import java.util.List;

public interface ReimburseDao {
    BigDecimal findCountAmount(String identity);

    Integer saveReimburse(Reimburse reimburse);

    List<Reimburse> findReimburseByPage(Integer curr, Integer limit, String areaCode, String username);

    Long findCountByPage(String areaCode, String username);

    Long findCountByPageAndCode(String areaCode);

    List<Reimburse> findReimburseByPageAndCode(Integer curr, Integer limit, String areaCode);

    List<Reimburse> findReimburseByPageAndName(Integer curr, Integer limit, String username, String session_area_code);

    Long findCountByPageAndName(String username, String session_area_code);


    List<Reimburse> findAllFileByPage(Integer curr, Integer limit, String session_area_code);

    Long findCountAllByPage(String session_area_code);


    void deleteBatchOrSingle(DetachedCriteria detachedCriteria);

}
