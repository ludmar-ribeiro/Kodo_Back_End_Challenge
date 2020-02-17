package com.lud.challenge.kodo.resources.spring.events.entities;

import com.lud.challenge.kodo.commons.entities.Robot;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * ApplicationEvent for robot creation
 *
 * @author Lud Ribeiro
 */
@Getter
public class RobotCreationEvent extends ApplicationEvent {
    private Robot robot;

    public RobotCreationEvent(Robot robot) {
        super(robot);
        this.robot = robot;
    }
}
