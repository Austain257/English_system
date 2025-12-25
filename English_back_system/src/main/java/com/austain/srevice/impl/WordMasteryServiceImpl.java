package com.austain.srevice.impl;

import com.austain.domain.dto.WordMasteryRequest;
import com.austain.domain.dto.WordMasterySummaryDTO;
import com.austain.domain.po.UserBook;
import com.austain.domain.po.WordMastery;
import com.austain.mapper.WordMasteryMapper;
import com.austain.srevice.IndexListService;
import com.austain.srevice.WordMasteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordMasteryServiceImpl implements WordMasteryService {

    @Autowired
    private WordMasteryMapper wordMasteryMapper;

    @Autowired
    private IndexListService indexListService;

    @Override
    @Transactional
    public void markMastery(Long userId, WordMasteryRequest request) {
        Long resolvedBookId = resolveBookId(userId, request);
        WordMastery mastery = new WordMastery();
        mastery.setUserId(userId);
        mastery.setBookId(resolvedBookId);
        mastery.setWordId(request.getWordId());
        mastery.setWordText(request.getWordText());
        mastery.setWordBank(request.getWordBank());
        mastery.setProficiencyScore(request.getProficiencyScore());
        mastery.setMastered(Boolean.TRUE.equals(request.getMastered()));
        mastery.setFirstMasteredTime(LocalDateTime.now());
        mastery.setLastMasteredTime(LocalDateTime.now());
        mastery.setReviewCount(1);
        mastery.setRegressionCount(0);
        wordMasteryMapper.upsert(mastery);
    }

    @Override
    @Transactional
    public void regressMastery(Long userId, WordMasteryRequest request) {
        Long resolvedBookId = resolveBookId(userId, request);
        wordMasteryMapper.regress(userId, resolvedBookId, request.getWordId(), request.getWordText());
    }

    @Override
    public List<WordMasterySummaryDTO> getSummary(Long userId, Long bookId) {
        List<WordMasterySummaryDTO> summaries = wordMasteryMapper.getSummary(userId);
        if (bookId == null) {
            return summaries;
        }
        return summaries.stream()
                .filter(dto -> dto.getBookId() != null && dto.getBookId().equals(bookId))
                .collect(Collectors.toList());
    }

    private Long resolveBookId(Long userId, WordMasteryRequest request) {
        if (request.getBookId() != null) {
            return request.getBookId();
        }
        if (request.getBookName() == null || request.getBookName().isEmpty()) {
            throw new IllegalArgumentException("缺少课本信息，无法记录掌握状态");
        }
        UserBook book = indexListService.findBook(userId, false, null, request.getBookName());
        if (book == null) {
            throw new IllegalArgumentException("未找到对应课本：" + request.getBookName());
        }
        request.setBookId(book.getId());
        request.setBookName(book.getBookName());
        request.setBookCode(book.getBookCode());
        return book.getId();
    }
}
