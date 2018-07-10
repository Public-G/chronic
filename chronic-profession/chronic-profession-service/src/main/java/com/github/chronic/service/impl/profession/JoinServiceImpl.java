package com.github.chronic.service.impl.profession;

import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.profession.FileDao;
import com.github.chronic.dao.profession.JoinDao;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Join;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.profession.JoinService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.FormatUtil;
import com.github.chronic.service.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * @ClassName JoinServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/25 11:06
 * @Version 1.0
 **/
@Service
public class JoinServiceImpl extends CommonService implements JoinService{

    @Autowired
    private JoinDao joinDao;

    @Autowired
    private FileDao fileDao;


    @Override
    public boolean saveJoin(Integer jid) {

        List<File> fileByFid = fileDao.findFileByFid(jid);
        if(fileByFid == null || fileByFid.size() < 1){
            return false;
        }
        File file = fileByFid.get(0);

        //查询该家庭已有慢性病证号总数
        Long countFamilyid = joinDao.findCountByFamilyid(file.getFamily_id()) + 1;
        Random rand = new Random();
        //生成两次随机数
        int randIntNum = rand.nextInt(100) + 1;
        Long randLongNum = Long.valueOf(rand.nextInt(randIntNum));

        String join_id = file.getFamily_id() + FormatUtil.formatStr("00", randLongNum) + FormatUtil.formatStr("00", countFamilyid); //生成慢性病证号
        String invoice = UUIDUtil.getUUID();

        Join join = new Join();
        join.setFile(file);
        join.setJoin_id(join_id);
        join.setInvoice(invoice);
        join.setCreate_time(new Date());

        Integer rowResult = null;
        try {
            rowResult = ((CommonDao<Join>) joinDao).saveT(join);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public void deleteBatchOrSingle(Integer jid) {
        detachedCriteria = DetachedCriteriaUtil.setRequire(Join.class, "jid", jid);
        ((CommonDao<Join>) joinDao).deleteBatchOrSingle(detachedCriteria);
    }

}
