package com.app.mdc.serviceImpl.scheduler;

import com.app.mdc.service.scheduler.TaskSchedulerService;
import org.springframework.stereotype.Service;

@Service("taskSchedulerService")
public class TaskSchedulerServiceImpl implements TaskSchedulerService {

    @Override
    public void testTask(String testTask) {
        System.out.println(testTask);
    }
}
