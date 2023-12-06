package com.aceteam.tm.user.service.scheduler;

import com.acteam.tm.user.facade.server.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description: some desc
 * @author: haoran
 */
@Component
@Slf4j
public class UserDynamicWorker {
    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private RedissonClient redissonClient;

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void threadTask() {
        execute();
    }

    private void execute() {
        RLock lock = redissonClient.getFairLock("user_dynamic_worker" + UUID.randomUUID());

        try {
            boolean b = lock.tryLock();
            if (b) {
                // Update dynamic information for all users
                dynamicService.updateAll();
            }
        } catch (Exception e) {
            log.error("UserLevelWorker failed!", e);
        } finally {
            // Held by current thread and locked
            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
