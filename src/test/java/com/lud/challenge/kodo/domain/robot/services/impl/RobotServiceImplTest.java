package com.lud.challenge.kodo.domain.robot.services.impl;


import com.lud.challenge.kodo.commons.entities.MutableRobot;
import com.lud.challenge.kodo.commons.entities.Robot;
import com.lud.challenge.kodo.domain.robot.events.producers.RobotEventProducer;
import com.lud.challenge.kodo.domain.robot.exceptions.RobotNotFoundException;
import com.lud.challenge.kodo.domain.robot.repositories.RobotRepository;
import com.lud.challenge.kodo.domain.robot.services.impl.data.RobotServiceImplTestData;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit Test for RobotServiceImpl
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles({"test","service-test"})
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:robot-service-test.properties")
public class RobotServiceImplTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Autowired
    private RobotRepository repository;

    @Autowired
    private RobotEventProducer eventProducer;

    @Autowired
    private RobotServiceImpl service;

    @Autowired
    RobotServiceImplTestData testData;

    @Before
    public void before() {
        reset(repository, eventProducer);
    }

    /**
     * Given a robot with name and attributes
     * When create method called
     * Then a robot object with id and creation date should be saved
     */
    @Test
    public void shouldCreateNewObject() {
        Robot input = testData.getRobotCreationTestInput().toDomain();
        Robot expectedOutput = Robot.of(input);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);

        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        LocalDateTime executionTime = LocalDateTime.now();

        service.create(input);

        assertNotNull(captor.getValue());
        Robot output = captor.getValue();

        assertEquals(input.getName(), output.getName());
        assertEquals(input.getAttributes(), output.getAttributes());
        assertNotNull(output.getId());
        assertTrue(
                executionTime.equals(output.getCreatedAt())
                        || executionTime.isBefore(output.getCreatedAt())
        );
    }

    /**
     * Given a robot with name and attributes
     * When create method called
     * Then a robot creation event should be published
     */
    @Test
    public void shouldPublishEventAfterCreateNewObject() {
        Robot input = testData.getRobotCreationTestInput().toDomain();
        Robot expectedOutput = Robot.of(input);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);

        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        service.create(input);

        assertNotNull(captor.getValue());
        Robot output = captor.getValue();

        verify(eventProducer).publishRobotCreationEvent(captor.capture());

        Robot createdRobotEvent = captor.getValue();

        assertEquals(input.getName(), createdRobotEvent.getName());
        assertEquals(output.getName(), createdRobotEvent.getName());
        assertEquals(expectedOutput, createdRobotEvent);
        assertEquals(2, captor.getAllValues().size());
    }

    /**
     * Given a valid robot id and some attributes
     * When update method called
     * Then a robot object referent to given id should be updated and saved
     */
    @Test
    public void shouldUpdateExistentObject() throws RobotNotFoundException {
        Robot input = testData.getRobotUpdateTestInput().get(0).toDomain();
        Robot robotFromStorage = Robot.of(testData.getRobotCreationTestInput().toDomain());
        UUID id = robotFromStorage.getId();
        Robot expectedOutput = robotFromStorage.update(input.getAttributes());

        when(repository.get(id)).thenReturn(robotFromStorage);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);
        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        LocalDateTime executionTime = LocalDateTime.now();

        service.update(id, input.getAttributes());

        assertNotNull(captor.getValue());
        Robot output = captor.getValue();

        assertEquals(id, output.getId());
        assertEquals(robotFromStorage.getName(), output.getName());

        input.getAttributes().keySet().forEach(key -> {
            assertEquals(input.getAttributes().get(key), output.getAttributes().get(key));
        });
        assertTrue(input.getAttributes().size() < output.getAttributes().size());
        assertTrue(robotFromStorage.getAttributes().size() < output.getAttributes().size());
        assertEquals(expectedOutput.getAttributes(), output.getAttributes());

        assertTrue(
                executionTime.equals(output.getCreatedAt())
                        || executionTime.isAfter(output.getCreatedAt())
        );
        assertTrue(
                executionTime.equals(output.getUpdatedAt())
                        || executionTime.isBefore(output.getUpdatedAt())
        );
    }

    /**
     * Given a valid robot id and some attributes
     * When update method called
     * Then a robot update event should be published
     */
    @Test
    @SuppressWarnings("unchecked")
    public void shouldPublicEventAfterUpdateExistentObject() throws RobotNotFoundException {
        Robot input = testData.getRobotUpdateTestInput().get(0).toDomain();
        Robot robotFromStorage = Robot.of(testData.getRobotCreationTestInput().toDomain());
        UUID id = robotFromStorage.getId();
        Robot expectedOutput = robotFromStorage.update(input.getAttributes());

        when(repository.get(id)).thenReturn(robotFromStorage);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);
        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        ArgumentCaptor<Map<String, Object>> oldAttributesCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<Map<String, Object>> newAttributesCaptor = ArgumentCaptor.forClass(Map.class);

        LocalDateTime executionTime = LocalDateTime.now();

        service.update(id, input.getAttributes());

        assertNotNull(captor.getValue());
        Robot output = captor.getValue();

        verify(eventProducer).publishRobotUpdateEvent(
                captor.capture(),
                oldAttributesCaptor.capture(),
                newAttributesCaptor.capture()
        );

        Robot robotUpdateEvent = captor.getValue();

        assertEquals(2, captor.getAllValues().size());
        assertEquals(output.getName(), robotUpdateEvent.getName());
        assertEquals(expectedOutput, robotUpdateEvent);
        assertEquals(robotFromStorage.getAttributes(), oldAttributesCaptor.getValue());
        assertEquals(input.getAttributes(), newAttributesCaptor.getValue());
    }

    /**
     * Given a invalid robot id and some attributes
     * When update method called
     * Then a RobotNotFoundException should be thrown
     */
    @Test
    public void shouldThrowExceptionWhenUpdateInvalidId() throws RobotNotFoundException {
        Robot input = testData.getRobotUpdateTestInput().get(0).toDomain();

        thrownException.expect(RobotNotFoundException.class);
        thrownException.expectMessage(input.getId().toString());

        when(repository.get(input.getId())).thenReturn(null);

        service.update(input.getId(), input.getAttributes());
    }

    /**
     * Given a valid robot id
     * When get method called
     * Then a robot object referent to given id should be returned
     */
    @Test
    public void shouldGetExistentObject() throws RobotNotFoundException {
        Robot robotFromStorage = Robot.of(testData.getRobotCreationTestInput().toDomain());
        UUID id = robotFromStorage.getId();

        when(repository.get(id)).thenReturn(robotFromStorage);

        Robot output = service.get(id);

        assertNotNull(output);
        assertEquals(id, output.getId());
        assertEquals(robotFromStorage, output);
    }

    /**
     * Given a invalid robot id
     * When get method called
     * Then a RobotNotFoundException should be thrown
     */
    @Test
    public void shouldThrowExceptionWhenGetInvalidId() throws RobotNotFoundException {
        UUID id = UUID.randomUUID();

        thrownException.expect(RobotNotFoundException.class);
        thrownException.expectMessage(id.toString());

        when(repository.get(id)).thenReturn(null);

        service.get(id);
    }

    /**
     * Given the fact that there's no robot on storage
     * When get all method called
     * Then a empty list of robot should be returned
     */
    @Test
    public void shouldReturnNoRobotWhenGetAll() {
        when(repository.getAll()).thenReturn(Collections.emptyList());

        List<Robot> output = service.getAll();

        assertTrue(output.isEmpty());
    }

    /**
     * Given the fact that there's some robots on storage
     * When get all method called
     * Then a list of robot should be returned
     */
    @Test
    public void shouldReturnAllObjects() {
        List<Robot> robotsFromStorage = testData.getRobotGetAllTestStorage()
                .stream()
                .map(MutableRobot::toDomain)
                .collect(Collectors.toList());

        when(repository.getAll()).thenReturn(robotsFromStorage);

        List<Robot> output = service.getAll();

        assertEquals(3, output.size());
    }
}
