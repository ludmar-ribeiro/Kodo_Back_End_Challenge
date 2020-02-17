package com.lud.challenge.kodo.resources.spring.events.listeners;

import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotCreationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * SpringEventListener for robot creation event
 *
 * @author Lud Ribeiro
 */
@Component
public class RobotCreationSpringEventListener implements ApplicationListener<RobotCreationEvent> {

    @Autowired
    private RobotEventConsumer consumer;

    /**
     * Listens to robot creation event
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(RobotCreationEvent event) {
        consumer.consumeRobotCreationEvent(event.getRobot());
    }
}
