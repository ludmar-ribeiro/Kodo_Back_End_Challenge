package com.lud.challenge.kodo.domain.robot.events.consumers;

import com.lud.challenge.kodo.domain.robot.entities.Robot;

import java.util.Map;

/**
 * Robot event consumer
 *
 * @author Lud Ribeiro
 */
public interface RobotEventConsumer {

    /**
     * Consumes a robot creation event
     *
     * @param robot
     */
    void consumeRobotCreationEvent(Robot robot);

    /**
     * Consumes a robot update event
     *
     * @param robot
     * @param oldAttributes
     * @param newAttributes
     */
    void consumeRobotUpdateEvent(Robot robot, Map<String, Object> oldAttributes, Map<String, Object> newAttributes);
}
