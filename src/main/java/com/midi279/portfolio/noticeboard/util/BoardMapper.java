package com.midi279.portfolio.noticeboard.util;

import com.midi279.portfolio.noticeboard.dto.BoardDto;
import com.midi279.portfolio.noticeboard.dto.BoardListDto;
import com.midi279.portfolio.noticeboard.dto.BoardUpdateDto;
import com.midi279.portfolio.noticeboard.dto.BoardViewDto;
import com.midi279.portfolio.noticeboard.model.Board;

import java.time.LocalDateTime;

public class BoardMapper {


    public static BoardListDto toListDto(Board board) {
        if (board == null) {
            return null;
        }
        LocalDateTime editDate = board.getModifyDate().isAfter(board.getCreateDate())  ? board.getModifyDate() : board.getCreateDate();

        return new BoardListDto(board.getId(), board.getTitle(), board.getFavorite(),
                board.getWriter(), board.getCount(), editDate);
    }

    public static BoardDto toDto(Board board) {
        if (board == null) {
            return null;
        }
        LocalDateTime editDate = board.getModifyDate().isAfter(board.getModifyDate())  ? board.getModifyDate() : board.getCreateDate();


        return new BoardDto(board.getId(), board.getTitle(), board.getBodyContent(), board.getFavorite(),
                board.getWriter(), board.getPassword(), board.getCount(), board.getTags(), editDate);
    }

    public static BoardViewDto toViewDto(Board board) {
        if (board == null) {
            return null;
        }
        LocalDateTime editDate = board.getModifyDate().isAfter(board.getModifyDate())  ? board.getModifyDate() : board.getCreateDate();

        return new BoardViewDto(board.getId(), board.getTitle(), board.getBodyContent(), board.getFavorite(),
                board.getWriter(), board.getCount(), board.getTags(), editDate);
    }

    public static BoardUpdateDto toUpdateDto(Board board) {
        if (board == null) {
            return null;
        }
        return new BoardUpdateDto(board.getId(), board.getTitle(), board.getBodyContent(), board.getTags());
    }



    public static Board toEntity(BoardDto boardDto) {
        if (boardDto == null) {
            return null;
        }

        Board board = new Board();
        board.setId(boardDto.getId());
        board.setTitle(boardDto.getTitle());
        board.setBodyContent(boardDto.getBodyContent());
        board.setFavorite(boardDto.getFavorite() != null ? boardDto.getFavorite() : false);
        board.setWriter(boardDto.getWriter());
        board.setPassword(boardDto.getPassword());
        board.setCount(boardDto.getCount() != null ? boardDto.getCount() : 0);
        board.setTags(boardDto.getTags());

        return board;
    }
}
