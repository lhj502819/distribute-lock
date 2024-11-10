package com.onenine.distributelock.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Table(name = "common_lock")
@Entity
public class CommonLock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String lockKey;

    public String clientIp;

    public String threadId;

    public Integer entryCount;

    @Column(insertable = false, updatable = false)
    public LocalDateTime addTime;

    @Column(insertable = false, updatable = true)
    public LocalDateTime updateTime;

}