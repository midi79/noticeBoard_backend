package com.midi279.portfolio.noticeboard.service;

import com.midi279.portfolio.noticeboard.dto.BoardDto;
import com.midi279.portfolio.noticeboard.dto.BoardListDto;
import com.midi279.portfolio.noticeboard.dto.BoardUpdateDto;
import com.midi279.portfolio.noticeboard.dto.BoardViewDto;
import com.midi279.portfolio.noticeboard.exception.BoardNotFoundException;
import com.midi279.portfolio.noticeboard.model.Board;
import com.midi279.portfolio.noticeboard.repository.BoardRepository;
import com.midi279.portfolio.noticeboard.util.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * Get All Boards
     * @return BoardDto List
     */
    public Page<BoardListDto> getAllBoards(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        List<BoardListDto> listDtos = boardPage.getContent().stream().map(BoardMapper::toListDto).collect(Collectors.toList());
        return new PageImpl<>(listDtos, pageable, boardPage.getTotalElements());
    }

    /**
     * Search Boards by search condition
     * @return BoardDto List
     */
    public Page<BoardListDto> searchBoards(String writer, String title, String tag, Boolean favorite, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime localDateTimeFromDate = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime localDateTimeToDate = toDate != null ? toDate.atStartOfDay() : null;
        Page<Board> boardPage = boardRepository.search(writer, title, tag, favorite, localDateTimeFromDate, localDateTimeToDate, pageable);
        List<BoardListDto> listDtos = boardPage.getContent().stream().map(BoardMapper::toListDto).collect(Collectors.toList());
        return new PageImpl<>(listDtos, pageable, boardPage.getTotalElements());
    }


    /**
     * Find BoardDto by ID
     * @param id Board ID
     * @return BoardDto
     */
    public BoardViewDto getBoardByIdForUpdate(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException(id));
        return BoardMapper.toViewDto(board);
    }
    /**
     * Find BoardDto by ID and increase count
     * @param id Board ID
     * @return BoardDto
     */
    public BoardViewDto getBoardById(Long id) {
        try {
            boardRepository.incrementCount(id);
        } catch (Exception exception) {
            throw new BoardNotFoundException(id);
        }

        Board board = boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException(id));
        return BoardMapper.toViewDto(board);
    }

    /**
     * Create New Board
     * @param boardDto Board DTO
     * @return New boardDto
     */
    public BoardDto createBoard(BoardDto boardDto) {
        Board board = BoardMapper.toEntity(boardDto);
        return BoardMapper.toDto(boardRepository.save(board));
    }

    /**
     * Delete Board by ID
     * @param id Board ID
     */
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }


    /**
     * Delete multiple Boards by IDs
     * @param ids
     */
    public void deleteBoardsByIds(List<Long> ids) {
        boardRepository.deleteByIds(ids);
    }

    /**
     * Update Board
     * @param boardUpdateDto Board Update DTO
     */
    public BoardUpdateDto updateBoard(BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardUpdateDto.getId()).orElseThrow(() -> new BoardNotFoundException(boardUpdateDto.getId()));
        board.setTitle(boardUpdateDto.getTitle());
        board.setBodyContent(boardUpdateDto.getBodyContent());
        board.setTags(boardUpdateDto.getTags());
        return BoardMapper.toUpdateDto(boardRepository.save(board));
    }

    /**
     * Update Favorite by Board ID
     * @param boardId Board ID
     * @param favorite Favorite or not
     */
    public void updateBoardFavorite(Long boardId, Boolean favorite) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
        board.setFavorite(favorite);
        boardRepository.save(board);
    }

    /**
     * Verify the Board password
     * @param boardId Board ID
     * @param password Password that was input by a user
     */
    public Boolean verifyBoardPassword(Long boardId, String password) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
        return board.getPassword().equals(password);
    }



    /**
     * [Unused] Increase Board Count
     * @param boardId Board ID
     */
    public void increaseBoardCount(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotFoundException(boardId));
        board.setCount(board.getCount() + 1);
        boardRepository.save(board);
    }
}
