package com.austain.handler;

import com.austain.mapper.EnglishMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
@SpringBootTest
public class PronunciationReader {

    @Autowired
    private EnglishMapper englishMapper;

    @Test
    public void update( ) {
        String filePath = "src/main/resources/static/576单词英式音标.txt"; // 替换为你的文件路径
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过空行
                if (line.trim().isEmpty()) continue;
                
                // 按制表符分割行
                String[] parts = line.split("\t");
                
                if (parts.length >= 2) {
                    String word = parts[0].trim();
                    String pronounce = parts[1].trim();
                    
                    // 这里可以添加你的处理逻辑
                    englishMapper.updatePronounce(word, pronounce);
                }
            }
        } catch (IOException e) {
            System.err.println("读取文件时出错: " + e.getMessage());
        }
    }
}