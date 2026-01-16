package com.compiler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "code_submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;
    
    @Column(nullable = false)
    private String language;
    
    @Column(columnDefinition = "TEXT")
    private String input;
    
    @Column(columnDefinition = "TEXT")
    private String output;
    
    @Column(columnDefinition = "TEXT")
    private String error;
    
    @Column(name = "execution_time")
    private Long executionTime; // in milliseconds
    
    @Column(name = "memory_used")
    private Long memoryUsed; // in bytes
    
    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;
    
    @Column(name = "share_id", unique = true)
    private String shareId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    private String userId; // Optional: for user tracking
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    public enum ExecutionStatus {
        SUCCESS,
        ERROR,
        TIMEOUT,
        COMPILATION_ERROR,
        RUNTIME_ERROR
    }
}
