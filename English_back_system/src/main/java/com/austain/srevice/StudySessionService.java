package com.austain.srevice;

import com.austain.domain.dto.LearningTimeSummaryDTO;
import com.austain.domain.dto.StudySessionEndRequest;
import com.austain.domain.dto.StudySessionStartRequest;
import com.austain.domain.po.StudySession;

import java.util.List;

public interface StudySessionService {

    StudySession startSession(Long userId, StudySessionStartRequest request);

    StudySession endSession(Long userId, StudySessionEndRequest request);

    List<LearningTimeSummaryDTO> getSummary(Long userId);
}
