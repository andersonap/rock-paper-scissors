package com.acmegames.rock_paper_scissors.domain;

import java.util.Random;

public class RandomPlayer {

    private static final Random RANDOM = new Random();
    private static final Move[] moves = Move.values();

    public static Move play() {
        return moves[RANDOM.nextInt(moves.length)];
    }
}
