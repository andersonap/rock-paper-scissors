package com.acmegames.rock_paper_scissors.controller;

import com.acmegames.rock_paper_scissors.controller.resources.GameResult;
import com.acmegames.rock_paper_scissors.domain.Move;
import com.acmegames.rock_paper_scissors.domain.Opponent;
import com.acmegames.rock_paper_scissors.domain.entity.Game;
import com.acmegames.rock_paper_scissors.service.GameConfigService;
import com.acmegames.rock_paper_scissors.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GameConfigService gameConfigService;

    @Test
    public void testSetOpponentSuccess() throws Exception {
        Long userId = 1L;

        when(gameConfigService.setOpponent(userId, Opponent.RANDOM)).thenReturn("GameConfig(id=1, userId=1, opponent=RANDOM)");

        mockMvc.perform(post("/v1/games/{userId}/opponent", userId)
                        .param("opponent", "RANDOM"))
                .andExpect(status().isOk())
                .andExpect(content().string("GameConfig(id=1, userId=1, opponent=RANDOM)"));
    }

    @Test
    public void testSetOpponentInvalid() throws Exception {
        Long userId = 1L;
        String invalidOpponent = "INVALID_OPPONENT";

        mockMvc.perform(post("/v1/games/{userId}/opponent", userId)
                        .param("opponent", invalidOpponent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPlaySuccess() throws Exception {
        Long userId = 1L;
        String move = "ROCK";

        Game game = new Game();
        game.setStatus(Game.Status.WIN);
        game.setPlayerMove(Move.ROCK);
        game.setOpponentMove(Move.SCISSORS);
        game.setOpponent(Opponent.RANDOM);
        GameResult gameResult = GameResult.map(game);
        when(gameService.play(userId, Move.ROCK)).thenReturn(gameResult);

        mockMvc.perform(post("/v1/games/{userId}/play", userId)
                        .param("move", move))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("You win! ROCK beats SCISSORS!"));
    }

    @Test
    public void testPlayInvalidMove() throws Exception {
        Long userId = 1L;
        String invalidMove = "INVALID_MOVE";

        mockMvc.perform(post("/v1/games/{userId}/play", userId)
                        .param("move", invalidMove))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetGameResultsSuccess() throws Exception {
        Long userId = 1L;
        List<GameResult> gameResults = Stream.of(
                new Game(userId, Move.ROCK, Opponent.RANDOM, Move.PAPER),
                new Game(userId, Move.SCISSORS, Opponent.RANDOM, Move.ROCK)
        )
                .map(GameResult::map)
                .toList();

        when(gameService.getGameResults(userId)).thenReturn(gameResults);

        mockMvc.perform(get("/v1/games/{userId}/results", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playerMove").value("ROCK"))
                .andExpect(jsonPath("$[1].playerMove").value("SCISSORS"));
    }

    @Test
    public void testGetGameResultsError() throws Exception {
        Long userId = 1L;

        when(gameService.getGameResults(userId)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/v1/games/{userId}/results", userId))
                .andExpect(status().isBadRequest());
    }


}
