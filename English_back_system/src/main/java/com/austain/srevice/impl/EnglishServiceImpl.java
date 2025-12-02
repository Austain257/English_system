package com.austain.srevice.impl;

import com.austain.domain.po.AddRequest;
import com.austain.domain.po.Englishs;
import com.austain.domain.po.Sentence;
import com.austain.mapper.EnglishMapper;
import com.austain.srevice.EnglishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class EnglishServiceImpl implements EnglishService {

    @Autowired
    private EnglishMapper englishMapper;
    @Override
    public List<Englishs> getEnglishList(String startIndex,String endIndex,String bookName) {
       List<Englishs> englishs = englishMapper.getEnglishList(startIndex, endIndex, bookName);

       Collections.shuffle(englishs);
       return englishs;
    }

    @Override
    public boolean removeAgainWord(AddRequest request) {
        int result = englishMapper.removeAgainWord(request);
        return result > 0;
    }

    @Override
    public boolean FinalAddAgainWord(AddRequest request) {
        int result = englishMapper.finalAddAgainWord(request);
        return result > 0;
    }

    @Override
    public List<Sentence> getSentenceList(String start, String end) {
        List<Sentence> sentenceList = englishMapper.getSentenceList(start, end);

        Collections.shuffle(sentenceList);
        return sentenceList;
    }

    @Override
    public boolean addAgainWord(AddRequest request) {
        int result = englishMapper.addAgainWord(request);
        return result > 0;
    }
}
