package com.lud.challenge.kodo.domain.robot.events.consumers.impl;

import com.lud.challenge.kodo.domain.robot.entities.Robot;
import com.lud.challenge.kodo.domain.robot.entities.RobotHistoryType;
import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import com.lud.challenge.kodo.domain.robot.events.consumers.impl.data.HistoryGeneratorRobotEventConsumerTestData;
import com.lud.challenge.kodo.domain.robot.services.RobotHistoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Unit Test for HistoryGeneratorRobotEventConsumer
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles({"test", "consumer-test"})
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:history-generator-robot-event-consumer-test.properties")
public class HistoryGeneratorRobotEventConsumerTest {

    @Autowired
    private RobotHistoryService service;

    @Autowired
    private RobotEventConsumer consumer;

    @Autowired
    private HistoryGeneratorRobotEventConsumerTestData data;

    @Before
    public void before() {
        reset(service);
    }

    /**
     * Given that a robot creation event was published
     * When the event is consumed
     * Then a robot creation history entry should be created
     */
    @Test
    public void shouldCreateRobotCreationHistory() {
        Robot createdRobot = Robot.of(data.getRobotCreationTestInput().toDomain());

        consumer.consumeRobotCreationEvent(createdRobot);

        verify(service).create(
                createdRobot.getId(),
                createdRobot.getCreatedAt(),
                RobotHistoryType.CREATE,
                Collections.emptyMap(),
                createdRobot.getAttributes()
        );
    }

    /**
     * Given that a robot update event was published
     * When the event is consumed
     * Then a robot update history entry should be created
     */
    @Test
    public void shouldCreateRobotUpdateHistory() {
        Robot createdRobot = Robot.of(data.getRobotCreationTestInput().toDomain());
        Map<String, Object> newAttributes = data.getRobotUpdateTestInput().get(0).getAttributes();
        Robot updatedRobot = createdRobot.update(newAttributes);

        consumer.consumeRobotUpdateEvent(updatedRobot, createdRobot.getAttributes(), newAttributes);

        verify(service).create(
                updatedRobot.getId(),
                updatedRobot.getUpdatedAt(),
                RobotHistoryType.UPDATE,
                createdRobot.getAttributes(),
                newAttributes
        );
    }
}
