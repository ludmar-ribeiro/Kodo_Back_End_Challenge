package com.lud.challenge.kodo.domain.robot.repositories;

import com.lud.challenge.kodo.domain.robot.entities.RobotHistory;

import java.util.List;
import java.util.UUID;

/**
 * Robot history repository
 *
 * @author Lud Ribeiro
 */
public interface RobotHistoryRepository {

    /**
     * Saves a robot history
     *
     * @param robot
     * @return the saved robot history
     */
    RobotHistory save(RobotHistory robot);

    /**
     * Gets history entries for a given robotId
     *
     * @param robotId
     * @return List of robot history entries
     */
    List<RobotHistory> getAllByRobotId(UUID robotId);
}
