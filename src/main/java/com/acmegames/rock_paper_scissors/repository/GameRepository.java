package com.acmegames.rock_paper_scissors.repository;

import com.acmegames.rock_paper_scissors.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM games g where g.user_id = :userId ORDER BY g.id DESC LIMIT 100")
    List<Game> findByUserId(Long userId);
}
