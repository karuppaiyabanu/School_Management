package com.example.schoolmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mark")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    private Student student;
    @Column(name = "mark")
    private Integer mark;
    @ManyToOne
    private Exam exam;
    @CurrentTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;

}
