package com.austain.srevice.impl;

import com.austain.domain.dto.PageResult;
import com.austain.domain.po.Jotting;
import com.austain.mapper.JottingMapper;
import com.austain.srevice.JottingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JottingServiceImpl implements JottingService {

    @Autowired
    private JottingMapper jottingMapper;

    @Override
    public PageResult<Jotting> getList(Integer page, Integer size) {
        PageHelper.startPage(page,size);  // 开启分页,其后必须紧接查询语句才有效
        List<Jotting> list = jottingMapper.page();  // 获取分页结果
        Page<Jotting> pageList = (Page<Jotting>) list;
        return new PageResult<>(pageList.getTotal(),pageList.getResult());
    }

    @Override
    public int addJotting(Jotting jotting) {
        return jottingMapper.addJotting(jotting);
    }

    @Override
    public int updateJotting(Jotting jotting) {
        return jottingMapper.updateJotting(jotting);
    }

    @Override
    public int deleteJotting(String id) {
        return jottingMapper.deleteJotting(id);
    }

    @Override
    public int batchDeleteJotting(List<String> ids) {
        return jottingMapper.batchDeleteJotting(ids);
    }
}
