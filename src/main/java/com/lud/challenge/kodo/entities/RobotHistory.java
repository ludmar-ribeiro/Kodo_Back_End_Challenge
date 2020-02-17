package com.lud.challenge.kodo.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Robot change history entry
 *
 * @author Lud Ribeiro
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RobotHistory {

    /**
     * Robot History id
     */
    private UUID id;

    /**
     * Robot id
     */
    private UUID robotId;

    /**
     * Robot History type
     */
    private RobotHistoryType type;

    /**
     * Robot attributes changes
     */
    private Map<String, Change> changes;

    /**
     * Entry date
     */
    private LocalDateTime date;

    /**
     * Factory method to create a Robot history
     *
     * @param robotId
     * @param date
     * @param type
     * @param oldAttributes
     * @param newAttributes
     * @return a new robot history entry
     */
    public static RobotHistory of(
            UUID robotId,
            LocalDateTime date,
            RobotHistoryType type,
            Map<String, Object> oldAttributes,
            Map<String, Object> newAttributes
    ) {

        Map<String, Change> changes = changesOf(oldAttributes, newAttributes);

        return new RobotHistory(
                UUID.randomUUID(),
                robotId,
                type,
                changes,
                date
        );
    }

    /**
     * Creates a changes map from old and new attributes map
     *
     * @param oldAttributes
     * @param newAttributes
     * @return Changes map
     */
    private static Map<String, Change> changesOf(Map<String, Object> oldAttributes, Map<String, Object> newAttributes) {
        Map<String, Change> changes = new HashMap<>();

        newAttributes.keySet().forEach(key -> {
            changes.put(key, new Change(
                    oldAttributes.get(key),
                    newAttributes.get(key)
            ));
        });

        return changes;
    }

    /**
     * Change of an attribute
     *
     * @author Lud Ribeiro
     */
    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Change {

        /**
         * Old value
         */
        private Object oldValue;

        /**
         * New value
         */
        private Object newValue;
    }
}
