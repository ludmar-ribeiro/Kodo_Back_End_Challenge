package com.lud.challenge.kodo.resources.spring.events.listeners.configs.mock;

import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("spring-listener-test")
public class ListenerMockConfig {

    @Bean
    @Primary
    public RobotEventConsumer robotEventConsumer() { return mock(RobotEventConsumer.class); }
}
