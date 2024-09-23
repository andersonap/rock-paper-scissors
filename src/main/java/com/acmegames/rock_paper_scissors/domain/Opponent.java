package com.acmegames.rock_paper_scissors.domain;

import com.acmegames.rock_paper_scissors.domain.entity.Game;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public enum Opponent {

    RANDOM {
        @Override
        public Move play(final List<Game> games) {
            return RandomPlayer.play();
        }
    },
    PREDICTIVE {
        @Override
        public Move play(final List<Game> games) {
            try {
                return PredictivePlayer.play(games);
            } catch (Exception e) {
                log.warn("A exception was thrown when trying to play with PredictivePlayer." +
                        " Fallback to RandomPlayer. Exception: {}", e.getMessage());
                return RandomPlayer.play();
            }
        }
    };

    public abstract Move play(List<Game> games);
}
