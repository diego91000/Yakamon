package fr.epita.assistants.yakamon.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PlayerEntity {

    UUID uuid;
    String name;

    Integer posX;

    public PlayerEntity(UUID uuid, String name, Integer posX, Integer posY, LocalDateTime lastMove, LocalDateTime lastCatch, LocalDateTime lastCollect, LocalDateTime lastFeed) {
        this.uuid = uuid;
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.lastMove = lastMove;
        this.lastCatch = lastCatch;
        this.lastCollect = lastCollect;
        this.lastFeed = lastFeed;
    }

    Integer posY;
    LocalDateTime lastMove;
    LocalDateTime lastCatch;
    LocalDateTime lastCollect;
    LocalDateTime lastFeed;

}
