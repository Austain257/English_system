package com.austain.srevice;

import com.austain.domain.dto.PageResult;
import com.austain.domain.po.Jotting;

import java.util.List;

public interface JottingService {
    PageResult<Jotting> getList(Integer page, Integer size);

    int addJotting(Jotting jotting);

    int updateJotting(Jotting jotting);

    int deleteJotting(String id);

    int batchDeleteJotting(List<String> ids);
}
