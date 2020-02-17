package com.lud.challenge.kodo.resources.spring.events.listeners;

import com.lud.challenge.kodo.domain.robot.events.consumers.RobotEventConsumer;
import com.lud.challenge.kodo.resources.spring.events.entities.RobotUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * SpringEventListener for robot update event
 *
 * @author Lud Ribeiro
 */
@Component
public class RobotUpdateSpringEventListener implements ApplicationListener<RobotUpdateEvent> {

    @Autowired
    private RobotEventConsumer consumer;

    /**
     * Listens to robot update event
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(RobotUpdateEvent event) {
        consumer.consumeRobotUpdateEvent(event.getRobot(), event.getOldAttributes(), event.getNewAttributes());
    }
}
