package com.lud.challenge.kodo.resources.spring.events.publishers;

import com.lud.challenge.kodo.entities.Robot;
import com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotCreationEvent;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotUpdateEvent;
import com.lud.challenge.kodo.resources.spring.events.publishers.data.RobotSpringEventPublisherTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Unit Test for RobotSpringEventPublisherTest
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:robot-spring-event-publisher-test.properties")
public class RobotSpringEventPublisherTest {

    @Autowired
    private ApplicationEventPublisher springEventPublisher;

    @Autowired
    private RobotEventProducer robotEventProducer;

    @Autowired
    private RobotSpringEventPublisherTestData data;

    @Before
    public void before() { reset( springEventPublisher ); }

    /**
     * Given a robot creation event
     * When publishing this event
     * Then a equivalent event should be published to spring application context
     */
    @Test
    public void shouldPublishRobotCreationEvent() {
        Robot createdRobot = Robot.of(data.getRobotCreationTestInput().toDomain());

        robotEventProducer.publishRobotCreationEvent(createdRobot);

        ArgumentCaptor<RobotCreationEvent> captor = ArgumentCaptor.forClass(RobotCreationEvent.class);

        verify(springEventPublisher).publishEvent(captor.capture());

        RobotCreationEvent event = captor.getValue();
        assertNotNull(event);
        assertEquals(createdRobot, event.getRobot());
    }

    /**
     * Given a robot update event
     * When publishing this event
     * Then a equivalent event should be published to spring application context
     */
    @Test
    public void shouldPublishRobotUpdateEvent() {
        Robot createdRobot = Robot.of(data.getRobotCreationTestInput().toDomain());
        Map<String, Object> newAttributes = data.getRobotUpdateTestInput().get(0).getAttributes();
        Robot updatedRobot = createdRobot.update(newAttributes);

        robotEventProducer.publishRobotUpdateEvent(updatedRobot, createdRobot.getAttributes(), newAttributes);

        ArgumentCaptor<RobotUpdateEvent> captor = ArgumentCaptor.forClass(RobotUpdateEvent.class);

        verify(springEventPublisher).publishEvent(captor.capture());

        RobotUpdateEvent event = captor.getValue();
        assertNotNull(event);
        assertEquals(updatedRobot, event.getRobot());
        assertEquals(createdRobot.getAttributes(), event.getOldAttributes());
        assertEquals(newAttributes, event.getNewAttributes());
    }
}
