package fr.epita.assistants.yakamon.presentation.api.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PlayerResponse {

    UUID uuid;
    String name;
    Integer posX;
    Integer posY;
    LocalDateTime lastMove;
    LocalDateTime lastCatch;
    LocalDateTime lastCollect;
    LocalDateTime lastFeed;

    public PlayerResponse(UUID uuid, String name, int pos_x, int pos_y, LocalDateTime last_move, LocalDateTime last_catch, LocalDateTime last_collect, LocalDateTime last_feed) {
        this.uuid = uuid;
        this.name = name;
        this.posX = pos_x;
        this.posY = pos_y;
        this.lastMove = last_move;
        this.lastCatch = last_catch;
        this.lastCollect = last_collect;
        this.lastFeed = last_feed;
    }
}
