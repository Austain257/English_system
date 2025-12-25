package com.austain.srevice;

import com.austain.domain.dto.WordMasteryRequest;
import com.austain.domain.dto.WordMasterySummaryDTO;

import java.util.List;

public interface WordMasteryService {

    void markMastery(Long userId, WordMasteryRequest request);

    void regressMastery(Long userId, WordMasteryRequest request);

    List<WordMasterySummaryDTO> getSummary(Long userId, Long bookId);
}
