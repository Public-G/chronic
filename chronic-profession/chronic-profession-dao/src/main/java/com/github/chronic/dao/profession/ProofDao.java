package com.github.chronic.dao.profession;

import com.github.chronic.pojo.Proof;

import java.util.Date;
import java.util.List;

public interface ProofDao {

    Proof findProofByPfid(Integer pfid);

    List<Proof> findField(Integer pfid, String proof_id, String proof_name, Date medical_time);
}
