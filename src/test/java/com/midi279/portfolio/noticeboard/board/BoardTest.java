package com.midi279.portfolio.noticeboard.board;

import com.midi279.portfolio.noticeboard.dto.BoardDto;
import com.midi279.portfolio.noticeboard.dto.BoardListDto;
import com.midi279.portfolio.noticeboard.dto.BoardUpdateDto;
import com.midi279.portfolio.noticeboard.dto.BoardViewDto;
import com.midi279.portfolio.noticeboard.model.Board;
import com.midi279.portfolio.noticeboard.repository.BoardRepository;
import com.midi279.portfolio.noticeboard.service.BoardService;
import com.midi279.portfolio.noticeboard.util.BoardMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BoardTest {


    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    private BoardDto boardDto1;
    private BoardDto boardDto2;
    private BoardDto boardDto3;
    private BoardDto boardDto4;


    @BeforeEach
    void setUp() {
        boardDto1 = new BoardDto();
        boardDto1.setTitle("Board test1");
        boardDto1.setBodyContent("Board Content");
        boardDto1.setWriter("Tester");
        boardDto1.setPassword("1234qwer");
        boardDto1.setTags(Set.of("tag1", "tag2"));

        boardDto2 = new BoardDto();
        boardDto2.setTitle("Board test2");
        boardDto2.setBodyContent("Board Content");
        boardDto2.setWriter("Observer");
        boardDto2.setPassword("1234qwer");
        boardDto2.setTags(Set.of("tag3", "tag4"));

        boardDto3 = new BoardDto();
        boardDto3.setTitle("Board test3");
        boardDto3.setBodyContent("Board Content");
        boardDto3.setWriter("Observer");
        boardDto3.setPassword("1234qwer");
        boardDto3.setTags(Set.of("tag5", "tag6"));

        boardDto4 = new BoardDto();
        boardDto4.setTitle("Board test4");
        boardDto4.setBodyContent("Board Content");
        boardDto4.setWriter("Observer");
        boardDto4.setPassword("1234qwer");
        boardDto4.setTags(Set.of("tag7", "tag8"));
    }


    @Test
    public void createBoard() {
        //given

        // when
        Board savedBoard = boardRepository.save(BoardMapper.toEntity(boardDto1));

        // then
        assertThat(savedBoard.getId()).isNotNull();
        assertThat(savedBoard.getTitle()).isEqualTo("Board test1");
        assertThat(savedBoard.getBodyContent()).isEqualTo("Board Content");
        assertThat(savedBoard.getWriter()).isEqualTo("Tester");
        assertThat(savedBoard.getPassword()).isEqualTo("1234qwer");
        assertThat(savedBoard.getTags().size()).isEqualTo(2);
        assertThat(savedBoard.getTags().contains("tag1")).isTrue();
        assertThat(savedBoard.getTags().contains("tag2")).isTrue();
        assertThat(savedBoard.getFavorite()).isFalse();
        assertThat(savedBoard.getCount()).isEqualTo(0);
    }

    @Test
    public void findByBoardId() {
        //given
        Board savedBoard = boardRepository.save(BoardMapper.toEntity(boardDto1));

        // when
        Optional<Board> foundBoard = boardRepository.findById(savedBoard.getId());

        // then
        assertThat(foundBoard.isPresent()).isTrue();
        assertThat(foundBoard.get().getTitle()).isEqualTo("Board test1");
    }

    @Test
    public void findAllBoard() {
        // given
        boardService.createBoard(boardDto1);
        boardService.createBoard(boardDto2);
        boardService.createBoard(boardDto3);
        boardService.createBoard(boardDto4);

        //when
        Pageable firstPageable = PageRequest.of(0, 2, Sort.by("id").descending());
        Page<BoardListDto> firstBoardList = boardService.getAllBoards(firstPageable);

        // then
        assertThat(firstBoardList.isEmpty()).isFalse();
        assertThat(firstBoardList.get().count()).isEqualTo(2);
        assertThat(firstBoardList.getContent().size()).isEqualTo(2);
        assertThat(firstBoardList.getContent().get(0).getTitle()).isEqualTo("Board test4");

        // when
        Pageable secondPageable = PageRequest.of(1, 2, Sort.by("id").descending());
        Page<BoardListDto> secondBoardList = boardService.getAllBoards(secondPageable);

        assertThat(secondBoardList.isEmpty()).isFalse();
        assertThat(secondBoardList.get().count()).isEqualTo(2);
        assertThat(secondBoardList.getContent().size()).isEqualTo(2);
        assertThat(secondBoardList.getContent().get(0).getTitle()).isEqualTo("Board test2");
    }

    @Test
    public void searchBoard() {
        // given
        boardService.createBoard(boardDto1);

        // when
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").descending());
        Page<Board> boardPageByWriter = boardRepository.search("Tester", null, null, null, null, null, pageable);
        Page<Board> boardPageByTitle = boardRepository.search(null, "Board test1", null, null, null, null, pageable);
        Page<Board> boardPageByTag = boardRepository.search(null, null, "tag1", null, null, null, pageable);

        // then
        assertThat(boardPageByWriter.getContent().get(0).getWriter()).isEqualTo("Tester");
        assertThat(boardPageByTitle.getContent().get(0).getTitle()).isEqualTo("Board test1");
        assertThat(boardPageByTitle.getContent().get(0).getTags()).containsExactlyInAnyOrder("tag1", "tag2");
    }


    @Test
    public void deleteBoard() {
        // given
        BoardDto boardDto = boardService.createBoard(boardDto1);

        // when
        Optional<Board> boardBefore = boardRepository.findById(boardDto.getId());

        // then
        assertThat(boardBefore).isPresent();

        // when
        boardService.deleteBoard(boardDto.getId());
        Optional<Board> boardAfter = boardRepository.findById(boardDto.getId());

        // then
        assertThat(boardAfter).isNotPresent();
    }

    @Test
    public void deleteBoards() {
        // given
        BoardDto firstBoardDto = boardService.createBoard(boardDto1);
        BoardDto secondBoardDto = boardService.createBoard(boardDto2);

        // when
        List<Board> boardList = boardRepository.findAllById(List.of(firstBoardDto.getId(), secondBoardDto.getId()));

        // then
        assertThat(boardList.size()).isEqualTo(2);

        // when
        boardService.deleteBoardsByIds(List.of(firstBoardDto.getId(), secondBoardDto.getId()));
        List<Board> boardListAfter = boardRepository.findAllById(List.of(firstBoardDto.getId(), secondBoardDto.getId()));

        // then
        assertThat(boardListAfter.size()).isEqualTo(0);
    }

    @Test
    public void updateBoard() {
        // given
        BoardDto boardDtoBefore = boardService.createBoard(boardDto1);
        BoardUpdateDto boardUpdateDto = new BoardUpdateDto();
        boardUpdateDto.setId(boardDtoBefore.getId());
        boardUpdateDto.setTitle("Updated title");
        boardUpdateDto.setBodyContent("Updated body content");
        boardUpdateDto.setTags(new HashSet<>(Set.of("updatedTag1", "updatedTag2")));

        // when
        BoardUpdateDto updatedBoardDto = boardService.updateBoard(boardUpdateDto);
        Optional<Board> updatedBoard = boardRepository.findById(updatedBoardDto.getId());

        // then
        assertThat(updatedBoard.get().getTitle()).isEqualTo("Updated title");
        assertThat(updatedBoard.get().getBodyContent()).isEqualTo("Updated body content");
        assertThat(updatedBoard.get().getTags()).containsExactlyInAnyOrder("updatedTag1", "updatedTag2");
    }

// java.lang.UnsupportedOperationException
//    @Test
//    public void updateBoardFavorite() {
//        // given
//        BoardDto boardDto = boardService.createBoard(boardDto1);
//
//        // when
//        Optional<Board> boardBefore = boardRepository.findById(boardDto.getId());
//
//        // then
//        assertThat(boardBefore).isPresent();
//        assertThat(boardBefore.get().getFavorite()).isFalse();
//
//        // when
//        boardService.updateBoardFavorite(boardDto.getId(), true);
//
//        // then
//        Optional<Board> boardAfter = boardRepository.findById(boardDto.getId());
//        assertThat(boardAfter).isPresent();
//        assertThat(boardAfter.get().getFavorite()).isTrue();
//    }

// Transaction doesn't apply
//    @Test
//    public void verifyCountIncreaseWhenRead() {
//        // given
//        BoardDto boardDto = boardService.createBoard(boardDto1);
//
//        // when
//        Optional<Board> boardBefore = boardRepository.findById(boardDto.getId());
//
//        // then
//        assertThat(boardBefore.get().getCount()).isEqualTo(0);
//
//        // when
//        BoardViewDto boardViewDto = boardService.getBoardById(boardDto.getId());
//        Optional<Board> boardAfter = boardRepository.findById(boardDto.getId());
//
//        // then
//        assertThat(boardAfter.get().getCount()).isEqualTo(1);
//    }



}
