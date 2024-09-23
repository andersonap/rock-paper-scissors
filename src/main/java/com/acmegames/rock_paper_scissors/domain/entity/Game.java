package com.acmegames.rock_paper_scissors.domain.entity;

import com.acmegames.rock_paper_scissors.domain.Move;
import com.acmegames.rock_paper_scissors.domain.Opponent;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
public class Game {

    private static final Map<Move, Move> RULES = Map.of(Move.ROCK, Move.SCISSORS,
                                                        Move.SCISSORS, Move.PAPER,
                                                        Move.PAPER, Move.ROCK);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private Opponent opponent;
    @Enumerated(EnumType.STRING)
    private Move playerMove;
    @Enumerated(EnumType.STRING)
    private Move opponentMove;

    public enum Status { WIN, LOSE, DRAW }

    public Game(final Long userId,
                final Move playerMove,
                final Opponent opponent,
                final Move opponentMove) {
        this.userId = userId;
        this.playerMove = playerMove;
        this.opponent = opponent;
        this.opponentMove = opponentMove;
        this.date = LocalDateTime.now();
        this.play();
    }

    private void play() {
        if (playerMove.equals(opponentMove)) {
            this.setStatus(Status.DRAW);
        } else if (RULES.get(playerMove).equals(opponentMove)) {
            this.setStatus(Status.WIN);
        } else {
            this.setStatus(Status.LOSE);
        }
    }

    public boolean isWin() {
        return Status.WIN.equals(status);
    }

    public boolean isLose() {
        return Status.LOSE.equals(status);
    }

}
