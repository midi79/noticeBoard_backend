package com.midi279.portfolio.noticeboard.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(Long id) {
        super("Board not found with id : " + id);
    }
}
