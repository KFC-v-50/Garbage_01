package com.example;

import com.example.utils.ScheduleUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdministrationSchoolApplicationTests {

    /**
     * 测试日期映射函数
     */
    @Test
    void scheduleTest1(){
        // 示例：多个date和time组合
        Integer date = 123;
        Integer time = 123;
        String combinedScheduleString = ScheduleUtils.schedule(date, time);
        System.out.println(combinedScheduleString);
    }

    @Test
    void scheduleTest2(){
        // 示例：多个date和time组合
        Integer date = 12;
        Integer time = 12;
        String combinedScheduleString = ScheduleUtils.scheduleTable(date, time);
        System.out.println(combinedScheduleString);
    }

}
