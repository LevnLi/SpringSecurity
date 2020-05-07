package com.rouyi;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.common.util.MathUtils;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootTest
class RuoYiApplicationTest {

    public static void main(String[] args) {

        int c = 0;
        Long[] d = {1L,2L,3L};
        int a = d.length;

        for(int i = 0;i < 1;i ++){
            c++;
        }

        System.out.println(a>c);

    }
}

