package com.lud.challenge.kodo.resources.spring.events.listeners;

import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import com.lud.challenge.kodo.entities.Robot;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotCreationEvent;
import com.lud.challenge.kodo.resources.spring.events.publishers.data.RobotSpringEventPublisherTestData;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Unit Test for RobotCreationSpringEventListener
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles({"test", "spring-listener-test"})
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:robot-spring-event-listener-test.properties")
public class RobotCreationSpringEventListenerTest {

    @Autowired
    private RobotEventConsumer consumer;

    @Autowired
    private RobotCreationSpringEventListener springEventListener;

    @Autowired
    private RobotSpringEventPublisherTestData data;

    @Before
    public void before() {
        reset(consumer);
    }

    /**
     * Given a spring application event referent to robot creation
     * When listening to this event
     * Then the domain event consumer should consume such event
     */
    @Test
    public void shouldSendCreationEventToBeConsumed() {
        Robot createdRobot = Robot.of(data.getRobotCreationTestInput().toDomain());
        RobotCreationEvent event = new RobotCreationEvent(createdRobot);

        springEventListener.onApplicationEvent(event);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);
        verify(consumer).consumeRobotCreationEvent(captor.capture());

        Robot capturedCreatedRobot = captor.getValue();
        assertNotNull(capturedCreatedRobot);

        assertEquals(createdRobot, capturedCreatedRobot);
    }
}
