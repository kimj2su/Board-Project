package com.example.projectboard.support.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @CreationTimestamp @Column private LocalDateTime createdAt;

    @UpdateTimestamp @Column private LocalDateTime updatedAt;
    @ColumnDefault("'N'") private String deleted;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getDeleted() {
        return deleted;
    }

    public void deleted() {
        this.deleted = "Y";
    }
}
