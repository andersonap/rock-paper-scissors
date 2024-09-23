package com.acmegames.rock_paper_scissors.controller;

import com.acmegames.rock_paper_scissors.controller.resources.GameResult;
import com.acmegames.rock_paper_scissors.domain.Move;
import com.acmegames.rock_paper_scissors.domain.Opponent;
import com.acmegames.rock_paper_scissors.service.GameConfigService;
import com.acmegames.rock_paper_scissors.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameConfigService gameConfigService;

    @PostMapping("/{userId}/opponent")
    public ResponseEntity<String> setOpponent(@PathVariable Long userId, @RequestParam String opponent) {
        try {
            return ResponseEntity.ok(gameConfigService.setOpponent(userId, Opponent.valueOf(opponent)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/{userId}/play")
    public ResponseEntity<GameResult> play(@PathVariable Long userId, @RequestParam String move) {
        try {
            return ResponseEntity.ok(gameService.
                    play(userId, Move.valueOf(move)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/results")
    public ResponseEntity<List<GameResult>> getGameResults(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(gameService.getGameResults(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
