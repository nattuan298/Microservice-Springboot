package com.springboot.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @Column(updatable = false)
  private LocalDateTime createdAt;

  @Column(updatable = false)
  private String createdBy;

  @Column(insertable = false)
  private LocalDateTime updatedAt;

  @Column(insertable = false)
  private String updatedBy;
}
