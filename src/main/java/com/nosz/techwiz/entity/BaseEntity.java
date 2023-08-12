package com.nosz.techwiz.entity;


import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@MappedSuperclass
public abstract class BaseEntity implements Cloneable, Serializable {
    @CreationTimestamp
    private LocalDateTime createAt;


    @Getter
    @CreationTimestamp
    private LocalDateTime updateAt;


    @Transient
    private String updateAtInString;

    @Transient
    private Long minusAgo;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public BaseEntity() {
        super();
    }

    public BaseEntity(LocalDateTime createAt, LocalDateTime updateAt) {
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getUpdateAtInString() {
        return updateAt.getHour() + ":" + updateAt.getMinute();
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getMinusAgo() {

        return ChronoUnit.MINUTES.between(updateAt, LocalDateTime.now());
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
