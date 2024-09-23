package com.acmegames.rock_paper_scissors.service;

import com.acmegames.rock_paper_scissors.domain.Opponent;
import com.acmegames.rock_paper_scissors.domain.entity.GameConfig;
import com.acmegames.rock_paper_scissors.repository.GameConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameConfigService {

    @Autowired
    private GameConfigRepository gameConfigRepository;

    public String setOpponent(final Long userId, final Opponent opponent) {
        Optional<GameConfig> gameConfigOptional = gameConfigRepository.findByUserId(userId);

        if (gameConfigOptional.isEmpty()) {
            return gameConfigRepository.save(new GameConfig(userId, opponent))
                    .toString();
        }

        GameConfig gameConfig = gameConfigOptional.get();
        gameConfig.setOpponent(opponent);
        return gameConfigRepository.save(gameConfig).toString();
    }

    public Opponent getOpponent(final Long userId) {
        return gameConfigRepository.findByUserId(userId)
                .map(GameConfig::getOpponent)
                .orElse(Opponent.RANDOM);

    }
}
