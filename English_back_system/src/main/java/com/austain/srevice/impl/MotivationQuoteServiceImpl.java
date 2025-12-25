package com.austain.srevice.impl;

import com.austain.domain.po.MotivationQuote;
import com.austain.mapper.MotivationQuoteMapper;
import com.austain.srevice.MotivationQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotivationQuoteServiceImpl implements MotivationQuoteService {

    @Autowired
    private MotivationQuoteMapper motivationQuoteMapper;

    @Override
    public MotivationQuote getRandomQuote() {
        return motivationQuoteMapper.pickOne();
    }
}
