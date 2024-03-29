package com.nexon.flow.domain.entity;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

    private static final String DATE_FORMAT ="yyyy-MM-dd HH:mm:ss";
    private static final String TIME_ZONE ="Asia/Seoul";

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT, timezone = TIME_ZONE)
    @Column(name = "regdt", updatable = false)
    private Date regdt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT, timezone = TIME_ZONE)
    @Column(name = "moddt", updatable = true)
    private Date moddt;
}