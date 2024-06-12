package com.midi279.portfolio.noticeboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDto {
    private Long id;
    private String title;
    private String bodyContent;
    private Set<String> tags;
}
