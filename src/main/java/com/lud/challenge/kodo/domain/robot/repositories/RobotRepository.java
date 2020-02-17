package com.lud.challenge.kodo.domain.robot.repositories;

import com.lud.challenge.kodo.domain.robot.entities.Robot;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Robot
 *
 * @author Lud Ribeiro
 */
public interface RobotRepository {

    /**
     * Saves a Robot
     * @param robot
     * @return the saved robot
     */
    Robot save(Robot robot);

    /**
     * Gets a Robot from the repository
     * @param id
     * @return the found Robot
     */
    Robot get(UUID id);

    /**
     * Gets all robots from the repository
     * @return List of robots
     */
    List<Robot> getAll();
}
