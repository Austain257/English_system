package com.austain.handler;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {

    public static void main(String[] args) {
        String filePath = "src/main/resources/static/【星星·独家资料包】英语四级常用576词.pdf"; // 请根据实际情况调整文件路径

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            String[] lines = text.split("\n");
            for (String line : lines) {
                processLine(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line) {
        // 使用正则表达式匹配需要的信息
        Pattern pattern = Pattern.compile("(\\d+)\\.\\s*(\\S+)\\s*/([^/]+)/\\s*(.*)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String index = matcher.group(1);
            String word = matcher.group(2);
            String phonetic = matcher.group(3);
            String meanings = matcher.group(4).replaceAll("；", ";"); // 将中文分号替换为英文分号

            System.out.printf("%s；%s；/%s/；%s；666%n", index, word, phonetic, meanings);
//            System.out.printf("%s\n", word);
        }
    }
}