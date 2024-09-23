package com.acmegames.rock_paper_scissors.service;

import com.acmegames.rock_paper_scissors.controller.resources.GameResult;
import com.acmegames.rock_paper_scissors.domain.Move;
import com.acmegames.rock_paper_scissors.domain.Opponent;
import com.acmegames.rock_paper_scissors.domain.entity.Game;
import com.acmegames.rock_paper_scissors.repository.GameRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Test
    public void testPlaySuccessfully() {
        Long userId = 1L;
        Move playerMove = Move.ROCK;
        GameRepository gameRepository = mock(GameRepository.class);
        GameConfigService gameConfigService = mock(GameConfigService.class);

        when(gameConfigService.getOpponent(userId)).thenReturn(Opponent.RANDOM);

        GameService gameService = new GameService(gameRepository, gameConfigService);

        GameResult result = gameService.play(userId, playerMove);

        assertNotNull(result);
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    public void testGetGames() {
        Long userId = 1L;
        GameRepository gameRepository = mock(GameRepository.class);
        GameConfigService gameConfigService = mock(GameConfigService.class);

        List<Game> expectedGames = List.of(new Game(userId, Move.ROCK, Opponent.RANDOM, Move.PAPER));
        when(gameRepository.findByUserId(userId)).thenReturn(expectedGames);

        GameService gameService = new GameService(gameRepository, gameConfigService);

        List<GameResult> games = gameService.getGameResults(userId);

        assertNotNull(games);
        assertEquals(expectedGames.size(), games.size());
        assertEquals(expectedGames.get(0).getPlayerMove().name(), games.get(0).getPlayerMove());
    }

    @Test
    public void testGameIsSaved() {
        Long userId = 1L;
        Move playerMove = Move.PAPER;
        Opponent opponent = Opponent.RANDOM;
        GameRepository gameRepository = mock(GameRepository.class);
        GameConfigService gameConfigService = mock(GameConfigService.class);

        when(gameConfigService.getOpponent(userId)).thenReturn(opponent);

        GameService gameService = new GameService(gameRepository, gameConfigService);

        gameService.play(userId, playerMove);

        verify(gameRepository, times(1)).save(any(Game.class));
    }


}