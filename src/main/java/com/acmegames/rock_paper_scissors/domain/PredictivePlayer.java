package com.acmegames.rock_paper_scissors.domain;

import com.acmegames.rock_paper_scissors.domain.entity.Game;

import java.util.*;
import java.util.stream.Collectors;

public class PredictivePlayer {

    public static Move play(final List<Game> games) {
        if (games.isEmpty() || games.size() < 10) {
            return RandomPlayer.play();
        }

        Map<Move, Double> probabilities = Map.of(Move.ROCK, getProbability(games, Move.ROCK),
                                                 Move.PAPER, getProbability(games, Move.PAPER),
                                                 Move.SCISSORS, getProbability(games, Move.SCISSORS));

        Optional<Game> lastWinOptional = games.stream().filter(Game::isWin).max(Comparator.comparing(Game::getDate));

        if (lastWinOptional.isEmpty()) {
            return Collections.max(probabilities.entrySet(), Map.Entry.comparingByValue()).getKey();
        }

        Game lastWin = lastWinOptional.get();

        Map<Move, Double> filteredProbabilities = probabilities.entrySet().stream()
                .filter(move -> !lastWin.getPlayerMove().equals(move.getKey()))
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue() * ((double) 1 / probabilities.size())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Double total = filteredProbabilities.values().stream().mapToDouble(v -> v).sum();

        Map<Move, Double> percentageProbabilities = filteredProbabilities.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue() * 100 / total))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Collections.max(percentageProbabilities.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public static double getProbability(final List<Game> games, final Move move) {
        long gamesCount = games.stream().filter(game -> move.equals(game.getPlayerMove())).count();

        if (gamesCount == 0) {
            return 0d;
        }

        return (double) gamesCount / games.size();
    }
}
