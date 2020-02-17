package com.lud.challenge.kodo.domain.robot.services.impl;

import com.lud.challenge.kodo.domain.robot.entities.Robot;
import com.lud.challenge.kodo.domain.robot.exceptions.RobotNotFoundException;
import com.lud.challenge.kodo.domain.robot.repositories.RobotRepository;
import com.lud.challenge.kodo.domain.robot.services.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * Robot Info's Service Implementation
 *
 * @see com.lud.challenge.kodo.domain.robot.services.RobotService
 * @author Lud Ribeiro
 */
@Service
public class RobotServiceImpl implements RobotService {

    /**
     * Robot Repository
     */
    @Autowired
    private RobotRepository repository;

    /**
     * Creates a new Robot from a base robot object
     *
     * @see com.lud.challenge.kodo.domain.robot.services.RobotService#create(Robot)
     * @param robot
     * @return created robot
     */
    @Override
    public Robot create(Robot robot) {
        return repository.save(Robot.of(robot));
    }

    /**
     * Updates a robot updating or adding new specified attributes
     *
     * @see com.lud.challenge.kodo.domain.robot.services.RobotService#update(UUID, Map)
     * @param id
     * @param attributes
     * @return updated robot
     */
    @Override
    public Robot update(UUID id, Map<String, Object> attributes) throws RobotNotFoundException {

        Robot robot = get(id);

        return repository.save(robot.update(attributes));
    }

    /**
     * Gets a robot for a given id
     *
     * @see com.lud.challenge.kodo.domain.robot.services.RobotService#get(UUID)
     * @param id
     * @return robot referent to the given id
     * @throws RobotNotFoundException when the given id doesn't represent any existent robot
     */
    @Override
    public Robot get(UUID id) throws RobotNotFoundException {
        Robot robot = repository.get(id);

        if(robot == null) {
            throw new RobotNotFoundException(id);
        }

        return robot;
    }
}
