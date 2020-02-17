package com.lud.challenge.kodo.commons.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MutableRobot {
    private UUID id;
    private String name;
    private Map<String, Object> attributes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Robot toDomain() {
        return new Robot(
                id,
                name,
                new HashMap<>(attributes),
                createdAt,
                updatedAt
        );
    }
}
