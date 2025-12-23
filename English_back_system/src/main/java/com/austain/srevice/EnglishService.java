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

    // 错词本相关接口
    List<Englishs> getAllAgainWords();
    
    List<Englishs> getAgainWordsByBook(String bookname);
    
    List<Englishs> getAgainWordsByTimes(int minTimes);
    
    boolean increaseWordTimes(Long id);
    
    List<String> getAgainWordBooks();

    // 用户相关的错词接口
    List<Englishs> getAgainWordsByUserId(Long userId);
    
    List<Englishs> getAgainWordsByBookAndUserId(String bookname, Long userId);
    
    List<Englishs> getAgainWordsByTimesAndUserId(int minTimes, Long userId);
    
    List<String> getAgainWordBooksByUserId(Long userId);
    
    boolean isWordBelongsToUser(Long wordId, Long userId);
}
