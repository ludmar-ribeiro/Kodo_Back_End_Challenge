package com.lud.challenge.kodo.resources.spring.events.listeners;

import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import com.lud.challenge.kodo.entities.Robot;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotUpdateEvent;
import com.lud.challenge.kodo.resources.spring.events.publishers.data.RobotSpringEventPublisherTestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

/**
 * Unit Test for RobotUpdateSpringEventListener
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles({"test", "spring-listener-test"})
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:robot-spring-event-listener-test.properties")
public class RobotUpdateSpringEventListenerTest {

    @Autowired
    private RobotEventConsumer consumer;

    @Autowired
    private RobotUpdateSpringEventListener springEventListener;

    @Autowired
    private RobotSpringEventPublisherTestData data;

    @Before
    public void before() {
        reset(consumer);
    }

    /**
     * Given a spring application event referent to robot update
     * When listening to this event
     * Then the domain event consumer should consume such event
     */
    @Test
    @SuppressWarnings("unchecked")
    public void shouldSendUpdateEventToBeConsumed() {
        Robot createdRobot = Robot.of(data.getRobotCreationTestInput().toDomain());
        Map<String, Object> newAttributes = data.getRobotUpdateTestInput().get(0).getAttributes();
        Robot updatedRobot = createdRobot.update(newAttributes);

        RobotUpdateEvent event = new RobotUpdateEvent(updatedRobot, createdRobot.getAttributes(), newAttributes);

        springEventListener.onApplicationEvent(event);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);
        ArgumentCaptor<Map<String, Object>> oldAttributesCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, Object>> newAttributesCaptor = ArgumentCaptor.forClass(Map.class);

        verify(consumer).consumeRobotUpdateEvent(
                captor.capture(),
                oldAttributesCaptor.capture(),
                newAttributesCaptor.capture()
        );

        Robot capturedUpdatedRobot = captor.getValue();
        assertNotNull(capturedUpdatedRobot);

        Map<String, Object> capturedOldAttributes = oldAttributesCaptor.getValue();
        assertNotNull(capturedOldAttributes);

        Map<String, Object> capturedNewAttributes = newAttributesCaptor.getValue();
        assertNotNull(capturedNewAttributes);

        assertEquals(updatedRobot, capturedUpdatedRobot);
        assertEquals(createdRobot.getAttributes(), capturedOldAttributes);
        assertEquals(newAttributes, capturedNewAttributes);
    }
}
