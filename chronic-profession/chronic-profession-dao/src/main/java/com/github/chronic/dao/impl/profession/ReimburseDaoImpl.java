package com.github.chronic.dao.impl.profession;

import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.profession.ReimburseDao;
import com.github.chronic.pojo.Reimburse;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName ReimburseDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/28 18:13
 * @Version 1.0
 **/
@Repository
public class ReimburseDaoImpl implements ReimburseDao {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String getBaseSql(){
        return "FROM tb_reimburse r WHERE r.`identity` IN (SELECT f.identity FROM tb_file f WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?)))";
    }


    @Override
    public BigDecimal findCountAmount(String identity) {
        String sqlQuery = "SELECT SUM(reimburse_amount) FROM tb_reimburse WHERE identity = ?";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[]{identity}, BigDecimal.class);
    }

    @Override
    public Integer saveReimburse(Reimburse reimburse) {
        return (Integer) hibernateUtil.getSession().save(reimburse);
    }

    @Override
    public List<Reimburse> findReimburseByPage(Integer curr, Integer limit, String areaCode, String username) {
        String sqlQuery = "SELECT r.* " + getBaseSql() + " AND r.username = ? AND is_delete = 0 LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Reimburse.class), areaCode, username, (curr - 1) * limit, limit);
    }

    @Override
    public Long findCountByPage(String areaCode, String username) {
        String sqlQuery = "SELECT count(*) " + getBaseSql() + " AND r.username = ? AND is_delete = 0";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {areaCode, username}, Long.class);
    }

    @Override
    public Long findCountByPageAndCode(String areaCode) {
        String sqlQuery = "SELECT count(*) " + getBaseSql() + " AND is_delete = 0";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {areaCode}, Long.class);
    }

    @Override
    public List<Reimburse> findReimburseByPageAndCode(Integer curr, Integer limit, String areaCode) {
        String sqlQuery = "SELECT r.* " + getBaseSql() + " AND is_delete = 0 LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Reimburse.class), areaCode, (curr - 1) * limit, limit);
    }

    @Override
    public List<Reimburse> findReimburseByPageAndName(Integer curr, Integer limit, String username, String session_area_code) {
        String sqlQuery = "SELECT r.* " + getBaseSql() + " AND r.username = ? AND is_delete = 0 LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Reimburse.class), session_area_code, username, (curr - 1) * limit, limit);
    }

    @Override
    public Long findCountByPageAndName(String username, String session_area_code) {
        String sqlQuery = "SELECT count(*) " + getBaseSql() + " AND r.username = ? AND is_delete = 0";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {session_area_code, username}, Long.class);
    }

    @Override
    public List<Reimburse> findAllFileByPage(Integer curr, Integer limit, String session_area_code) {
        String sqlQuery = "SELECT r.* " + getBaseSql() + " AND is_delete = 0 LIMIT ?, ?";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Reimburse.class), session_area_code, (curr - 1) * limit, limit);
    }

    @Override
    public Long findCountAllByPage(String session_area_code) {
        String sqlQuery = "SELECT count(*) " + getBaseSql() + " AND is_delete = 0";
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {session_area_code}, Long.class);
    }

    @Override
    public void deleteBatchOrSingle(DetachedCriteria detachedCriteria) {
        Session session = hibernateUtil.getSession();
        List<Reimburse> list = detachedCriteria.getExecutableCriteria(session).list();
        for (Reimburse entity : list) {
            entity.setIs_delete(1); //1: 删除标志
            session.update(entity);
        }
    }
}
