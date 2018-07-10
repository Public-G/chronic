package com.github.chronic.service.impl.profession;

import com.github.chronic.bean.JsonBean;
import com.github.chronic.dao.profession.PolicyDao;
import com.github.chronic.dao.profession.ReimburseDao;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Location;
import com.github.chronic.pojo.Policy;
import com.github.chronic.pojo.Reimburse;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.profession.ReimburseService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.StringUtils;
import com.github.chronic.service.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ReimburseServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/28 18:12
 * @Version 1.0
 **/
@Service
public class ReimburseServiceImpl extends CommonService implements ReimburseService {

    @Autowired
    private ReimburseDao reimburseDao;

    @Autowired
    private PolicyDao policyDao;

    private List<Reimburse> reimburseList;


    @Override
    public BigDecimal findCalculateAmount(String identity, BigDecimal medical_expenses) {
        Policy policy = null;
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        List<Policy> policyList = policyDao.findPolicyByYear(year); //查询本年度报销比例等信息
        if(policyList != null && policyList.size() > 0){
             policy = policyList.get(0);
        }

        BigDecimal countAmount = reimburseDao.findCountAmount(identity); //查询该用户已报销金额
        if (countAmount == null) {
            countAmount = BigDecimal.valueOf(0);
        }


        BigDecimal multiply = null; //本次报销金额
        try {
            assert policy != null; //如果为false，抛出AssertionError，并终止执行
            if (countAmount.compareTo(policy.getCapline()) == 0) { //如果该用户已报销金额等于封顶线
                        return BigDecimal.valueOf(0); //此次报销金额为0
                    }

            multiply = medical_expenses.multiply(policy.getScale()).setScale(1, BigDecimal.ROUND_HALF_UP); //按照比例计算本次报销金额

            if (multiply.compareTo(policy.getCapline().subtract(countAmount)) >= 0) { //如果本次报销金额大于等于剩余报销金额
                return policy.getCapline().subtract(countAmount); //此次报销金额为剩余报销金额
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return multiply; //按报销比例照常报销
    }

    @Override
    public boolean saveReimburse(Reimburse reimburse, Location session_location, String session_job_id) {
        String reimburse_id = UUIDUtil.getUUID(); //报销单号

        String org_name = session_location.getOrg_name(); //经办机构

        String ip = null;
        try {
             ip = InetAddress.getLocalHost().getHostAddress(); //主机ip
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        reimburse.setReimburse_id(reimburse_id);
        reimburse.setOrg_name(org_name);
        reimburse.setJob_id(session_job_id);
        reimburse.setIp(ip);
        reimburse.setReimburse_time(new Date());

        Integer rowResult = reimburseDao.saveReimburse(reimburse);
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public JsonBean findReimburse(Integer curr, Reimburse reimburse, String areaCode, String session_area_code) {
        if (!StringUtils.isEmpty(areaCode) && !StringUtils.isEmpty(reimburse.getUsername())) {
            reimburseList = reimburseDao.findReimburseByPage(curr, limit, areaCode, reimburse.getUsername());
            count = reimburseDao.findCountByPage(areaCode, reimburse.getUsername());

        } else if (!StringUtils.isEmpty(areaCode)) {
            reimburseList = reimburseDao.findReimburseByPageAndCode(curr, limit, areaCode);
            count = reimburseDao.findCountByPageAndCode(areaCode);

        } else if (!StringUtils.isEmpty(reimburse.getUsername())) {
            reimburseList = reimburseDao.findReimburseByPageAndName(curr, limit, reimburse.getUsername(), session_area_code);
            count = reimburseDao.findCountByPageAndName(reimburse.getUsername(), session_area_code);

        } else {
            reimburseList = reimburseDao.findAllFileByPage(curr, limit, session_area_code);
            count = reimburseDao.findCountAllByPage(session_area_code);
        }
        return new JsonBean(limit, curr, count, reimburseList);
    }

    @Override
    public void deleteBatchOrSingle(String checkids) {
        List<Integer> rid = split(checkids);
        detachedCriteria = DetachedCriteriaUtil.setIn(Reimburse.class, "rid", rid);
        reimburseDao.deleteBatchOrSingle(detachedCriteria);
    }
}
