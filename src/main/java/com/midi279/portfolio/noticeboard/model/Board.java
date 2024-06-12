package com.midi279.portfolio.noticeboard.model;

import com.midi279.portfolio.noticeboard.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_BOARD")
public class Board extends BaseTimeEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1583457078643074983L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 65000)
    private String bodyContent;

    @Column
    private Boolean favorite = false;

    @Column
    private String writer;

    @Column
    private String password;

    @Column
    private Integer count = 0;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_board_tag", joinColumns = @JoinColumn(name = "board_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();


}
