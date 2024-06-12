package com.midi279.portfolio.noticeboard.repository;

import com.midi279.portfolio.noticeboard.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Board board WHERE board.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT board FROM Board board WHERE " +
            "(:writer IS NULL OR board.writer = :writer) AND " +
            "(:title IS NULL OR board.title LIKE %:title%) AND " +
            "(:tag IS NULL OR :tag MEMBER OF board.tags) AND " +
            "(:favorite IS NULL OR board.favorite = :favorite) AND " +
            "(:fromDate IS NULL OR board.modifyDate >= :fromDate) AND " +
            "(:toDate IS NULL OR board.modifyDate <= :toDate)")
    Page<Board> search(@Param("writer") String writer,
                             @Param("title") String title,
                             @Param("tag") String tag,
                             @Param("favorite") Boolean favorite,
                             @Param("fromDate") LocalDateTime fromDate,
                             @Param("toDate") LocalDateTime toDate,
                             Pageable pageable);


    @Modifying
    @Transactional
    @Query("UPDATE Board board SET board.count = board.count + 1 WHERE board.id = :id")
    void incrementCount(@Param("id") Long id);




}
