package com.example.assettest.entity;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Base entity fields from persistence.
 */
@Data
public abstract class BaseEntity {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean deleted;
}
