package com.acmegames.rock_paper_scissors.domain;

import com.acmegames.rock_paper_scissors.domain.entity.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredictivePlayerTest {

    @Test
    public void testPlayWithLessThanTenGames() {
        List<Game> games = Arrays.asList(
                new Game(1L, Move.ROCK, null, Move.PAPER),
                new Game(1L, Move.SCISSORS, null, Move.ROCK)
        );

        Move move = PredictivePlayer.play(games);

        assertNotNull(move);
    }


    @Test
    public void testPlayWithMoreThanTenGames() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            games.add(new Game(1L, Move.ROCK, null, Move.PAPER));
        }

        Move move = PredictivePlayer.play(games);

        assertEquals(Move.ROCK, move);
    }

    @Test
    public void testPlayWithLastWin() {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            games.add(new Game(1L, Move.ROCK, null, Move.PAPER));
        }

        Game lastWin = new Game(1L, Move.PAPER, null, Move.ROCK);
        lastWin.setStatus(Game.Status.WIN);
        games.add(lastWin);

        Move move = PredictivePlayer.play(games);

        assertNotEquals(Move.PAPER, move);
    }

    @Test
    public void testProbabilityWithNoMove() {
        List<Game> games = Arrays.asList(
                new Game(1L, Move.ROCK, null, Move.PAPER),
                new Game(1L, Move.ROCK, null, Move.SCISSORS)
        );

        double probability = PredictivePlayer.getProbability(games, Move.PAPER);

        assertEquals(0.0, probability, 0.001);
    }

    @Test
    public void testGetProbability() {
        List<Game> games = Arrays.asList(
                new Game(1L, Move.ROCK, null, Move.PAPER),
                new Game(1L, Move.PAPER, null, Move.SCISSORS),
                new Game(1L, Move.ROCK, null, Move.PAPER)
        );

        double rockProbability = PredictivePlayer.getProbability(games, Move.ROCK);
        double paperProbability = PredictivePlayer.getProbability(games, Move.PAPER);
        double scissorsProbability = PredictivePlayer.getProbability(games, Move.SCISSORS);

        assertEquals(2.0 / 3.0, rockProbability, 0.001);
        assertEquals(1.0 / 3.0, paperProbability, 0.001);
        assertEquals(0.0, scissorsProbability, 0.001);
    }

}