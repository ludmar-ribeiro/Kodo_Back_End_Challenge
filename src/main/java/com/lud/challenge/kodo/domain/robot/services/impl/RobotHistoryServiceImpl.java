package com.lud.challenge.kodo.domain.robot.services.impl;

import com.lud.challenge.kodo.commons.entities.RobotHistoryType;
import com.lud.challenge.kodo.commons.entities.RobotHistory;
import com.lud.challenge.kodo.domain.robot.repositories.RobotHistoryRepository;
import com.lud.challenge.kodo.domain.robot.services.RobotHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Robot history service
 *
 * @see com.lud.challenge.kodo.domain.robot.services.RobotHistoryService
 * @author Lud Ribeiro
 */
@Service
public class RobotHistoryServiceImpl implements RobotHistoryService {


    /**
     * Robot History repository
     */
    @Autowired
    private RobotHistoryRepository repository;

    /**
     * Creates a new robot history entry
     *
     * @see com.lud.challenge.kodo.domain.robot.services.RobotHistoryService#create(UUID, LocalDateTime, RobotHistoryType, Map, Map)
     * @param robotId
     * @param date
     * @param type
     * @param oldAttributes
     * @param newAttributes
     * @return a new robot history entry
     */
    @Override
    public RobotHistory create(
            UUID robotId,
            LocalDateTime date,
            RobotHistoryType type,
            Map<String, Object> oldAttributes,
            Map<String, Object> newAttributes
    ) {
        RobotHistory history = RobotHistory.of(
                robotId,
                date,
                type,
                oldAttributes,
                newAttributes
        );

        return repository.save(history);
    }

    /**
     * Gets the history entries for a given robotId
     *
     * @see com.lud.challenge.kodo.domain.robot.services.RobotHistoryService#get(UUID)
     * @param robotId
     * @return List of robot history entries
     */
    @Override
    public List<RobotHistory> get(UUID robotId) {
        return repository.getAllByRobotId(robotId);
    }
}
