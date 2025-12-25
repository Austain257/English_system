package com.austain.srevice.impl;

import com.austain.domain.dto.LearningTimeSummaryDTO;
import com.austain.domain.dto.StudySessionEndRequest;
import com.austain.domain.dto.StudySessionStartRequest;
import com.austain.domain.po.StudySession;
import com.austain.mapper.StudySessionMapper;
import com.austain.srevice.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudySessionServiceImpl implements StudySessionService {

    @Autowired
    private StudySessionMapper studySessionMapper;

    @Override
    @Transactional
    public StudySession startSession(Long userId, StudySessionStartRequest request) {
        StudySession running = studySessionMapper.findRunningSession(userId);
        if (running != null) {
            studySessionMapper.markTimeout(running.getId());
        }
        StudySession session = new StudySession();
        session.setUserId(userId);
        session.setScene(normalizeScene(request.getScene()));
        session.setSource(normalizeSource(request.getSource()));
        session.setStartTime(LocalDateTime.now());
        session.setStatus("RUNNING");
        studySessionMapper.insertSession(session);
        return session;
    }

    @Override
    @Transactional
    public StudySession endSession(Long userId, StudySessionEndRequest request) {
        StudySession session = studySessionMapper.findById(request.getSessionId(), userId);
        if (session == null) {
            throw new IllegalArgumentException("会话不存在或已结束");
        }
        session.setEndTime(LocalDateTime.now());
        session.setDurationSeconds(calculateDuration(session, request.getDurationSeconds()));
        session.setStatus("COMPLETED");
        studySessionMapper.updateSession(session);
        return session;
    }

    private int calculateDuration(StudySession session, Integer clientDuration) {
        if (clientDuration != null && clientDuration > 0) {
            return clientDuration;
        }
        LocalDateTime end = session.getEndTime() != null ? session.getEndTime() : LocalDateTime.now();
        return (int) java.time.Duration.between(session.getStartTime(), end).getSeconds();
    }

    @Override
    public List<LearningTimeSummaryDTO> getSummary(Long userId) {
        return studySessionMapper.getLearningTimeSummary(userId);
    }

    private String normalizeScene(String scene) {
        return (scene == null || scene.isBlank()) ? "GENERIC" : scene.trim();
    }

    private String normalizeSource(String source) {
        return (source == null || source.isBlank()) ? "web" : source.trim();
    }
}
