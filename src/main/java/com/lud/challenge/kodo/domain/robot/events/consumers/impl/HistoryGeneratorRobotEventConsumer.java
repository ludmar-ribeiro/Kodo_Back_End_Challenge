package com.lud.challenge.kodo.domain.robot.events.consumers.impl;

import com.lud.challenge.kodo.entities.RobotHistoryType;
import com.lud.challenge.kodo.entities.Robot;
import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import com.lud.challenge.kodo.domain.robot.services.RobotHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * Robot event consumer that generates robot history entry
 *
 * @see com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer
 * @author Lud Ribeiro
 */
@Component
public class HistoryGeneratorRobotEventConsumer implements RobotEventConsumer {

    /**
     * Robot History service
     */
    @Autowired
    private RobotHistoryService service;

    /**
     * Consumes a robot creation event
     *
     * @see com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer#consumeRobotCreationEvent(Robot)
     * @param robot
     */
    @Override
    public void consumeRobotCreationEvent(Robot robot) {
        service.create(
                robot.getId(),
                robot.getCreatedAt(),
                RobotHistoryType.CREATE,
                Collections.emptyMap(),
                robot.getAttributes()
        );
    }

    /**
     * Consumes a robot update event
     *
     * @see com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer#consumeRobotUpdateEvent(Robot, Map, Map)
     * @param robot
     * @param oldAttributes
     * @param newAttributes
     */
    @Override
    public void consumeRobotUpdateEvent(
            Robot robot,
            Map<String, Object> oldAttributes,
            Map<String, Object> newAttributes
    ) {
        service.create(
                robot.getId(),
                robot.getUpdatedAt(),
                RobotHistoryType.UPDATE,
                oldAttributes,
                newAttributes
        );
    }
}
