package com.lud.challenge.kodo.domain.robot.services;

import com.lud.challenge.kodo.entities.Robot;
import com.lud.challenge.kodo.domain.robot.exceptions.RobotNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Robot Info's Service
 *
 * @author Lud Ribeiro
 */
public interface RobotService {

    /**
     * Creates a new Robot from a base robot object
     *
     * @param robot
     * @return the robot created object
     */
    Robot create(Robot robot);

    /**
     * Updates a robot updating or adding new specified attributes
     *
     * @param id
     * @param attributes
     * @return updated robot
     * @throws RobotNotFoundException when the given id doesn't represent any existent robot
     */
    Robot update(UUID id, Map<String, Object> attributes) throws RobotNotFoundException;

    /**
     * Gets a robot for a given id
     *
     * @param id
     * @return robot referent to the given id
     * @throws RobotNotFoundException when the given id doesn't represent any existent robot
     */
    Robot get(UUID id) throws RobotNotFoundException;

    /**
     * Gets all robots
     *
     * @return List of robots
     */
    List<Robot> getAll();
}
