package com.acmegames.rock_paper_scissors.controller.resources;

import com.acmegames.rock_paper_scissors.domain.entity.Game;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class GameResult implements Serializable {

    private static final String WIN = "You win! %s beats %s!";
    private static final String LOSE = "You lose! %s beats %s!";
    private static final String DRAW = "Draw! Both players chose %s!";

    private String message;
    private String status;
    private LocalDateTime date;
    private String opponent;
    private String playerMove;
    private String opponentMove;

    public static GameResult map(final Game game) {
        GameResultBuilder builder = GameResult.builder()
                .playerMove(game.getPlayerMove().name())
                .opponentMove(game.getOpponentMove().name())
                .opponent(game.getOpponent().name())
                .status(game.getStatus().name())
                .date(game.getDate());

        if (game.isWin()) {
            return builder.message(String.format(WIN, builder.playerMove, builder.opponentMove)).build();
        } else if (game.isLose()) {
            return builder.message(String.format(LOSE, builder.playerMove, builder.opponentMove)).build();
        }
        return builder.message(String.format(DRAW, builder.playerMove)).build();
    }
}
