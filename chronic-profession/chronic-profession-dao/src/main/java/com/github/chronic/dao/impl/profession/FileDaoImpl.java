package com.github.chronic.dao.impl.profession;

import com.github.chronic.dao.impl.common.CommonDaoImpl;
import com.github.chronic.dao.impl.util.HibernateUtil;
import com.github.chronic.dao.profession.FileDao;
import com.github.chronic.pojo.File;
import com.github.chronic.pojo.Menu;
import jdk.nashorn.internal.ir.EmptyNode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileDaoImpl
 * @Description
 * @Author ZEALER
 * @Date 2018/6/23 16:42
 * @Version 1.0
 **/
@Repository
public class FileDaoImpl extends CommonDaoImpl<File> implements FileDao {

    @Autowired
    private HibernateUtil hibernateUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    private String getSqlQueryListPage(String ... str){
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT f.fid, f.area_code, f.group_id, f.family_id, f.identity, f.username, f.sex, f.birth, f.address, f.cellphone, f.relation, f.create_time" +
                " FROM tb_file f WHERE area_code IN ( SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?)) )");
        for (int i = 0; i < str.length; i++) {
            sqlQuery.append(" AND " + str[i] + " = ? ");
        }
        sqlQuery.append(" AND is_delete = 0 LIMIT ?, ? ");
        return sqlQuery.toString();
    }

    private String getSqlQueryListPageCount(String ... str){
        StringBuffer sqlQuery = new StringBuffer();
        sqlQuery.append("SELECT count(*) FROM tb_file f WHERE area_code IN ( SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?)) )");
        for (int i = 0; i < str.length; i++) {
            sqlQuery.append(" AND " + str[i] + " = ? ");
        }

        sqlQuery.append(" AND is_delete = 0 ");
        return sqlQuery.toString();
    }


    @Override
    public void deleteBatchOrSingle(DetachedCriteria detachedCriteria) {
        Session session = hibernateUtil.getSession();
        List<File> list = detachedCriteria.getExecutableCriteria(session).list();
        for (File entity : list) {
            entity.setIs_delete(1); //1: 删除标志
            session.update(entity);
        }
    }



    @Override
    public List<File> findAllFileByPage(Integer curr, Integer limit, int pid, String session_area_code) {
        String sqlQuery = getSqlQueryListPage("pid");
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(File.class), session_area_code, pid, (curr - 1) * limit, limit);
    }

    @Override
    public Long findCountAllByPage(int pid, String session_area_code) {
        String sqlQuery = getSqlQueryListPageCount("pid");
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {session_area_code, pid}, Long.class);
    }

    @Override
    public List<File> findFileByPageAndName(Integer curr, Integer limit, String username, Integer pid, String session_area_code) {
        String sqlQuery = getSqlQueryListPage("username", "pid");
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(File.class), session_area_code, username, pid, (curr - 1) * limit, limit);
    }


    @Override
    public Long findCountByPageAndName(String username, int pid, String session_area_code) {
        String sqlQuery = getSqlQueryListPageCount("username", "pid");
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {session_area_code, username, pid}, Long.class);
    }



    @Override
    public Integer updateT(File entity) {
        if(entity.getRelation() != null){
            String hql = "update File set identity = ?, username = ?, sex = ?, birth = ?" +
                    ",address = ?, cellphone = ?, relation = ? where fid = ?";
            return hibernateUtil.getSession().createQuery(hql)
                    .setParameter(0, entity.getIdentity()).setParameter(1, entity.getUsername()).
                            setParameter(2, entity.getSex()).setParameter(3, entity.getBirth()).setParameter(4, entity.getAddress())
                    .setParameter(5, entity.getCellphone()).setParameter(6, entity.getRelation())
                    .setParameter(7, entity.getFid()).executeUpdate();
        }

        String hql = "update File set identity = ?, username = ?, sex = ?, birth = ?" +
                ",address = ?, cellphone = ?, create_time = ? where fid = ?";
        return hibernateUtil.getSession().createQuery(hql)
                .setParameter(0, entity.getIdentity()).setParameter(1, entity.getUsername()).
                        setParameter(2, entity.getSex()).setParameter(3, entity.getBirth()).setParameter(4, entity.getAddress())
                .setParameter(5, entity.getCellphone()).setParameter(6, entity.getCreate_time())
                .setParameter(7, entity.getFid()).executeUpdate();
    }

    @Override
    public List<File> findFileByPage(Integer curr, Integer limit, Integer pid) {
        String hql = "select new File(fid, area_code, group_id, family_id, identity, username, sex, birth, address, cellphone, relation, create_time) from File where pid = ? and is_delete = 0";
        return findT(hql, curr, limit, pid);

    }

    @Override
    public Long findCountByAreaCode(DetachedCriteria detachedCriteria) {
        return (Long) detachedCriteria.getExecutableCriteria(hibernateUtil.getSession())
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<File> findFileByFid(Integer fid) {
        String hql = "select new File(fid, area_code, group_id, family_id, identity, username, sex, birth, address, cellphone, relation, create_time) from File where fid = ? and is_delete = 0";
        return hibernateUtil.getSession().createQuery(hql).setParameter(0, fid).list();
    }


    @Override
    public List findFileByIdentity(String identity, String session_area_code) {
        String sqlQuery = "SELECT f.fid, f.identity, f.username, j.join_id FROM tb_file f " +
                "INNER JOIN tb_join j ON f.fid = j.jid " +
                "WHERE f.area_code IN(SELECT area_code FROM tb_file WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?))) " +
                "AND identity = ? AND is_delete = 0;";
        return hibernateUtil.getSession().createSQLQuery(sqlQuery).setParameter(0, session_area_code).setParameter(1, identity).list();
    }


    @Override
    public List<File> findFileByPage(Integer curr, Integer limit, String area_code, String username, Integer pid) {
        String sqlQuery = getSqlQueryListPage("username", "pid");
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(File.class), area_code, username, pid, (curr - 1) * limit, limit);
    }

    @Override
    public List<File> findFileByPageAndCode(Integer curr, Integer limit, String area_code, Integer pid) {
        String sqlQuery = getSqlQueryListPage("pid");
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(File.class), area_code, pid, (curr - 1) * limit, limit);
    }

    @Override
    public Long findCountByPage(String area_code, String username, Integer pid) {
        String sqlQuery = getSqlQueryListPageCount("username", "pid");
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {area_code, username, pid}, Long.class);

    }

    @Override
    public Long findCountByPage(String area_code, Integer pid) {
        String sqlQuery = getSqlQueryListPageCount("pid");
        return jdbcTemplate.queryForObject(sqlQuery, new Object[] {area_code, pid}, Long.class);

    }

    @Override
    public List<File> findFamilyByName(Integer curr, Integer limit, String username, String entity, String session_area_code) {
        String sqlQuery = null;
        String sql = null;
         List<File> fileList = null;
        if (entity.equals("join")){
            //查找在指定的家庭编号内的人员
            sql = "SELECT f.family_id FROM tb_file f " +
                    "WHERE area_code IN(SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?))) " +
                    "AND username = ? AND pid = 0 AND is_delete = 0";

            sqlQuery = "SELECT f.family_id, f.username, f.sex, f.identity, f.birth, f.address, f.cellphone, f.relation, f.fid, j.join_id, j.invoice, j.create_time" +
                    "   FROM tb_file f LEFT JOIN tb_join j ON f.fid = j.jid WHERE f.family_id IN ("+ sql +") AND is_delete = 0";

            fileList = hibernateUtil.getSession().createSQLQuery(sqlQuery).setParameter(0, session_area_code).setParameter(1, username)
                    .setFirstResult((curr - 1) * limit).setMaxResults(limit).list();

        } else if (entity.equals("proof")){
            sql = "SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?))";
            sqlQuery = "SELECT f.username, f.sex, f.identity, f.birth, f.address, f.cellphone, f.fid, j.join_id, pf.proof_id, pf.proof_name, pf.start_time, pf.end_time " +
                    "FROM tb_file f " +
                    "INNER JOIN tb_join j ON f.fid = j.jid " +
                    "LEFT JOIN tb_proof pf ON f.fid = pf.pfid" +
                    " WHERE f.area_code IN("
                    + sql +
                    ") AND username = ?  AND f.is_delete = 0";

            fileList = hibernateUtil.getSession().createSQLQuery(sqlQuery).setParameter(0, session_area_code).setParameter(1, username)
                    .setFirstResult((curr - 1) * limit).setMaxResults(limit).list();
        }
        return fileList;
    }

    @Override
    public Long findFamilyCountByName(String username, String entity, String session_area_code) {
        String sqlQuery = null;
        String sql = null;
        Long count = null;
        if (entity.equals("join")){
            sql = "SELECT f.family_id FROM tb_file f " +
                    "WHERE area_code IN(SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?))) " +
                    "AND username = ? AND pid = 0 AND is_delete = 0";
            sqlQuery = "SELECT count(*) FROM tb_file f LEFT JOIN tb_join j ON f.fid = j.jid WHERE f.family_id IN ("+ sql +") AND is_delete = 0";
            count = jdbcTemplate.queryForObject(sqlQuery, new Object[] {session_area_code, username}, Long.class);

        } else if (entity.equals("proof")){
            sql = "SELECT area_code FROM tb_area WHERE FIND_IN_SET(area_code, queryChildrenAreaInfo(?))";
            sqlQuery = "SELECT COUNT(*) FROM tb_file f " +
                    "INNER JOIN tb_join j ON f.fid = j.jid " +
                    "LEFT JOIN tb_proof pf ON f.fid = pf.pfid " +
                    "WHERE f.area_code IN("+ sql +")AND username = ?  AND f.is_delete = 0";
            count = jdbcTemplate.queryForObject(sqlQuery, new Object[] {session_area_code, username}, Long.class);
        }
        return count;
    }
}
