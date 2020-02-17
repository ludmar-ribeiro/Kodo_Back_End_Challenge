package com.lud.challenge.kodo.domain.robot.services;

import com.lud.challenge.kodo.domain.robot.entities.RobotHistoryType;
import com.lud.challenge.kodo.domain.robot.entities.RobotHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Robot history service
 *
 * @author Lud Ribeiro
 */
public interface RobotHistoryService {

    /**
     * Creates a new robot history entry
     *
     * @param robotId
     * @param date
     * @param type
     * @param oldAttributes
     * @param newAttributes
     * @return new robot history id
     */
    RobotHistory create(
            UUID robotId,
            LocalDateTime date,
            RobotHistoryType type,
            Map<String, Object> oldAttributes,
            Map<String, Object> newAttributes
    );

    /**
     * Gets the history entries for a given robotId
     *
     * @param robotId
     * @return List of robot history entries
     */
    List<RobotHistory> get(UUID robotId);
}
