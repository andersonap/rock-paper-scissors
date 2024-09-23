package com.acmegames.rock_paper_scissors.service;

import com.acmegames.rock_paper_scissors.controller.resources.GameResult;
import com.acmegames.rock_paper_scissors.domain.Move;
import com.acmegames.rock_paper_scissors.domain.Opponent;
import com.acmegames.rock_paper_scissors.domain.entity.Game;
import com.acmegames.rock_paper_scissors.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameConfigService gameConfigService;

    public GameService(GameRepository gameRepository, GameConfigService gameConfigService) {
        this.gameRepository = gameRepository;
        this.gameConfigService = gameConfigService;
    }

    public GameResult play(final Long userId, final Move playerMove) {
        Opponent opponent = getOpponent(userId);
        Move opponentMove = opponent.play(getGames(userId));

        Game game = new Game(userId, playerMove, opponent, opponentMove);
        gameRepository.save(game);

        return GameResult.map(game);
    }

    private Opponent getOpponent(final Long userId) {
        return gameConfigService.getOpponent(userId);
    }

    private List<Game> getGames(final Long userId) {
        return gameRepository.findByUserId(userId);
    }

    public List<GameResult> getGameResults(final Long userId) {
        return getGames(userId).stream().map(GameResult::map).toList();
    }
}
