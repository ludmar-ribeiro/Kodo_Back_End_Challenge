package com.lud.challenge.kodo.domain.robot.exceptions;

import java.util.UUID;

/**
 * Exception thrown when none robot is found for a given id
 *
 * @author Lud Ribeiro
 */
public class RobotNotFoundException extends Exception {

    public RobotNotFoundException(UUID id) {
        super(String.format("There's no robot for the given id %s", id));
        this.id = id;
    }

    /**
     * Id that doesn't represent any robot
     */
    private UUID id;
}
