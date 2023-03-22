package com.koreaIT.example.JAM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public class Util {
    public static String getNotDateTimeStr() {
        // 현재 날짜/시간
        LocalDateTime now = LocalDateTime.now();
        // 포맷팅
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formatedNow;
    }
    
    public static String getNotDateTimeStr(LocalDateTime DateTime) {
    	
        String formatedDateTime = DateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        return formatedDateTime;
    }
}