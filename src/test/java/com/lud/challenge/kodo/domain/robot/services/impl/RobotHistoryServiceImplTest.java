package com.lud.challenge.kodo.domain.robot.services.impl;

import com.lud.challenge.kodo.entities.RobotHistoryType;
import com.lud.challenge.kodo.entities.MutableRobotHistory;
import com.lud.challenge.kodo.entities.RobotHistory;
import com.lud.challenge.kodo.domain.robot.repositories.RobotHistoryRepository;
import com.lud.challenge.kodo.domain.robot.services.RobotHistoryService;
import com.lud.challenge.kodo.domain.robot.services.impl.data.RobotServiceHistoryImplTestData;
import org.junit.Before;
import org.junit.Test;
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
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Unit Test for RobotServiceHistoryImpl
 *
 * @author Lud Ribeiro
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:robot-history-service-test.properties")
public class RobotHistoryServiceImplTest {

    @Autowired
    private RobotHistoryRepository repository;

    @Autowired
    private RobotHistoryService service;

    @Autowired
    private RobotServiceHistoryImplTestData data;

    @Before
    public void before() { reset(repository); }

    /**
     * Given some robot creation info
     * When robot history creation method called
     * Then a new robot history entry should be created
     */
    @Test
    public void shouldCreateRobotCreationHistory() {
        UUID robotId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        RobotHistoryType type = RobotHistoryType.CREATE;
        Map<String, Object> oldAttributes = Collections.emptyMap();
        Map<String, Object> newAttributes = data.getRobotCreationTestInput().getAttributes();

        RobotHistory expectedOutput = RobotHistory.of(
                robotId,
                date,
                type,
                oldAttributes,
                newAttributes
        );

        ArgumentCaptor<RobotHistory> captor = ArgumentCaptor.forClass(RobotHistory.class);
        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        service.create(robotId, date, type, oldAttributes, newAttributes);

        RobotHistory output = captor.getValue();
        assertNotNull(output);

        assertNotNull(output.getId());
        assertEquals(robotId, output.getRobotId());
        assertEquals(date, output.getDate());
        assertEquals(type, output.getType());
        assertEquals(expectedOutput.getChanges(), output.getChanges());
        assertEquals(newAttributes.keySet(), output.getChanges().keySet());

        output.getChanges().keySet().forEach(key -> {
            RobotHistory.Change change = output.getChanges().get(key);
            assertEquals(newAttributes.get(key), change.getNewValue());

            assertFalse(oldAttributes.containsKey(key));
        });
    }

    /**
     * Given some robot update info
     * When robot history creation method called
     * Then a new robot history entry should be created
     */
    @Test
    public void shouldCreateRobotUpdateHistory() {
        UUID robotId = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();
        RobotHistoryType type = RobotHistoryType.UPDATE;
        Map<String, Object> oldAttributes = data.getRobotCreationTestInput().getAttributes();
        Map<String, Object> newAttributes = data.getRobotUpdateTestInput().get(0).getAttributes();

        RobotHistory expectedOutput = RobotHistory.of(
                robotId,
                date,
                type,
                oldAttributes,
                newAttributes
        );

        ArgumentCaptor<RobotHistory> captor = ArgumentCaptor.forClass(RobotHistory.class);
        when(repository.save(captor.capture())).thenReturn(expectedOutput);

        service.create(robotId, date, type, oldAttributes, newAttributes);

        RobotHistory output = captor.getValue();
        assertNotNull(output);

        assertNotNull(output.getId());
        assertEquals(robotId, output.getRobotId());
        assertEquals(date, output.getDate());
        assertEquals(type, output.getType());
        assertEquals(expectedOutput.getChanges(), output.getChanges());
        assertEquals(newAttributes.keySet(), output.getChanges().keySet());

        output.getChanges().keySet().forEach(key -> {
            RobotHistory.Change change = output.getChanges().get(key);
            assertEquals(newAttributes.get(key), change.getNewValue());

            if(change.getOldValue() == null) {
                assertFalse(oldAttributes.containsKey(key));
            } else {
                assertEquals(oldAttributes.get(key), change.getOldValue());
            }
        });
    }

    /**
     * Given a robotId referent to a robot which was changed
     * When get history method called
     * Then a list of history should be returned
     */
    @Test
    public void shouldGetAllRobotHistory() {
        List<RobotHistory> historyFromStorage = data.getRobotGetHistoryTestStorage()
                .stream()
                .map(MutableRobotHistory::toDomain)
                .collect(Collectors.toList());
        UUID robotId = historyFromStorage.get(0).getRobotId();

        when(repository.getAllByRobotId(robotId)).thenReturn(historyFromStorage);

        List<RobotHistory> output = service.get(robotId);

        assertEquals(2, output.size());
        assertEquals(robotId, output.get(0).getRobotId());
        assertEquals(RobotHistoryType.CREATE, output.get(0).getType());
        assertEquals(RobotHistoryType.UPDATE, output.get(1).getType());
    }
}
