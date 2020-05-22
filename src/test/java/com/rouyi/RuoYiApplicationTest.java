package com.rouyi;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootTest
class RuoYiApplicationTest {

    public static void main(String[] args) {
        String str = "^[0-9*]+$";
        String str1 = "00";
        String str2 = "**";
        String str3 = "*.";
        String str4 = "1.0*1*1";
        System.out.println(str1.matches(str));
        System.out.println(str2.matches(str));
        System.out.println(str3.matches(str));
        System.out.println(str4.matches(str));
    }
}

