package com.lud.challenge.kodo.domain.robot.services.impl.mock;

import com.lud.challenge.kodo.domain.robot.repositories.RobotRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Profile("test")
@Configuration
public class RobotServiceImplTestMockConfig {

    @Bean
    @Primary
    public RobotRepository robotRepositoryMock() {
        return mock(RobotRepository.class);
    }
}