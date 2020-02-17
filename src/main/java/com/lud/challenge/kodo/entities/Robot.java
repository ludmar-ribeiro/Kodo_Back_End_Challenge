package com.lud.challenge.kodo.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Robot entity
 *
 * @author Lud Ribeiro
 */
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Robot {

    /**
     * Robot's id
     */
    private UUID id;

    /**
     * Robot's name
     */
    private String name;

    /**
     * Robot's attributes map
     */
    private Map<String, Object> attributes = Collections.emptyMap();

    /**
     * Robot's creation date
     */
    private LocalDateTime createdAt;

    /**
     * Robot's update date
     */
    private LocalDateTime updatedAt;

    /**
     * Factory method for a robot from another robot object
     *
     * @param robot
     * @return a new robot
     */
    public static Robot of(Robot robot) {
        return new Robot(
                UUID.randomUUID(),
                robot.getName(),
                new HashMap<>(robot.getAttributes()),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    /**
     * Create a clone of this robot
     *
     * @return cloned robot
     */
    public Robot clone() {
        return new Robot(
                id,
                name,
                new HashMap<>(attributes),
                createdAt,
                updatedAt
        );
    }

    /**
     * Creates a updated clone from this robot
     *
     * @param attributes
     * @return updated robot
     */
    public Robot update(Map<String, Object> attributes) {
        Map<String, Object> newAttributes = new HashMap<>(this.attributes);

        newAttributes.putAll(attributes);

        return new Robot(
                id,
                name,
                newAttributes,
                createdAt,
                LocalDateTime.now()
        );
    }
}
