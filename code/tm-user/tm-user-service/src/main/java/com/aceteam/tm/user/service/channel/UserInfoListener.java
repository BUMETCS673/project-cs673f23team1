package com.aceteam.tm.user.service.channel;

import com.acteam.tm.user.facade.server.UserLevelService;
import com.alibaba.fastjson.JSON;
import com.liang.manage.auth.facade.dto.user.UserTokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @description: some desc
 * @author: haoran
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "${rocketmq.user.topic}", consumerGroup = "${rocketmq.consumer.group}")
public class UserInfoListener implements RocketMQListener<String> {
    @Reference
    private UserLevelService userLevelService;

    @Override
    public void onMessage(String message) {
        log.info("Message received. > UserTokenDTO: {}", message);
        // Create user level information
        userLevelService.create(JSON.parseObject(message, UserTokenDTO.class).getUserId());
    }
}
