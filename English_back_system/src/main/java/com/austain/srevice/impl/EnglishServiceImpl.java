package com.austain.srevice.impl;

import com.austain.domain.dto.WrongbookPageResponse;
import com.austain.domain.po.AddRequest;
import com.austain.domain.po.Englishs;
import com.austain.domain.po.Sentence;
import com.austain.mapper.EnglishMapper;
import com.austain.srevice.EnglishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    // 错词本相关方法实现
    @Override
    public List<Englishs> getAllAgainWords() {
        return englishMapper.getAllAgainWords();
    }

    @Override
    public List<Englishs> getAgainWordsByBook(String bookname) {
        return englishMapper.getAgainWordsByBook(bookname);
    }

    @Override
    public List<Englishs> getAgainWordsByTimes(int minTimes) {
        return englishMapper.getAgainWordsByTimes(minTimes);
    }

    @Override
    public boolean increaseWordTimes(Long id) {
        int result = englishMapper.increaseWordTimes(id);
        return result > 0;
    }

    @Override
    public List<String> getAgainWordBooks() {
        return englishMapper.getAgainWordBooks();
    }

    // 用户相关的错词方法实现
    @Override
    public List<Englishs> getAgainWordsByUserId(Long userId) {
        return englishMapper.getAgainWordsByUserId(userId);
    }

    @Override
    public List<Englishs> getAgainWordsByBookAndUserId(String bookname, Long userId) {
        return englishMapper.getAgainWordsByBookAndUserId(bookname, userId);
    }

    @Override
    public List<Englishs> getAgainWordsByTimesAndUserId(int minTimes, Long userId) {
        return englishMapper.getAgainWordsByTimesAndUserId(minTimes, userId);
    }

    @Override
    public List<String> getAgainWordBooksByUserId(Long userId) {
        return englishMapper.getAgainWordBooksByUserId(userId);
    }

    @Override
    public boolean isWordBelongsToUser(Long wordId, Long userId) {
        return englishMapper.isWordBelongsToUser(wordId, userId);
    }

    @Override
    public WrongbookPageResponse getWrongbookPage(Integer page, Integer size, Long currentUserId, String currentUserRole, String bookname, Integer minTimes) {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(currentUserRole);
        PageHelper.startPage(page, size);
        List<Englishs> list = englishMapper.pageWrongbook(currentUserId, isAdmin, bookname, minTimes);
        Page<Englishs> pageList = (Page<Englishs>) list;

        long total = pageList.getTotal();
        long frequentCount = englishMapper.countFrequentWrongbook(currentUserId, isAdmin, bookname);
        long bookCount = englishMapper.countBookWrongbook(currentUserId, isAdmin, bookname);
        long todayCount = englishMapper.countTodayWrongbook(currentUserId, isAdmin, bookname);

        return new WrongbookPageResponse(total, pageList.getResult(), frequentCount, bookCount, todayCount);
    }
}
