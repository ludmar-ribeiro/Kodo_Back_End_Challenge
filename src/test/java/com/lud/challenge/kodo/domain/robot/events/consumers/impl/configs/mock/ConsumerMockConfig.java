package com.lud.challenge.kodo.domain.robot.events.consumers.impl.configs.mock;

import com.lud.challenge.kodo.domain.robot.services.RobotHistoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("consumer-test")
public class ConsumerMockConfig {

    @Bean
    @Primary
    public RobotHistoryService robotHistoryService() { return mock(RobotHistoryService.class); }
}
