package com.rouyi;

import com.ruoyi.common.utils.DateUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootTest
class RuoYiApplicationTest {

    public static void main(String[] args) {

        DateUtils dateUtils = new DateUtils();
        System.out.println(dateUtils.getTime());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
