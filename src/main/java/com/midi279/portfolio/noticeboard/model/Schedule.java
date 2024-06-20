package com.midi279.portfolio.noticeboard.model;

import com.midi279.portfolio.noticeboard.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_SCHEDULE")
public class Schedule extends BaseTimeEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID = 7405115871022575055L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String fromHour;

    @Column
    private String toHour;

    @Column
    private String color;
}
