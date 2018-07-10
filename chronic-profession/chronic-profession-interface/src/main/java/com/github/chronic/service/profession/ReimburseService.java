package com.github.chronic.service.profession;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.Reimburse;

import java.math.BigDecimal;

public interface ReimburseService {
    BigDecimal findCalculateAmount(String identity, BigDecimal medical_expenses);

    boolean saveReimburse(Reimburse reimburse, Location session_location, String session_job_id);

    JsonBean findReimburse(Integer curr, Reimburse reimburse, String areaCode, String session_area_code);

    void deleteBatchOrSingle(String checkids);
}
