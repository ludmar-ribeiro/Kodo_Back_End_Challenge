package com.lud.challenge.kodo.domain.robot.services.impl;


import com.lud.challenge.kodo.domain.robot.entities.Robot;
import com.lud.challenge.kodo.domain.robot.exceptions.RobotNotFoundException;
import com.lud.challenge.kodo.domain.robot.repositories.RobotRepository;
import com.lud.challenge.kodo.domain.robot.services.impl.data.RobotServiceImplTestData;
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
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Unit Test for RobotServiceImpl
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:robot-service-test.properties")
public class RobotServiceImplTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Autowired
    private RobotRepository repository;

    @Autowired
    private RobotServiceImpl service;

    @Autowired
    RobotServiceImplTestData testData;

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
     * Given a valid robot id and some attributes
     * When update method called
     * Then a robot object referent to given id should be updated and saved
     */
    @Test
    public void shouldUpdateExistentObject() throws RobotNotFoundException {
        Robot input = testData.getRobotUpdateTestInput().get(0).toDomain();
        Robot robotFromStorage = Robot.of(testData.getRobotCreationTestInput().toDomain());
        Robot expectedOutput = robotFromStorage.update(input.getAttributes());

        when(repository.get(input.getId())).thenReturn(robotFromStorage);

        ArgumentCaptor<Robot> captor = ArgumentCaptor.forClass(Robot.class);
        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        LocalDateTime executionTime = LocalDateTime.now();

        service.update(input.getId(), input.getAttributes());

        assertNotNull(captor.getValue());
        Robot output = captor.getValue();

        assertEquals(input.getId(), output.getId());
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
        UUID id = UUID.randomUUID();
        Robot robotFromStorage = Robot.of(testData.getRobotCreationTestInput().toDomain());

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
}
