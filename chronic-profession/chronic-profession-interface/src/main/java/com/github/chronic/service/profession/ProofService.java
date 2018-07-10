package com.github.chronic.service.profession;

import com.github.chronic.pojo.Proof;

import java.util.Date;

public interface ProofService {

    boolean saveProof(Proof proof);

    boolean updateProof(Proof proof);

    Proof findProofByPfid(Integer pfid);

    void deleteBatchOrSingle(Integer pfid);

    boolean findField(Integer pfid, String proof_id, String proof_name, Date medical_time);
}
