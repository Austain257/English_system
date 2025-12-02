package com.austain;

import com.austain.srevice.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EnglishAndWordApplicationTests {

    @Autowired
    private TaskService taskService;

    @Test
    void contextLoads() {
    }

    @Test
    void testTaskService() {
        taskService.resetScoreAtMidnight();
    }
}
