package com.lud.challenge.kodo.resources.spring.events.entities;

import com.lud.challenge.kodo.domain.robot.entities.Robot;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * ApplicationEvent for robot update
 *
 * @author Lud Ribeiro
 */
@Getter
public class RobotUpdateEvent extends ApplicationEvent {
    private Robot robot;
    private Map<String, Object> oldAttributes;
    private Map<String, Object> newAttributes;

    public RobotUpdateEvent(Robot robot, Map<String, Object> oldAttributes, Map<String, Object> newAttributes) {
        super(robot);
        this.robot = robot;
        this.oldAttributes = oldAttributes;
        this.newAttributes = newAttributes;
    }
}
