package com.acmegames.rock_paper_scissors.repository;

import com.acmegames.rock_paper_scissors.domain.entity.GameConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameConfigRepository extends JpaRepository<GameConfig, Long> {
    Optional<GameConfig> findByUserId(Long userId);
}
