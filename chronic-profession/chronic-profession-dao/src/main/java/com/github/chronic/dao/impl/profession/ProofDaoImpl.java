package com.github.chronic.dao.impl.profession;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.profession.ProofDao;
import com.github.chronic.pojo.Proof;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ProofDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/26 10:21
 * @Version 1.0
 **/
@Repository
public class ProofDaoImpl extends CommonDaoImpl<Proof> implements ProofDao {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Integer updateT(Proof entity) {
        String hql = "update Proof set proof_name = ?, start_time = ?, end_time = ? where pfid = ?";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, entity.getProof_name())
                .setParameter(1, entity.getStart_time()).setParameter(2, entity.getEnd_time())
                .setParameter(3, entity.getPfid()).executeUpdate();
    }



    @Override
    public Proof findProofByPfid(Integer pfid) {
        String sqlQuery = "SELECT proof_name, start_time, end_time FROM tb_proof  WHERE pfid = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new BeanPropertyRowMapper<>(Proof.class), pfid);
    }

    @Override
    public List<Proof> findField(Integer pfid, String proof_id, String proof_name, Date medical_time) {
        String sqlQuery = "SELECT * FROM tb_proof WHERE pfid = ? AND proof_id = ? AND proof_name = ? AND ? BETWEEN start_time AND end_time; ";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Proof.class), pfid, proof_id, proof_name, medical_time);

    }
}
