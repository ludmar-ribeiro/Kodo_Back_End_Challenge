package com.lud.challenge.kodo.domain.robot.events.producers;

import com.lud.challenge.kodo.domain.robot.entities.Robot;

import java.util.Map;

/**
 * Robot event producer
 *
 * @author Lud Ribeiro
 */
public interface RobotEventProducer {


    /**
     * Publishes a robot creation event
     *
     * @param robot
     */
    void publishRobotCreatedEvent(Robot robot);

    /**
     * Publishes a robot update event
     *
     * @param robot
     * @param oldAttributes
     * @param newAttributes
     */
    void publishRobotUpdatedEvent(Robot robot, Map<String, Object> oldAttributes, Map<String, Object> newAttributes);
}
