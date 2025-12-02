package com.austain.srevice;

import com.austain.domain.po.AddRequest;
import com.austain.domain.po.Englishs;
import com.austain.domain.po.Sentence;

import java.util.List;

public interface EnglishService {
    List<Englishs> getEnglishList(String startIndex,String endIndex,String bookName);

    boolean addAgainWord(AddRequest request);

    boolean removeAgainWord(AddRequest request);

    List<Sentence> getSentenceList(String start, String end);

    boolean FinalAddAgainWord(AddRequest request);
}
