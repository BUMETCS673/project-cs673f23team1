package com.aceteam.tm.user.service.scheduler;

import com.acteam.tm.user.facade.server.UserLevelService;
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
public class UserLevelWorker {
    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private RedissonClient redissonClient;

    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0/30 * * * * ?")
    public void threadTask() {
        execute();
    }

    private void execute() {
        RLock lock = redissonClient.getFairLock("user_level_points_worker" + UUID.randomUUID());

        try {
            boolean b = lock.tryLock();
            if (b) {
                // Update level information for all users
                userLevelService.updatePointsAll();
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

    /**
     * Performed every 1 minute
     */
    @Async("asyncTaskExecutor")
    @Scheduled(cron = "0 * * * * ?")
    public void threadTask2() {
        executeNull();
    }

    private void executeNull() {
        RLock lock = redissonClient.getFairLock("user_level_null_worker" + UUID.randomUUID());

        try {
            boolean b = lock.tryLock();
            if (b) {
                // Synchronize level information for all users
                userLevelService.syncAll();
            }
        } catch (Exception e) {
            log.error("UserLevelWorker > executeNull failed!", e);
        } finally {
            // Held by current thread and locked
            if (lock.isHeldByCurrentThread() && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
