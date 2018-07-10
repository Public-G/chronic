package com.github.chronic.dao.profession;

import com.github.chronic.pojo.Join;

import java.util.List;

public interface JoinDao {
    List<Join> findJoinById(Integer jid);

    Long findCountByFamilyid(String family_id);

}
