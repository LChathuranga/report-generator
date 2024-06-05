package com.kingit.report_generator.utils;

import com.kingit.report_generator.entity.Report;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.UUID;

public class Utility {
    public static String generateReportId(){
        return UUID.randomUUID().toString();
    }

    public static String generateRandomString(int length){
        if(length < 1){
            throw new IllegalArgumentException("Length must greater than 0");
        }

        StringBuilder sb = new StringBuilder();
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(index));
        }
        return sb.toString();
    }

    public static void createTextFile(String content, String filePath, String fileName) throws IOException {
        File folder = new File(filePath);
        folder.mkdirs();
        File file = new File(folder, fileName);

        FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
    }

    public static String generateReportPath(Report report) {
        StringBuilder sb = new StringBuilder();
        sb.append("Start Date : ").append(report.getStartDate().toString()).append("\n");
        sb.append("End Date : ").append(report.getEndDate().toString()).append("\n");

        String content = generateRandomString(5);
        sb.append("Content : ").append(content);

        return sb.toString();
    }
}
