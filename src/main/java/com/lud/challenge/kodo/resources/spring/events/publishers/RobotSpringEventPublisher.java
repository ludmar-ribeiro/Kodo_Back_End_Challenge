package com.lud.challenge.kodo.resources.spring.events.publishers;

import com.lud.challenge.kodo.domain.robot.entities.Robot;
import com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotCreationEvent;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * SpringEventPublisher for robot event
 *
 * @see com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer
 * @author Lud Ribeiro
 */
@Component
public class RobotSpringEventPublisher implements RobotEventProducer {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Publishes a robot creation event
     *
     * @see com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer#publishRobotCreationEvent(Robot)
     * @param robot
     */
    @Override
    public void publishRobotCreationEvent(Robot robot) {
        RobotCreationEvent event = new RobotCreationEvent(robot);

        publisher.publishEvent(event);
    }

    /**
     * Publishes a robot update event
     *
     * @see com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer#publishRobotUpdateEvent(Robot, Map, Map)
     * @param robot
     * @param oldAttributes
     * @param newAttributes
     */
    @Override
    public void publishRobotUpdateEvent(Robot robot, Map<String, Object> oldAttributes, Map<String, Object> newAttributes) {
        RobotUpdateEvent event = new RobotUpdateEvent( robot, oldAttributes, newAttributes);

        publisher.publishEvent(event);
    }
}
