package com.rouyi;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.common.util.SeqGeneratorUtil;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootTest
class RuoYiApplicationTest {

    public static void main(String[] args) {

        System.out.println(SeqGeneratorUtil.seqGenerator(DateUtils.getNowDateStr(),6));

    }
}

