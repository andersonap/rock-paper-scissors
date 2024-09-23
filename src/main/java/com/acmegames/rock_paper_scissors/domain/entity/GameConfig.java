package com.acmegames.rock_paper_scissors.domain.entity;

import com.acmegames.rock_paper_scissors.domain.Opponent;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "game_config")
@Data
@NoArgsConstructor
public class GameConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Opponent opponent;

    public GameConfig(final Long userId, final Opponent opponent) {
        this.userId = userId;
        this.opponent = opponent;
    }
}
