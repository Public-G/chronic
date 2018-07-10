package com.github.chronic.service.profession;



public interface JoinService {

    boolean saveJoin(Integer jid);

    void deleteBatchOrSingle(Integer jid);
}
