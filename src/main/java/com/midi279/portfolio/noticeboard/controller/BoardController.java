package com.midi279.portfolio.noticeboard.controller;

import com.midi279.portfolio.noticeboard.dto.BoardDto;
import com.midi279.portfolio.noticeboard.dto.BoardListDto;
import com.midi279.portfolio.noticeboard.dto.BoardUpdateDto;
import com.midi279.portfolio.noticeboard.dto.BoardViewDto;
import com.midi279.portfolio.noticeboard.model.Board;
import com.midi279.portfolio.noticeboard.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * Get All Boards
     * @return BoardDto List
     */
    @Operation(summary = "Get All Boards", description = "Get All Board with pagination. Lists are ordered by ID descendant")
    @GetMapping("/all")
    public ResponseEntity<Page<BoardListDto>> getAllBoards(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(boardService.getAllBoards(pageable));
    }

    /**
     * Search Boards by search condition
     * @return BoardDto List
     */
    @Operation(summary = "Search boards", description = "Search boards by writer, title, tag, from-date, and to-date. It also applied pagination.")
    @GetMapping("/search")
    public ResponseEntity<Page<BoardListDto>> searchNoticeBoards(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Boolean favorite,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BoardListDto> results = boardService.searchBoards(writer, title, tag, favorite, fromDate, toDate, pageable);
        return ResponseEntity.ok(results);
    }

    /**
     * Find BoardDto by ID and increase count
     * @param id Board ID
     * @return BoardDto
     */
    @Operation(summary = "Get a board data with count increase", description = "Get a board data")
    @GetMapping("/{id}")
    public ResponseEntity<BoardViewDto> getBoardById(@PathVariable("id") Long id) {
        BoardViewDto boardViewDto = boardService.getBoardById(id);
        return boardViewDto != null ? ResponseEntity.ok(boardViewDto) : ResponseEntity.notFound().build();
    }

    /**
     * Find BoardDto by ID
     * @param id Board ID
     * @return BoardDto
     */
    @Operation(summary = "Get a board data without count increase", description = "Get a board data")
    @GetMapping("/{id}/update")
    public ResponseEntity<BoardViewDto> getBoardByIdForUpdate(@PathVariable("id") Long id) {
        BoardViewDto boardViewDto = boardService.getBoardByIdForUpdate(id);
        return boardViewDto != null ? ResponseEntity.ok(boardViewDto) : ResponseEntity.notFound().build();
    }


    /**
     * Create New Board
     * @param boardDto Board DTO
     * @return New boardDto
     */
    @Operation(summary = "Create new board", description = "Create board with title, writer, password, body content, tags.")
    @PostMapping("/create")
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto boardDto) {
        BoardDto newBoard = boardService.createBoard(boardDto);
        return ResponseEntity.ok(newBoard);
    }


    /**
     * Delete Board by ID
     * @param id Board ID
     */
    @Operation(summary = "Delete board data", description = "Delete one board data")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete multiple Boards by IDs
     * @param ids
     */
    @Operation(summary = "Delete board data", description = "Delete multiple board data")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBoards(@RequestBody List<Long> ids) {
        boardService.deleteBoardsByIds(ids);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update Board
     * @param boardUpdateDto Board Update DTO
     */
    @Operation(summary = "Update board data", description = "Update the board data. Title, body contents, and tags")
    @PatchMapping("/update")
    public ResponseEntity<BoardUpdateDto> updateBoard(@RequestBody BoardUpdateDto boardUpdateDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardUpdateDto));
    }

    /**
     * Update Favorite by Board ID
     * @param boardId Board ID
     * @param favorite Favorite or not
     */
    @Operation(summary = "Update the favorite value", description = "Set the favorite value for each board row.")
    @PatchMapping("/update/{boardId}/favorite")
    public ResponseEntity<Void> updateTags(@PathVariable("boardId") Long boardId, @RequestBody Boolean favorite) {
        boardService.updateBoardFavorite(boardId, favorite);
        return ResponseEntity.noContent().build();
    }

    /**
     *  Verify the password
     * @param boardDto
     * @return Boolean
     */
    @Operation(summary = "Verify the password before editing", description = "Compare the password that was stored in DB with the user input password.")
    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody BoardDto boardDto) {
        Boolean result = boardService.verifyBoardPassword(boardDto.getId(), boardDto.getPassword());
        return ResponseEntity.ok(result);
    }
}
