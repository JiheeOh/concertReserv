package com.hhplus.concertReserv.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Setter
@Getter
public class BaseEntity {

    @Column(name = "CREATE_DT")
    @CreationTimestamp
    LocalDateTime createDt;

    @Column(name = "UPDATE_DT")
    @UpdateTimestamp
    LocalDateTime updateDt;
}
