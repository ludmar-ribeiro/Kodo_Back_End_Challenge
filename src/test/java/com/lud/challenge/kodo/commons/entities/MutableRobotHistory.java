package com.lud.challenge.kodo.commons.entities;

import com.lud.challenge.kodo.domain.robot.entities.RobotHistory;
import com.lud.challenge.kodo.domain.robot.entities.RobotHistoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.lud.challenge.kodo.domain.robot.entities.RobotHistory.Change;

@Getter
@Setter
@NoArgsConstructor
public class MutableRobotHistory {
    private UUID id;
    private UUID robotId;
    private RobotHistoryType type;
    private Map<String, MutableChange> changes;
    private LocalDateTime date;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MutableChange {
        private Object oldValue;
        private Object newValue;

        public Change toDomain() {
            return new Change(
                    oldValue,
                    newValue
            );
        }
    }

    private Map<String, Change> getDomainChanges() {
        Map<String, Change> changes = new HashMap<>();

        this.changes.forEach((key, value) -> {
            changes.put(key, value.toDomain());
        });

        return changes;
    }

    public RobotHistory toDomain() {
        return new RobotHistory(
                id,
                robotId,
                type,
                getDomainChanges(),
                date
        );
    }
}
