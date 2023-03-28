package com.example.office.domain;


import com.example.office.domain.enums.FormStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@ToString
@Entity
@Table(name = Form.TABLE_NAME)
public class Form implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "form";
    private static final String SEQUENCE_GENERATOR = TABLE_NAME+"_sequence";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Form.SEQUENCE_GENERATOR)
    @SequenceGenerator(name = Form.SEQUENCE_GENERATOR, allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    private Instant datetime;

    @Enumerated(EnumType.STRING)
    private FormStatus status;

    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @PrePersist
    private void prePersist() {
        this.datetime = Instant.now();
    }
}
