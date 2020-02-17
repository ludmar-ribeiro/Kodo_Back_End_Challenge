package com.lud.challenge.kodo.domain.robot.services.impl.configs.mock;

import com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Profile("service-test")
@Configuration
public class ServiceMockConfig {
    @Bean
    @Primary
    public RobotEventProducer robotEventProducer() { return mock(RobotEventProducer.class); }
}
