package com.lud.challenge.kodo.domain.robot.services.impl;

import com.lud.challenge.kodo.domain.robot.entities.Robot;
import com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer;
import com.lud.challenge.kodo.domain.robot.exceptions.RobotNotFoundException;
import com.lud.challenge.kodo.domain.robot.repositories.RobotRepository;
import com.lud.challenge.kodo.domain.robot.services.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * Robot event producer
     */
    @Autowired
    private RobotEventProducer eventProducer;

    /**
     * Creates a new Robot from a base robot object
     *
     * @see com.lud.challenge.kodo.domain.robot.services.RobotService#create(Robot)
     * @param robot
     * @return created robot
     */
    @Override
    public Robot create(Robot robot) {
        Robot createdRobot = repository.save(Robot.of(robot));

        eventProducer.publishRobotCreationEvent(createdRobot);

        return createdRobot;
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

        Robot updatedRobot = repository.save(robot.update(attributes));

        eventProducer.publishRobotUpdateEvent(updatedRobot, robot.getAttributes(), attributes);

        return updatedRobot;
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

    /**
     * Gets all robots
     *
     * @return List of robots
     */
    @Override
    public List<Robot> getAll() {
        return repository.getAll();
    }
}
