package com.midi279.portfolio.noticeboard.util;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseTimeEntity implements Serializable {

	@Serial
	private static final long serialVersionUID = 3939245997777718376L;

	@CreatedDate
	private LocalDateTime createDate;

	@LastModifiedDate
    private LocalDateTime modifyDate;

}

