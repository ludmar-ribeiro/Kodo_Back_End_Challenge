package com.lud.challenge.kodo.domain.robot.services.impl.data;

import com.lud.challenge.kodo.domain.robot.entities.MutableRobot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("test")
@Configuration
@ConfigurationProperties("test-data")
@Getter
@Setter
public class RobotServiceImplTestData {
    private MutableRobot robotCreationTestInput;
    private List<MutableRobot> robotUpdateTestInput;
}
