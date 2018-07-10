package com.github.chronic.service.impl.profession;

import com.github.chronic.dao.common.CommonDao;
import com.github.chronic.dao.profession.FileDao;
import com.github.chronic.dao.profession.ProofDao;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Join;
import com.github.chronic.pojo.Proof;
import com.github.chronic.service.impl.common.CommonService;
import com.github.chronic.service.profession.ProofService;
import com.github.chronic.service.util.DetachedCriteriaUtil;
import com.github.chronic.service.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ProofServiceImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 10:21
 * @Version 1.0
 **/
@Service
public class ProofServiceImpl extends CommonService implements ProofService {

    @Autowired
    private ProofDao proofDao;

    @Autowired
    private FileDao fileDao;

    @Override
    public boolean saveProof(Proof proof) {

        List<File> fileByFid = fileDao.findFileByFid(proof.getPfid());
        if(fileByFid == null || fileByFid.size() < 1){
            return false;
        }
        File file = fileByFid.get(0);

        String proof_id = UUIDUtil.generateShortUUID();
        proof.setProof_id(proof_id);
        proof.setFile(file);

        Integer rowResult = null;
        try {
            rowResult = ((CommonDao<Proof>) proofDao).saveT(proof);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rowResult != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateProof(Proof proof) {
        if (((CommonDao<Proof>) proofDao).updateT(proof) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Proof findProofByPfid(Integer pfid) {
        return proofDao.findProofByPfid(pfid);
    }

    @Override
    public void deleteBatchOrSingle(Integer pfid) {
        detachedCriteria = DetachedCriteriaUtil.setRequire(Proof.class, "pfid", pfid);
        ((CommonDao<Proof>) proofDao).deleteBatchOrSingle(detachedCriteria);
    }

    /**
    * @Description 慢病报销校验
    * @Param [fid, proof_id, proof_name]
    * @return boolean
    **/
    @Override
    public boolean findField(Integer fid, String proof_id, String proof_name,  Date medical_time) {
        List<Proof> proofList = proofDao.findField(fid, proof_id, proof_name, medical_time);
        if( proofList != null && proofList.size() > 0) {
            return true;
        }
        return false;
    }
}
