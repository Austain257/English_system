package com.austain.handler;

import com.austain.domain.po.Englishs;
import com.austain.mapper.EnglishMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

@Component
@SpringBootTest
public class WordHandler {

    @Autowired
    private EnglishMapper englishMapper;

    @Test
    public void handlerWord() throws Exception {
//        File file = new File("src/main/resources/static/836~1990word.txt");
        File file = new File("src/main/resources/static/1-4420word.txt");
        FileReader fr = new FileReader( file);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while ((line = br.readLine()) != null){
            String[] split = line.split("。");
//            System.out.println(split[0] + "。" + split[1] + "。" + split[2] + "." + split[3] + "。" + split[4]);
//            System.out.println(split[0] + "。" + split[1] + "。" + split[2] + "。" + split[3]);
//            System.out.println(Arrays.toString(split));


            Englishs englishs = new Englishs(split[0],split[1],split[2],split[3],"888");
            englishMapper.insert(englishs);
        }
    }



}
